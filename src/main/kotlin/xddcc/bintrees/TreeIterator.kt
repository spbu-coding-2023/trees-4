package xddcc.bintrees

import xddcc.nodes.RBNode
import kotlin.collections.ArrayDeque

class TreeIterator<K: Comparable<K>>(val root: RBNode<K>?): Iterator<K>{
    val stack: ArrayDeque<RBNode<K>> = ArrayDeque()
    init {
        root?.let { treeToStack(root) }
    }

    private fun treeToStack(curNode: RBNode<K>) {
        val leftNode = curNode.left
        val rightNode = curNode.right
        leftNode?.let { treeToStack(leftNode) }
        stack.add(curNode)
        rightNode?.let { treeToStack(rightNode) }
    }

    override fun hasNext() = stack.isNotEmpty()

    override fun next() = stack.removeFirst().key
}