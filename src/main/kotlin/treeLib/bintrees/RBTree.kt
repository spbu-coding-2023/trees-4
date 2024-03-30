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
	 * 1. Adds node to the tree and returns null,
	 * 2. If node with given key already exist, it will replace it with new node(also with new value).
	 * In this case, method will return Pair(key, old value)
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
				if (nextNode === curNode) {   //replace
					val parent = treeBranch.first()
					parent.attach(newNode)
					newNode.left = curNode.left
					newNode.right = curNode.right
					newNode.isRed = curNode.isRed
					return curNode.value
				}
				curNode = nextNode
			}
			val parent = treeBranch.first()
			parent.attach(newNode)
		}
		amountOfNodes++
		treeBranch.addFirst(newNode)
		balancerAdd(treeBranch)
		return null
	}

	/**
	 * Used in add() method to balance tree :)
	 */
	override fun balancerAdd(treeBranch: ArrayDeque<RBNode<K, V>>) {
		var (son, parent, grandparent) =
			Triple(
				treeBranch.removeFirstOrNull(),
				treeBranch.removeFirstOrNull(),
				treeBranch.removeFirstOrNull()
			)

		while (parent != null && parent.isRed) {
			if (parent === grandparent?.left) {
				val uncle = grandparent.right
				if (uncle?.isRed == true) {
					parent.isRed = false
					uncle.isRed = false
					grandparent.isRed = true

					son = grandparent
					parent = treeBranch.removeFirstOrNull()
					grandparent = treeBranch.removeFirstOrNull()
				} else /*uncle == black or null*/ {
					if (son === parent.right) {
						son = parent
						parent = grandparent
						grandparent = treeBranch.removeFirst()
						rotateLeft(son, parent)
					}
					parent.isRed = false
					grandparent.isRed = true
					rotateRight(grandparent, treeBranch.firstOrNull())
				}
			} else if (parent === grandparent?.right) {
				val uncle = grandparent.left
				if (uncle?.isRed == true) {
					parent.isRed = false
					uncle.isRed = false
					grandparent.isRed = true

					son = grandparent
					parent = treeBranch.removeFirstOrNull()
					grandparent = treeBranch.removeFirstOrNull()
				} else /*uncle == black or null*/ {
					if (son === parent.left) {
						son = parent
						parent = grandparent
						grandparent = treeBranch.removeFirstOrNull()
						rotateRight(son, parent)
					}
					parent.isRed = false
					if (grandparent != null) {//why safe call
						grandparent.isRed = true
						rotateLeft(grandparent, treeBranch.firstOrNull())
					}
				}
			} else /*grandparent == null*/ {
				parent.isRed = false
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
		var curNode = root
		while (curNode != null) {
			treeBranch.addFirst(curNode)
			val nextNode = curNode.moveOn(key)
			if (nextNode === curNode) {   //remove
				val parent = treeBranch.first()
				when {
					curNode.left != null && curNode.right != null -> TODO()
					curNode.left != null -> TODO("replace nodes")
					curNode.right != null -> TODO("replace nodes")
					else -> TODO("удалить у родителя указатель")
				}
				balancerRemove(treeBranch)
				return curNode.value
			}
		}
		return null
	}

	/**
	 * Used in remove() method to balance tree :(
	 */
	override fun balancerRemove(treeBranch: ArrayDeque<RBNode<K, V>>) {
		TODO("Not yet implemented")
	}
}