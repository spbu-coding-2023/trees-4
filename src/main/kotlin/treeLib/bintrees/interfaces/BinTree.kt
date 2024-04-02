package treeLib.bintrees.interfaces

import treeLib.nodes.TreeNode

abstract class BinTree<K : Comparable<K>, V, Node_T : TreeNode<K, V, Node_T>> : Iterable<Node_T> {
	protected abstract var root: Node_T?
	protected abstract var amountOfNodes: Int

	abstract fun add(key: K, value: V): Node_T?

	abstract fun remove(key: K): V?

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

	open fun changeVal(key: K, newValue: V): V? {
		var curNode = root
		while (curNode != null)
			curNode = when {
				key > curNode.key -> curNode.right
				key < curNode.key -> curNode.left
				else -> {
					curNode.value = newValue
					return newValue
				}
			}
		return null
	}

	fun max(): Node_T? {
		var curNode = root
		while (curNode?.right != null)
			curNode = curNode.right
		return curNode
	}

	fun min(): Node_T? {
		var curNode = root
		while (curNode?.left != null)
			curNode = curNode.left
		return curNode
	}

	open fun root(): Node_T? {
		return root
	}

	fun clear() {
		root?.let { root = null }
		amountOfNodes = 0
	}

	fun countNodes(): Int {
		return amountOfNodes
	}

	/**
	 * basic In-order iterator
	 */
	override fun iterator(): Iterator<Node_T> = TreeIterator(root, IterationOrder.IN_ORDER)

	fun preOrderIterator(): Iterator<Node_T> = TreeIterator(root, IterationOrder.PRE_ORDER)

	fun postOrderIterator(): Iterator<Node_T> = TreeIterator(root, IterationOrder.POST_ORDER)


	private fun countHeight(tNode: Node_T?): Int {
		if (tNode == null) return 0
		val leftChild = countHeight(tNode.left)
		val rightChild = countHeight(tNode.right)
		return kotlin.math.max(leftChild, rightChild) + 1
	}


	open fun height(): Int? {
		return countHeight(root)
	}
}
