package treeLib.bintrees

import treeLib.bintrees.interfaces.BinTree
import treeLib.nodes.AVLNode


class AVLTree<K : Comparable<K>, V> : BinTree<K, V, AVLNode<K, V>>() {
	override var root: AVLNode<K, V>? = null
	override var amountOfNodes = 0

	override fun remove(key: K): V? {
		var value: V? = null
		fun removeRec(node: AVLNode<K, V>?, key: K): AVLNode<K, V>? {
			if (node == null) return null
			if (key < node.key) {
				node.left = removeRec(node.left, key)
			} else if (key > node.key) {
				node.right = removeRec(node.right, key)
			} else {
				val nodeA = node.left
				val nodeB = node.right
				value = node.value
				if (nodeB == null) {
					return nodeA
				}
				val min = findMin(nodeB)
				min?.right = removeMin(nodeB)
				min?.left = nodeA
				if (root?.key == key){
					root = min
				}
				return balanceNode(min)
			}
			return balanceNode(node)
		}
		removeRec(root, key)
		amountOfNodes -= 1
		return value
	}

	private fun findMin(node: AVLNode<K, V>?): AVLNode<K, V>? {
		if (node?.left != null) {
			return findMin(node.left)
		}
		return node
	}

	private fun removeMin(node: AVLNode<K, V>?): AVLNode<K, V>? {
		if (node?.left == null) {
			return node?.right
		}
		node.left = removeMin(node.left)
		return balanceNode(node)
	}

	override fun add(key: K, value: V): AVLNode<K, V>? {
		fun addRec(node: AVLNode<K, V>?, key: K, value: V): AVLNode<K, V>? {
			if (node == null) {
				return AVLNode(key, value)
			}
			if (key < node.key) {
				node.left = addRec(node.left, key, value)
			} else if (key > node.key) {
				node.right = addRec(node.right, key, value)
			}
			return balanceNode(node)
		}
		if (root == null) {
			root = AVLNode(key, value)
			amountOfNodes += 1
			return root
		}
		amountOfNodes += 1
		return addRec(root, key, value)
	}

	private fun rotateLeft(nodeA: AVLNode<K, V>?): AVLNode<K, V>? {
		if (nodeA != null) {
			if (nodeA.right != null) {
				val nodeB = nodeA.right
				nodeA.right = nodeB?.left
				nodeB?.left = nodeA
				fixHeight(nodeA)
				fixHeight(nodeB)
				return nodeB
			} else {
				throw NullPointerException("Right child node cannot be null.")
			}
		} else {
			throw NullPointerException("Node cannot be null.")
		}
	}

	private fun rotateRight(nodeA: AVLNode<K, V>?): AVLNode<K, V>? {
		if (nodeA != null) {
			if (nodeA.left != null) {
				val nodeB = nodeA.left
				nodeA.left = nodeB?.right
				nodeB?.right = nodeA
				fixHeight(nodeA)
				fixHeight(nodeB)
				return nodeB
			} else {
				throw NullPointerException("Left child node cannot be null.")
			}
		} else {
			throw NullPointerException("Node cannot be null.")
		}
	}

	private fun height(node: AVLNode<K, V>?): Int {
		if (node == null) {
			return 0
		}
		return node.height
	}

	private fun balanceFactor(node: AVLNode<K, V>?): Int {
		if (node == null) {
			throw NullPointerException("Node cannot be null.")
		}
		return height(node.right) - height(node.left)
	}

	private fun fixHeight(node: AVLNode<K, V>?) {
		if (node != null) {
			val heightLeft = height(node.left)
			val heightRight = height(node.right)
			if (heightLeft > heightRight) {
				node.height = heightLeft + 1
			} else {
				node.height = heightRight + 1
			}
		}
	}

	private fun balanceNode(node: AVLNode<K, V>?): AVLNode<K, V>? {
		fixHeight(node)
		if (balanceFactor(node) == 2) {
			if (node != null) {
				if (balanceFactor(node.right) < 0) {
					node.right = rotateRight(node.right)
				}
				if (node == root) {
					root = rotateLeft(node)
					return root
				}
				return rotateLeft(node)
			}
		}
		if (balanceFactor(node) == -2) {
			if (node != null) {
				if (balanceFactor(node.left) > 0) {
					node.left = rotateLeft(node.left)
				}
				if (node == root) {
					root = rotateRight(node)
					return root
				}
				return rotateRight(node)
			}
		}
		return node
	}

	override fun height(): Int? {
		return if (root == null) {
			0
		} else {
			root?.height
		}
	}
}