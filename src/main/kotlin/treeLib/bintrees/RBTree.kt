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
	 * 1. Adds node to the tree and returns node,
	 * 2. If node with given key already exist, it does nothing and returns null
	 * (to actually change value use changeVal)
	 */
	override fun add(key: K, value: V): RBNode<K, V>? {
		val treeBranch = ArrayDeque<RBNode<K, V>>()   //to know who's the parent
		val newNode = RBNode(key, value)
		if (root == null) {
			root = newNode
		} else {
			var curNode = root
			while (curNode != null) {
				treeBranch.addFirst(curNode)
				val nextNode = curNode.moveOn(key)
				if (nextNode == curNode) return null    //node with given key already exist
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
			} else /*uncle is black or null */ {
				if (parent == grandparent.right && son == parent.left) {
					rotateRight(parent, grandparent)
					parent = son
				} else if (parent == grandparent.left && son == parent.right) {
					rotateLeft(parent, grandparent)
					parent = son
				}
				parent.isRed = false
				grandparent.isRed = true
				if (parent == grandparent.right) {
					rotateLeft(grandparent, treeBranch.firstOrNull())
				} else {
					rotateRight(grandparent, treeBranch.firstOrNull())
				}
			}
		}

		root?.isRed = false
	}

	/**
	 * Removes node with the same key and returns node's value.
	 * Or does nothing and returns null if there's no such node.
	 */
	override fun remove(key: K): V? {
		val treeBranch = ArrayDeque<RBNode<K, V>?>()   //to know who's the parent
		var curNode = root ?: return null
		while (curNode.key != key) {
			treeBranch.addFirst(curNode)
			val nextNode = curNode.moveOn(key) ?: return null //node with given key doesn't exist
			curNode = nextNode
		}

		var parent = treeBranch.firstOrNull()
		val sonRight = curNode.right
		val sonLeft = curNode.left
		val sonOfRemovedNode = when {
			sonRight != null && sonLeft != null -> {
				treeBranch.addFirst(curNode)
				val copyTo = curNode    //Will copy from curNode
				curNode = sonLeft
				while (curNode.right != null) {
					treeBranch.addFirst(curNode)
					curNode = curNode.right ?: throw NullPointerException("Removing node cannot be null.")
				}

				parent = treeBranch.first()
				val temp = copyTo.value
				copyTo.key = curNode.key
				copyTo.value = curNode.value
				curNode.value = temp  //to save deleted node's value
				curNode.left
			}
			sonRight != null -> sonRight
			else /*sonLeft != null*/ -> sonLeft
		}

		amountOfNodes--
		when (curNode) {
			parent?.right -> parent.right = sonOfRemovedNode
			parent?.left -> parent.left = sonOfRemovedNode
			else -> root = sonOfRemovedNode   //only root doesn't have parent
		}
		treeBranch.addFirst(sonOfRemovedNode)
		if (!curNode.isRed) balancerRemove(treeBranch)

		return curNode.value
	}

	/**
	 * Used in remove() method to balance tree :(
	 */
	private fun balancerRemove(treeBranch: ArrayDeque<RBNode<K, V>?>) {
		var son = treeBranch.removeFirstOrNull()
		var parent = treeBranch.removeFirstOrNull()
		var grandparent = treeBranch.removeFirstOrNull()

		if (son?.isRed == true) {
			son.isRed = false
			return
		}
		while (parent != null) {
			var brother = if (son == parent.right) parent.left else parent.right
			if (brother?.isRed == true) {
				brother.isRed = false
				parent.isRed = true
				if (son == parent.right) {
					rotateRight(parent, grandparent)
					grandparent = brother
					brother = parent.left
				}
				if (son == parent.left) {
					rotateLeft(parent, grandparent)
					grandparent = brother
					brother = parent.right
				}
			}

			var nephewRight = brother?.right
			var nephewLeft = brother?.left
			if ((nephewRight == null || !nephewRight.isRed) && (nephewLeft == null || !nephewLeft.isRed)) {
				brother?.isRed = true
				if (!parent.isRed) {
					parent.isRed = false
					son = parent
					parent = grandparent
					grandparent = treeBranch.removeFirstOrNull()
				} else {
					parent.isRed = false
					break
				}
			} else {
				if (son == parent.right) {
					if (nephewRight?.isRed == true && (nephewLeft == null || !nephewLeft.isRed)) {
						rotateLeft(brother, parent)
						brother = parent.left
						nephewLeft = brother?.left
						brother?.isRed = false
						nephewLeft?.isRed = true
					}

					rotateRight(parent, grandparent)
					brother?.isRed = parent.isRed
					parent.isRed = false
					nephewLeft?.isRed = false
				}
				if (son == parent.left) {
					if (nephewLeft?.isRed == true && (nephewRight == null || !nephewRight.isRed)) {
						rotateRight(brother, parent)
						brother = parent.right
						nephewRight = brother?.right
						brother?.isRed = false
						nephewRight?.isRed = true
					}

					rotateLeft(parent, grandparent)
					brother?.isRed = parent.isRed
					parent.isRed = false
					nephewRight?.isRed = false
					}
				break
			}
		}

		root?.isRed = false
	}
}