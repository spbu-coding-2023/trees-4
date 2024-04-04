package treeLib.nodes

class RBNode<K : Comparable<K>, V>(
	key: K,
	value: V,
	right: RBNode<K, V>? = null,
	left: RBNode<K, V>? = null,
	internal var isRed: Boolean = true,
) : TreeNode<K, V, RBNode<K, V>>(key, value, right, left)