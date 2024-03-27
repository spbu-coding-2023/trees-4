package treeLib.nodes

class AVLNode<K : Comparable<K>, V>(
	key: K,
	value: V,
	right: AVLNode<K, V>? = null,
	left: AVLNode<K, V>? = null
) : TreeNode<K, V, AVLNode<K, V>>(key, value, right, left) {
	internal var parent: AVLNode<K, V>? = null
	private var height: Int = 1

	fun height(node: AVLNode<K, V>?) : Int {
		if (node == null) {
			return 0
		}
		return node.height
	}

	override fun attach(node: AVLNode<K, V>?): Boolean {
		TODO("Not yet implemented")
	}

	override fun moveOn(otherKey: K): AVLNode<K, V>? {
		TODO("Not yet implemented")
	}
}