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


}