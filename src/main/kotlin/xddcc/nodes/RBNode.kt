package xddcc.nodes

class RBNode<K: Comparable<K>, V> (
    var value: V,
    val key: K,
    var right: RBNode<K, *>? = null,
    var left: RBNode<K, *>? = null,
): Comparable<RBNode<K, *>> {
    enum class Color {RED, BLACK}
    var color = Color.RED

    override fun compareTo(other: RBNode<K, *>): Int {
        return when {
            key > other.key -> 1
            key < other.key -> -1
            else -> 0
        }
    }
}