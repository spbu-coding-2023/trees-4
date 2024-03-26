package treeLib.bintrees

import treeLib.bintrees.interfaces.BinTree
import treeLib.nodes.AVLNode

class AVLTree<K : Comparable<K>, V> : BinTree<K, V, AVLNode<K, V>> {
	override var root: AVLNode<K, V>? = null

	override fun add(key: K, value: V) {
		var node: AVLNode<K, V>? = null
		var curr = root
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
				println("Error: this key is already in the tree\n.")
			}
		}
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

	private fun rotateLeft(node: AVLNode<K, V>) {
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
			println("Node.right is null.\n")
		}
	}

	private fun rotateRight(node: AVLNode<K, V>) {
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
			println("Node.left is null.\n")
		}
	}
}
