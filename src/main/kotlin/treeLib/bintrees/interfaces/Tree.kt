package treeLib.bintrees.interfaces

import treeLib.nodes.TreeNode

interface Tree<K : Comparable<K>, V, Node_T: TreeNode<K, V, Node_T>>: Iterable<Pair<K, V>> {
	var root: Node_T?
	fun add(key: K, value: V)
	override fun iterator(): Iterator<Pair<K, V>> = TreeIterator(root)
}
