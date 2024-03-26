package treeLib.nodes

abstract class TreeNode<K : Comparable<K>, V, Node_T : TreeNode<K, V, Node_T>>(
	internal val key: K,
	internal val value: V,
	internal var right: Node_T? = null,
	internal var left: Node_T? = null,
) : Comparable<Node_T> {

	override fun compareTo(other: Node_T) = key.compareTo(other.key)

	override fun hashCode() = Pair(key, value).hashCode()

	override fun equals(other: Any?): Boolean {
		val node = other as? Node_T
		return (node != null) && (Pair(key, value) == Pair(node.key, node.value))
	}

	fun attach(node: Node_T?): Boolean {
		if (node == null) return false
		when {
			this > node -> left = node
			this < node -> right = node
			else -> return false
		}
		return true
	}
}