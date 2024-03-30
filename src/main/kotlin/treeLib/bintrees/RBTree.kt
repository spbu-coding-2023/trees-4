package treeLib.bintrees

import treeLib.bintrees.interfaces.BinTree
import treeLib.bintrees.interfaces.TreeBalancer
import treeLib.nodes.RBNode

/**
 * Class which implements... Red-Black Tree :O
 * Takes two types: for key(K) and for value(V)
 */
class RBTree<K : Comparable<K>, V> : BinTree<K, V, RBNode<K, V>>, TreeBalancer<K, V, RBNode<K, V>> {
	override var root: RBNode<K, V>? = null
	override var amountOfNodes: Int = 0

	/**
	 * Adds or replaces node to the tree depending on given key:
	 * 1. Adds node to the tree and returns VALUE,
	 * 2. If node with given key already exist, it does nothing(check changeVAAAAAAAAAAAAAAAAAAAL() method)
	 */
	override fun add(key: K, value: V): V? {
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
		return value
	}

	/**
	 * Used in add() method to balance tree :)
	 */
	override fun balancerAdd(treeBranch: ArrayDeque<RBNode<K, V>>) {
		var son = treeBranch.removeFirstOrNull()
		var parent = treeBranch.removeFirstOrNull()
		var	grandparent = treeBranch.removeFirstOrNull()

		while (parent != null && parent.isRed) {
			val uncle = when {
				parent === grandparent?.right -> grandparent.left
				parent === grandparent?.left -> grandparent.right
				else -> null
			}

			if (uncle?.isRed == false) {
				if (parent === grandparent?.right && son === parent.left) {
					rotateRight(parent, grandparent)
					parent = son
					son = parent?.right
				} else if (parent === grandparent?.left && son === parent.right) {
					rotateLeft(parent, grandparent)
					parent = son
					son = parent?.left
				}
				parent?.isRed = false
				grandparent?.isRed = true
				if (parent === grandparent?.right) rotateLeft(grandparent, treeBranch.firstOrNull())
				else if (parent === grandparent?.left) rotateRight(grandparent, treeBranch.firstOrNull())
			} else {
				parent.isRed = false
				uncle?.isRed = false
				grandparent?.isRed = true
				son = grandparent
				parent = treeBranch.removeFirstOrNull()
				grandparent = treeBranch.removeFirstOrNull()
			}
		}

		root?.isRed = false
	}

	/**
	 * Removes node with the same key and returns node's key and value.
	 * Or returns null if node with the same key doesn't exist.
	 */
	fun remove(key: K): V? {
		val treeBranch = ArrayDeque<RBNode<K, V>>()
		var curNode = root

		while (curNode != null && curNode.key != key) {
			treeBranch.addFirst(curNode)
			curNode = curNode.moveOn(key)
		}

		if (curNode == null) return null	//node with given key isn't exist

		val parent = treeBranch.first()
		when {
			curNode == parent.right -> {
				when {
					curNode.left != null && curNode.right != null -> {
						var newNode = curNode.right
						while (newNode?.left?.left != null) newNode = newNode.left
						parent.right = newNode?.left
						newNode?.left = null
					}
					else -> parent.right = if (curNode.right != null)  curNode.right else curNode.left
				}
			}
			curNode == parent.left -> {
				when {
					curNode.right != null && curNode.left != null -> {
						var newNode = curNode.right
						while (newNode?.left?.left != null) newNode = newNode.left
						parent.left = newNode?.left
						newNode?.left = null
					}
					else -> parent.left =  if (curNode.right != null)  curNode.right else curNode.left
				}
			}
			else -> throw Exception("Node without a parent. What a tragedy.")
		}
		amountOfNodes--
		//treeBranch.addFirst(newNode)
		balancerRemove(treeBranch)
		return curNode.value
	}

	/**
	 * Used in remove() method to balance tree :(
	 */
	override fun balancerRemove(treeBranch: ArrayDeque<RBNode<K, V>>) {
		val son = treeBranch.removeFirstOrNull()
		val parent = treeBranch.removeFirstOrNull()

		while (son?.isRed == false) {
			if (son === parent?.right) {
				val brother = parent.left
				if (brother?.isRed == true) {
					brother.isRed = false
					parent.isRed = true
					rotateLeft(parent, treeBranch.firstOrNull())
				}
				val nephewRight = brother?.right
				val nephewLeft = brother?.left
				if (nephewRight?.isRed == false && nephewLeft?.isRed == false) {
					brother.isRed = true
				} else {
					if (nephewRight?.isRed == false) {
						brother.isRed = true
						nephewLeft?.isRed = false
						rotateRight(brother, parent)
					}
					brother?.isRed = parent.isRed
					parent.isRed = false
					nephewRight?.isRed = false
					rotateLeft(parent, treeBranch.firstOrNull())
					return
				}
			} else if (son === parent?.left) {
				val brother = parent.right
				if (brother?.isRed == true) {
					brother.isRed = false
					parent.isRed = true
					rotateRight(parent, treeBranch.firstOrNull())
				}
				val nephewRight = brother?.right
				val nephewLeft = brother?.left
				if (nephewRight?.isRed == false && nephewLeft?.isRed == false) {
					brother.isRed = true
				} else {
					if (nephewLeft?.isRed == false) {
						brother.isRed = true
						nephewRight?.isRed = false
						rotateLeft(brother, parent)
					}
					brother?.isRed = parent.isRed
					parent.isRed = false
					nephewLeft?.isRed = false
					rotateRight(parent, treeBranch.firstOrNull())
					return
				}
			}
		}
	}
}