package treeLib.bintrees.interfaces

import treeLib.nodes.TreeNode

class TreeIterator<K : Comparable<K>, V, Node_T : TreeNode<K, V, Node_T>>(private val root: Node_T?) :
	Iterator<Pair<K, V>> {
	private val stack: ArrayDeque<Node_T> = ArrayDeque()

	init {
		root?.let { treeToStack(root) }
	}

	private fun treeToStack(curNode: Node_T) {
		val leftNode = curNode.left
		val rightNode = curNode.right
		leftNode?.let { treeToStack(leftNode) }
		stack.add(curNode)
		rightNode?.let { treeToStack(rightNode) }
	}

	override fun hasNext() = stack.isNotEmpty()

	override fun next(): Pair<K, V> {
		val node = stack.removeFirst()
		return Pair(node.key, node.value)
	}
}