package xddcc.nodes

class RBNode<K: Comparable<K>>(
    val key: K,
    var red: Boolean = true,
    var right: RBNode<K>? = null,
    var left: RBNode<K>? = null,
): Comparable<RBNode<K>> {
    override fun compareTo(other: RBNode<K>) = this.key.compareTo(other.key)
}