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

	// add для коллекций?

	override fun initTree(data:  List<Pair<K, V>>): AVLTree<K, V> {
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

	// пока больше для отладки -> замена на iterable
	fun inorder() {
		fun inorderRec(root: AVLNode<K, V>?) {
			if (root != null) {
				inorderRec(root.left)
				println(root.key)
				inorderRec(root.right)
			}
		}
		inorderRec(root)
	}
}