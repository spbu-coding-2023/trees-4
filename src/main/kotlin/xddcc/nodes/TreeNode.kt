package xddcc.nodes

abstract class TreeNode<K: Comparable<K>, V, Node_T: TreeNode<K, V, Node_T>>(
    val key: K,
    val value: V,
    var right: Node_T? = null,
    var left: Node_T? = null,
): Comparable<Node_T> {
    override fun compareTo(other: Node_T) = key.compareTo(other.key)

    override fun hashCode() = Pair(key, value).hashCode()

    override fun equals(other: Any?): Boolean {
        val node = other as? Node_T
        return (node != null) && (Pair(key, value) == Pair(node.key, node.value))
    }

    fun attach(node: Node_T): Boolean {
        when {
            this > node -> left = node
            this < node -> right = node
            else -> return false
        }
        return true
    }
}