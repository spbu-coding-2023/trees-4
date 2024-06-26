package treeLib.bintrees.interfaces

import treeLib.nodes.TreeNode

abstract class BalancedTree<K : Comparable<K>, V, Node_T : TreeNode<K, V, Node_T>> : BinTree<K, V, Node_T>() {

    //We must provide parent, because don't have link to it in node
    protected fun rotateRight(node: Node_T?, parent: Node_T?) {
        if (node == null) return

        val nodeLeft = node.left

        node.left = nodeLeft?.right
        nodeLeft?.right = node

        if (parent != null) parent.attach(nodeLeft) else root = nodeLeft   //root doesn't have parent
    }

    protected fun rotateLeft(node: Node_T?, parent: Node_T?) {
        if (node == null) return

        val nodeRight = node.right
        node.right = nodeRight?.left
        nodeRight?.left = node

        if (parent != null) parent.attach(nodeRight) else root = nodeRight //root doesn't have parent
    }
}