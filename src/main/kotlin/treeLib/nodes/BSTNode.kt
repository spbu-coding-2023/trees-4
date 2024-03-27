package treeLib.nodes

class BSTNode<K : Comparable<K>, V>(
	key: K,
	value: V,
	right: BSTNode<K, V>? = null,
	left: BSTNode<K, V>? = null
) : TreeNode<K, V, BSTNode<K, V>>(key, value, right, left) {
	internal var parent: AVLNode<K, V>? = null
	internal var height: Int = 1
	override fun attach(node: BSTNode<K, V>?): Boolean {
		TODO("Not yet implemented")
	}

	override fun moveOn(otherKey: K): BSTNode<K, V>? {
		TODO("Not yet implemented")
	}
}