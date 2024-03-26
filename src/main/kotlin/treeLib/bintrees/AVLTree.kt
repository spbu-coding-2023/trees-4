package treeLib.bintrees

import treeLib.nodes.AVLNode

class AVLTree<K: Comparable<K>, V> : BSTree<K, V>() {
	private val root : AVLNode<K, V>? = null

	///пока больше для отладки
	fun inorder(root: AVLNode<K, V>?) {
		if (root != null) {
			inorder(root.left)
			println(root.key)
			inorder(root.right)
		}
	}
}