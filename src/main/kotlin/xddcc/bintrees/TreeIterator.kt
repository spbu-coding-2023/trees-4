package xddcc.bintrees

import xddcc.nodes.RBNode
import kotlin.collections.ArrayDeque

class TreeIterator<K: Comparable<K>, V>(private val root: RBNode<K, V>?): Iterator<K>{
    private val stack: ArrayDeque<RBNode<K, V>> = ArrayDeque()
    init {
        root?.let { treeToStack(root) }
    }

    private fun treeToStack(curNode: RBNode<K, V>) {
        val leftNode = curNode.left
        val rightNode = curNode.right
        leftNode?.let { treeToStack(leftNode) }
        stack.add(curNode)
        rightNode?.let { treeToStack(rightNode) }
    }

    override fun hasNext() = stack.isNotEmpty()

    override fun next() = stack.removeFirst().key
}