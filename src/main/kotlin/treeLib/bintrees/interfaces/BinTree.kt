package treeLib.bintrees.interfaces

import treeLib.nodes.BSTNode
import treeLib.nodes.TreeNode
import kotlin.math.max

interface BinTree<K : Comparable<K>, V, Node_T : TreeNode<K, V, Node_T>> : Iterable<Pair<K, V>> {
	var root: Node_T?
	var amountOfNodes: Int

	fun add(key: K, value: V): V?

	fun remove(key: K): V?

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

	fun max(): Pair<K, V>? {
		var curNode = root
		while (curNode?.right != null)
			curNode = curNode.right
		return if (curNode != null) Pair(curNode.key, curNode.value) else null
	}

	fun min(): Pair<K, V>? {
		var curNode = root
		while (curNode?.left != null)
			curNode = curNode.left
		return if (curNode != null) Pair(curNode.key, curNode.value) else null
	}

	fun root(): Pair<K, V>? = root?.let { Pair(it.key, it.value) }

	fun clear() {
		root?.let { root = null }
	}

	fun countNodes(): Int = amountOfNodes

	override fun iterator(): Iterator<Pair<K, V>> = TreeIterator(root)

	private fun hiddenHeight(tNode: Node_T?): Int {
		if(tNode == null) return 0
		val lf = hiddenHeight(tNode.left)
		val rg = hiddenHeight(tNode.right)
		return max(lf, rg) + 1
	}

	fun height(): Int {
		return hiddenHeight(root)
	}
}

