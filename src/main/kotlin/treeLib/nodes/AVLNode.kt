package treeLib.nodes

class AVLNode<K : Comparable<K>, V>(
    key: K,
    value: V,
    right: AVLNode<K, V>? = null,
    left: AVLNode<K, V>? = null
) : TreeNode<K, V, AVLNode<K, V>>(key, value, right, left) {
    internal var height: Int = 1
}
