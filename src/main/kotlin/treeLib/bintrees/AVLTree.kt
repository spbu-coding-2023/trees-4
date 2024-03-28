package treeLib.bintrees

import treeLib.bintrees.interfaces.BinTree
import treeLib.nodes.AVLNode

class AVLTree<K : Comparable<K>, V> : BinTree<K, V, AVLNode<K, V>> {
	override var root: AVLNode<K, V>? = null
	override var amountOfNodes: Int
		get() = TODO("Not yet implemented")
		set(value) {}

	override fun add(key: K, value: V): V? {
		var node: AVLNode<K, V>? = null
		var curr = root
		var toReturn: V? = null
		while (curr != null) {
			node = curr
			if (key < curr.key) {
				curr = curr.left
			} else {
				curr = curr.right
			}
		}
		if (node == null) {
			root = AVLNode(key, value)
		} else {
			if (key < node.key) {
				node.left = AVLNode(key, value)
				(node.left)?.parent = node
			} else if (key > node.key) {
				node.right = AVLNode(key, value)
				(node.right)?.parent = node
			} else {
				toReturn = node.value
				node.value = value
			}
		}

		balanceNode(node)

		return toReturn
	}

	fun initTree(data: List<Pair<K, V>>): AVLTree<K, V> {
		val tree = AVLTree<K, V>()
		for (element in data) {
			if (tree.root == null) {
				tree.root = AVLNode(element.first, element.second)
			} else {
				tree.add(element.first, element.second)
			}
		}
		return tree
	}

	private fun rotateLeft(node: AVLNode<K, V>?) {
		if (node != null) {
			if ((node.right) != null) {
				val oldRight = node.right
				node.right = oldRight?.left

				if (oldRight?.left != null) {
					(oldRight.left)?.parent = node
				}

				oldRight?.parent = node.parent
				if ((node.parent) == null) {
					root = oldRight
				} else if (node == (node.parent)?.left) {
					(node.parent)?.left = oldRight
				} else {
					(node.parent)?.right = oldRight
				}

				oldRight?.left = node
				node.parent = oldRight

			} else {
				throw NullPointerException("Right child node cannot be null.")
			}
		} else {
			throw NullPointerException("Node cannot be null.")
		}
	}

	private fun rotateRight(node: AVLNode<K, V>?) {
		if (node != null) {
			if ((node.left) != null) {
				val oldLeft = node.left
				node.left = oldLeft?.right

				if (oldLeft?.right != null) {
					(oldLeft.right)?.parent = node
				}

				oldLeft?.parent = node.parent
				if ((node.parent) == null) {
					root = oldLeft
				} else if (node == (node.parent)?.right) {
					(node.parent)?.right = oldLeft
				} else {
					(node.parent)?.left = oldLeft
				}

				oldLeft?.right = node
				node.parent = oldLeft

			} else {
				throw NullPointerException("Left child node cannot be null.")
			}
		} else {
			throw NullPointerException("Node cannot be null.")
		}
	}

	private fun height(node: AVLNode<K, V>?) : Int {
		if (node == null) {
			return 0
		}
		return node.height
	}

	private fun balanceFactor(node: AVLNode<K, V>?) : Int {
		if (node == null) {
			throw NullPointerException("Node cannot be null.")
		}
		return height(node.right) - height(node.left)
	}

	private fun fixHeight(node: AVLNode<K, V>?) {
		if (node != null) {
			var heightLeft = height(node.left)
			var heightRight = height(node.right)
			if (heightLeft > heightRight) {
				node.height = heightLeft + 1
			} else {
				node.height = heightRight + 1
			}
		}
	}

	private fun balanceNode(node: AVLNode<K, V>?) {
		fixHeight(node)
		if (balanceFactor(node) == 2) {
			if (node != null) {
				if (balanceFactor(node.right) < 0) {
					node.right?.let { rotateRight(it) }
					rotateLeft(node)
				}
			}
		}
		if (balanceFactor(node) == -2) {
			if (node != null) {
				if (balanceFactor(node.left) > 0) {
					node.left?.let { rotateLeft(it) }
					rotateRight(node)
				}
			}
		}
	}

}