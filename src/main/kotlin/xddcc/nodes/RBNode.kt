package xddcc.nodes

class RBNode<K: Comparable<K>> (
    var value: Any,
    val key: K,
    var right: RBNode<K>? = null,
    var left: RBNode<K>? = null,
) {
    enum class Color {RED, BLACK}
    var color = Color.RED
}