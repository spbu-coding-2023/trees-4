package treeLib.nodes

class TreapNode<K : Comparable<K>, V: Comparable<V>>(
    key: K,
    value: V,
    right: TreapNode<K, V>? = null,
    left: TreapNode<K, V>? = null
) : TreeNode<K, V, TreapNode<K, V>>(key, value, right, left)