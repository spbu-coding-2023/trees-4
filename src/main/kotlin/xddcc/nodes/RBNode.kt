package xddcc.nodes

class RBNode<K: Comparable<K>, V>(
    val key: K,
    val value: V,
    var red: Boolean = true,
    var right: RBNode<K, V>? = null,
    var left: RBNode<K, V>? = null,
): Comparable<RBNode<K, *>> {
    override fun compareTo(other: RBNode<K, *>) = this.key.compareTo(other.key)
}