package xddcc.nodes

class RBNode<K: Comparable<K>>(
    val key: K,
    var right: RBNode<K>? = null,
    var left: RBNode<K>? = null,
): Comparable<RBNode<K>> {
    enum class Color {RED, BLACK}
    var color = Color.RED

    override fun compareTo(other: RBNode<K>): Int {
        return key.compareTo(other.key)
    }
}