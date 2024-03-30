package treeLib.bintrees.interfaces

import treeLib.nodes.TreeNode

interface TreeBalancer<K : Comparable<K>, V, Node_T : TreeNode<K, V, Node_T>> {
	var root: Node_T?

	fun balancerAdd(treeBranch: ArrayDeque<Node_T>)

	fun balancerRemove(parent: Node_T?, removed: Node_T?)

	fun rotateRight(node: Node_T?, parent: Node_T?) {
		if (node == null) return

		val nodeLeft = node.left
		node.left = nodeLeft?.right
		nodeLeft?.right = node

		if (parent != null)
			parent.attach(nodeLeft)
		else /*only root doesn't have parent*/
			root = nodeLeft
	}

	fun rotateLeft(node: Node_T?, parent: Node_T?) {
		if (node == null) return

		val nodeRight = node.right
		node.right = nodeRight?.left
		nodeRight?.left = node

		if (parent != null)
			parent.attach(nodeRight)
		else /*only root doesn't have parent*/
			root = nodeRight
	}
}