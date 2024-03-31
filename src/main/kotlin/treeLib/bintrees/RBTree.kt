package treeLib.bintrees

import treeLib.bintrees.interfaces.BinTree
import treeLib.bintrees.interfaces.TreeBalancer
import treeLib.nodes.RBNode

/**
 * Class which implements... Red-Black Tree :O
 * Takes two types: for key(K) and for value(V)
 */
class RBTree<K : Comparable<K>, V> : BinTree<K, V, RBNode<K, V>>(), TreeBalancer<K, V, RBNode<K, V>> {
	override var root: RBNode<K, V>? = null
	override var amountOfNodes: Int = 0

	/**
	 * Adds or replaces node to the tree depending on given key:
	 * 1. Adds node to the tree and returns VALUE,
	 * 2. If node with given key already exist, it does nothing(check changeVAAAAAAAAAAAAAAAAAAAL() method)
	 */
	override fun add(key: K, value: V): RBNode<K, V>? {
		val treeBranch = ArrayDeque<RBNode<K, V>>()
		val newNode = RBNode(key, value)
		if (root == null) {
			root = newNode
		} else {
			var curNode = root
			while (curNode != null) {
				treeBranch.addFirst(curNode)
				val nextNode = curNode.moveOn(key)
				if (nextNode === curNode) return null	//node with given key already exist
				curNode = nextNode
			}
			val parent = treeBranch.first()
			parent.attach(newNode)
		}
		amountOfNodes++
		treeBranch.addFirst(newNode)
		balancerAdd(treeBranch)
		return newNode
	}

	/**
	 * Used in add() method to balance tree :)
	 */
	private fun balancerAdd(treeBranch: ArrayDeque<RBNode<K, V>>) {
		var son = treeBranch.removeFirst()
		var parent = treeBranch.removeFirstOrNull()
		var grandparent = treeBranch.removeFirstOrNull()

		while (parent != null && parent.isRed && grandparent != null) {
			val uncle = if (parent != grandparent.right) grandparent.right else grandparent.left

			if (uncle?.isRed == true) {
				parent.isRed = false
				uncle.isRed = false
				grandparent.isRed = true
				son = grandparent
				parent = treeBranch.removeFirstOrNull()
				grandparent = treeBranch.removeFirstOrNull()
			} else {
				if (parent === grandparent.right && son === parent.left) {
					rotateRight(parent, grandparent)
					parent = son
				} else if (parent === grandparent.left && son === parent.right) {
					rotateLeft(parent, grandparent)
					parent = son
				}
				parent.isRed = false
				grandparent.isRed = true
				if (parent === grandparent.right) rotateLeft(grandparent, treeBranch.firstOrNull())
				else rotateRight(grandparent, treeBranch.firstOrNull())
			}
		}

		root?.isRed = false
	}

	/**
	 * Removes node with the same key and returns node's key and value.
	 * Or returns null if node with the same key doesn't exist.
	 */
	override fun remove(key: K): V? {
		val treeBranch = ArrayDeque<RBNode<K, V>>()
		var removedNode = if (root != null) root else return null
		while (removedNode?.key != key) {
			if (removedNode == null) return null	//node with given key doesn't exist
			treeBranch.addFirst(removedNode)
			removedNode = removedNode.moveOn(key)
		}

		var parent = treeBranch.firstOrNull()
		val sonRight = removedNode.right
		val sonLeft = removedNode.left
		val sonRemoved = when {
			sonRight == null && sonLeft == null -> null
			sonRight != null && sonLeft == null -> sonRight
			sonRight == null && sonLeft != null -> sonLeft
			else /*sonRight and sonLeft != null*/ -> {
				val replace = removedNode	//Will rewrite this node with other one
				treeBranch.addFirst(replace)
				removedNode = sonRight
				while (removedNode?.left != null) {
					treeBranch.addFirst(removedNode)
					removedNode = removedNode.left
				}
				parent = treeBranch.first()
				replace.key = removedNode?.key ?: throw Exception("well...")
				replace.value = removedNode?.value ?: throw Exception("that's bad")
				removedNode.right
			}
		}

		if (removedNode === parent?.right) parent.right
		else if (removedNode === parent?.left) parent.left = sonRemoved
		amountOfNodes--
		if (sonRemoved != null && !removedNode.isRed) {
			treeBranch.addFirst(sonRemoved)
			balancerRemove(treeBranch)
		}

		return removedNode.value
	}

	/**
	 * Used in remove() method to balance tree :(
	 */
	private fun balancerRemove(treeBranch: ArrayDeque<RBNode<K, V>>) {
		val son = treeBranch.removeFirstOrNull()
		var parent = treeBranch.removeFirstOrNull()
		var grandparent = treeBranch.removeFirstOrNull()


		while (parent != null && son?.isRed == false) {
			if (son === parent.right) {
				var brother = parent.left
				if (brother?.isRed == true) {
					brother.isRed = false
					parent.isRed = true
					rotateRight(parent, grandparent)
					grandparent = brother
					brother = parent.left
				}

				val nephewRight = brother?.right
				val nephewLeft = brother?.left
				if (nephewRight?.isRed == false && nephewLeft?.isRed == false) {
					brother?.isRed = true
				} else {
					if (nephewLeft?.isRed == false) {
						nephewRight?.isRed = false
						brother?.isRed = true
						rotateLeft(brother, parent)
						parent = nephewLeft
					}
					brother?.isRed = parent.isRed
					parent.isRed = false
					nephewRight?.isRed = false
					rotateLeft(parent, grandparent)
				}
			} else if (son === parent.left) {
				var brother = parent.right
				if (brother?.isRed == true) {
					brother.isRed = false
					parent.isRed = true
					rotateLeft(parent, grandparent)
					grandparent = brother
					brother = parent.right
				}

				val nephewRight = brother?.right
				val nephewLeft = brother?.left
				if (nephewRight?.isRed == false && nephewLeft?.isRed == false) {
					brother?.isRed = true
				} else {
					if (nephewRight?.isRed == false) {
						nephewLeft?.isRed = false
						brother?.isRed = true
						rotateRight(brother, parent)
					}
					brother?.isRed = parent.isRed == true
					parent.isRed = false
					nephewRight?.isRed = false
					rotateLeft(parent, grandparent)
					break
				}
			}
		}

		root?.isRed = false
	}
}