package treeLib.nodes

class BSTNode<K : Comparable<K>, V>(
	key: K,
	value: V,
	right: BSTNode<K, V>? = null,
	left: BSTNode<K, V>? = null
) : TreeNode<K, V, BSTNode<K, V>>(key, value, right, left){
	internal fun isThereChild(): Boolean{
		if(this.left != null || this.right != null) return true
		else return false
	}
}
