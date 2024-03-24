package xddcc.bintrees

import xddcc.nodes.RBNode

class RBTree<K: Comparable<K>> {
    private var root: RBNode<K>? = null

    fun add(key: K): Boolean {
        val addNode = RBNode(key)
        if (root == null) {
            root = addNode
            return true
        }

        var curNode = root
        while (curNode != null) {
            when {
                addNode > curNode -> {
                    if (curNode.right == null) {
                        curNode.right = addNode
                        return true
                    } else {
                        curNode = curNode.right
                    }
                }
                addNode < curNode -> {
                    if (curNode.left == null) {
                        curNode.left = addNode
                        return true
                    } else {
                        curNode = curNode.left
                    }
                }
                addNode == curNode -> return false
            }
        }

        return false
    }

    fun max(): K? {
        var maxNode = root
        while (maxNode?.right != null) maxNode = maxNode.right
        return maxNode?.key
    }

    fun min(): K? {
        var minNode = root
        while (minNode?.left != null) minNode = minNode.left
        return minNode?.key
    }

    fun root(): K? = root?.key

    fun clear(): Unit { root = null }

}