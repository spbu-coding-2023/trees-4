package treeLib.nodes

class RBNode<K: Comparable<K>, V>(
    key: K,
    value: V,
    right: RBNode<K, V>? = null,
    left: RBNode<K, V>? = null,
    var isRed: Boolean = true,
): TreeNode<K, V, RBNode<K, V>>(key, value, right, left) {
    override fun attach(node: RBNode<K, V>?): Boolean {
        if (node == null) return false
        when {
            this > node -> left = node
            this < node -> right = node
            else -> return false
        }
        return true
    }

    override fun moveOn(otherKey: K) = when {
        key > otherKey -> left
        key < otherKey -> right
        else -> this
    }
}