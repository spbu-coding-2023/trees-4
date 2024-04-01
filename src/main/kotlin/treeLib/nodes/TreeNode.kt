package treeLib.nodes

abstract class TreeNode<K : Comparable<K>, V, Node_T : TreeNode<K, V, Node_T>>(
	internal var key: K,
	internal var value: V,
	internal var right: Node_T? = null,
	internal var left: Node_T? = null,
) : Comparable<Node_T> {

	override fun compareTo(other: Node_T): Int = key.compareTo(other.key)

	override fun hashCode(): Int = Pair(key, value).hashCode()

	override fun equals(other: Any?): Boolean {
		return (other as? Node_T != null) && (key == other.key) && (value == other.value)
	}

	override fun toString(): String = Pair(key, value).toString()

	fun toPair(): Pair<K, V> = Pair(key, value)

	internal fun attach(node: Node_T?): Boolean {
		if (node == null) return false
		when {
			this > node -> left = node
			this < node -> right = node
			else -> return false
		}
		return true
	}

	internal fun moveOn(otherKey: K): Node_T? = when {
		key > otherKey -> left
		key < otherKey -> right
		else -> this as? Node_T
	}
}
