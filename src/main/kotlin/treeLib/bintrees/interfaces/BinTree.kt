package treeLib.bintrees.interfaces

import treeLib.nodes.TreeNode

interface BinTree<K : Comparable<K>, V, Node_T : TreeNode<K, V, Node_T>> : Iterable<Pair<K, V>> {
	var root: Node_T?
	fun add(key: K, value: V)
	fun search(key: K): Node_T? {
		var curNode = root
		while (curNode != null)
			curNode = when {
				key > curNode.key -> curNode.right
				key < curNode.key -> curNode.left
				else -> return curNode
			}
		return null
	}

	override fun iterator(): Iterator<Pair<K, V>> = TreeIterator(root)
}
