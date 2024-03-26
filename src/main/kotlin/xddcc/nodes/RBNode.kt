package xddcc.nodes

class RBNode<K: Comparable<K>, V>(
    val key: K,
    val value: V,
    var isRed: Boolean = true,
    var right: RBNode<K, V>? = null,
    var left: RBNode<K, V>? = null,
): Comparable<RBNode<K, *>> {
    override fun compareTo(other: RBNode<K, *>) = this.key.compareTo(other.key)

    fun attach(node: RBNode<K, V>): Boolean {
        when {
            this > node -> this.left = node
            this < node -> this.right = node
            else -> return false
        }
        return true
    }
}