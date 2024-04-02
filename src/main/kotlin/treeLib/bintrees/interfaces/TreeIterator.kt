package treeLib.bintrees.interfaces

import treeLib.nodes.TreeNode

class TreeIterator<K : Comparable<K>, V, Node_T : TreeNode<K, V, Node_T>>(
	private val root: Node_T?,
	private val order: IterationOrder,
	): Iterator<Node_T> {
	private val stack: ArrayDeque<Node_T> = ArrayDeque()

	init {
		root?.let { treeToStack(root) }
	}

	private fun treeToStack(start: Node_T) {
		when (order) {
			IterationOrder.IN_ORDER -> inOrder(start)
			IterationOrder.PRE_ORDER -> preOrder(start)
			IterationOrder.POST_ORDER -> postOrder(start)
		}
	}

	private fun inOrder(curNode: Node_T) {
		val leftNode = curNode.left
		val rightNode = curNode.right
		leftNode?.let { inOrder(leftNode) }
		stack.add(curNode)
		rightNode?.let { inOrder(rightNode) }
	}

	private fun preOrder(curNode: Node_T) {
		val leftNode = curNode.left
		val rightNode = curNode.right
		stack.add(curNode)
		leftNode?.let { preOrder(leftNode) }
		rightNode?.let { preOrder(rightNode) }
	}

	private fun postOrder(curNode: Node_T) {
		val leftNode = curNode.left
		val rightNode = curNode.right
		leftNode?.let { postOrder(leftNode) }
		rightNode?.let { postOrder(rightNode) }
		stack.add(curNode)
	}

	override fun hasNext() = stack.isNotEmpty()

	override fun next() = stack.removeFirst()
}