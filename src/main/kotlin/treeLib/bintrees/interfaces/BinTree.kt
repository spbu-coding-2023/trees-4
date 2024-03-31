package treeLib.bintrees.interfaces

import treeLib.nodes.TreeNode

abstract class BinTree<K : Comparable<K>, V, Node_T : TreeNode<K, V, Node_T>> : Iterable<Pair<K, V>> {
	protected abstract var root: Node_T?
	protected abstract var amountOfNodes: Int

	abstract fun add(key: K, value: V): Node_T?

	fun findByKey(key: K): Node_T? {
		var curNode = root
		while (curNode != null)
			curNode = when {
				key > curNode.key -> curNode.right
				key < curNode.key -> curNode.left
				else -> return curNode
			}
		return null
	}

	abstract fun remove(key: K): V?

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

	open fun root(): Node_T? {
		return root
	}

	fun clear() {
		root?.let { root = null }
	}

	fun countNodes(): Int {
		return amountOfNodes
	}

	override fun iterator(): Iterator<Pair<K, V>> = TreeIterator(root)

	private fun countHeight(tNode: Node_T?): Int {
		if (tNode == null) return 0
		val lf = countHeight(tNode.left)
		val rg = countHeight(tNode.right)
		return kotlin.math.max(lf, rg) + 1
	}

	open fun height(): Int? {
		return countHeight(root)
	}
}
