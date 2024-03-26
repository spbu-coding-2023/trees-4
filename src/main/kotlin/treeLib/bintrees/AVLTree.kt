package treeLib.bintrees

import treeLib.nodes.AVLNode

class AVLTree<K : Comparable<K>, V> : BSTree<K, V>() {
	private var root: AVLNode<K, V>? = null

	override fun add(key: K, value: V) {
		var node: AVLNode<K, V>? = null
		var curr = root
		while (curr != null) {
			node = curr
			if (key < curr.key) { curr = curr.left }
			else { curr = curr.right }
		}
		if (node == null) {
			root = AVLNode(key, value)
		} else {
			if (key < node.key) {
				node.left = AVLNode(key, value)
			} else {
				node.right = AVLNode(key, value)
			}
		}
	}

	///пока больше для отладки
	fun inorder(root: AVLNode<K, V>?) {
		if (root != null) {
			inorder(root.left)
			println(root.key)
			inorder(root.right)
		}
	}
}