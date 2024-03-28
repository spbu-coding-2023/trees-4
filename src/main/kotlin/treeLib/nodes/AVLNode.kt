package treeLib.nodes

class AVLNode<K : Comparable<K>, V>(
	key: K,
	value: V,
	right: AVLNode<K, V>? = null,
	left: AVLNode<K, V>? = null
) : TreeNode<K, V, AVLNode<K, V>>(key, value, right, left) {
	internal var height: Int = 1

	override fun attach(node: AVLNode<K, V>?): Boolean {
		TODO("Not yet implemented")
	}

	override fun moveOn(otherKey: K): AVLNode<K, V>? {
		TODO("Not yet implemented")
	}
}
