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
			} else if (key > node.key) {
				node.right = AVLNode(key, value)
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

	private fun rotateLeft(nodeA: AVLNode<K, V>?) {
		if (nodeA != null) {
			if ((nodeA.right) != null) {
				val nodeB = nodeA.right
				nodeA.right = nodeB?.left
				nodeB?.left = nodeA
				fixHeight(nodeA)
				fixHeight(nodeB)
			} else {
				throw NullPointerException("Right child node cannot be null.")
			}
		} else {
			throw NullPointerException("Node cannot be null.")
		}
	}

	private fun rotateRight(nodeA: AVLNode<K, V>?) {
		if (nodeA != null) {
			if ((nodeA.left) != null) {
				val nodeB = nodeA.left
				nodeA.left = nodeB?.right
				nodeB?.right = nodeA
				fixHeight(nodeA)
				fixHeight(nodeB)
			} else {
				throw NullPointerException("Left child node cannot be null.")
			}
		} else {
			throw NullPointerException("Node cannot be null.")
		}
	}

	private fun bigRotateLeft(nodeA: AVLNode<K, V>?) {
		if (nodeA != null) {
			rotateRight(nodeA.right)
			rotateLeft(nodeA)
		} else {
			throw NullPointerException("Node cannot be null.")
		}
	}

	private fun bigRotateRight(nodeA: AVLNode<K, V>?) {
		if (nodeA != null) {
			rotateLeft(nodeA.left)
			rotateRight(nodeA)
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
					bigRotateLeft(node)
				} else {
					rotateLeft(node)
				}
			}
		}
		if (balanceFactor(node) == -2) {
			if (node != null) {
				if (balanceFactor(node.left) > 0) {
					bigRotateRight(node)
				} else {
					rotateRight(node)
				}
			}
		}
	}

}