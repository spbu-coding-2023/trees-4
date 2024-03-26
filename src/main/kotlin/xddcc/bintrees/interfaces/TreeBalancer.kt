package xddcc.bintrees.interfaces

import xddcc.nodes.RBNode
import xddcc.nodes.TreeNode

interface TreeBalancer<K: Comparable<K>, V, Node_T: TreeNode<K, V, Node_T>> {
    var root: RBNode<K, V>?

    fun balancerAdd(treeBranch: ArrayDeque<RBNode<K, V>>)

    fun balancerRemove(treeBranch: ArrayDeque<RBNode<K, V>>)

    fun rotateRight(node: RBNode<K, V>, parent: RBNode<K, V>?) {
        val nodeLeft = node.left
        node.left = nodeLeft?.right
        nodeLeft?.right = node

        if (parent != null)
            parent.attach(nodeLeft)
        else /*only root doesn't have parent*/
            root = nodeLeft
    }

    fun rotateLeft(node: RBNode<K, V>, parent: RBNode<K, V>?) {
        val nodeRight = node.right
        node.right = nodeRight?.left
        nodeRight?.left = node

        if (parent != null)
            parent.attach(nodeRight)
        else /*only root doesn't have parent*/
            root = nodeRight
    }
}