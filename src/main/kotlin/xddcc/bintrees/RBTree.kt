package xddcc.bintrees

import xddcc.nodes.RBNode

class RBTree<K: Comparable<K>>: Iterable<K> {
    private var root: RBNode<K>? = null

    fun add(key: K): Boolean {
        val addNode = RBNode(key)
        if (root == null) {
            root = addNode
            return true
        }

        var curNode = root
        while (curNode != null) {
            when (addNode.compareTo(curNode)) {
                +1 -> {
                    if (curNode.right == null) {
                        curNode.right = addNode
                        return true
                    } else {
                        curNode = curNode.right
                    }
                }
                +0 -> return false
                -1 -> {
                    if (curNode.left == null) {
                        curNode.left = addNode
                        return true
                    } else {
                        curNode = curNode.left
                    }
                }
            }
        }

        return false
    }

    fun remove(key: K): Boolean {
        var prevNode: RBNode<K>? = null
        var removeNode = root
        while (removeNode != null) {
            when (key.compareTo(removeNode.key)){
                +1  -> {
                    prevNode = removeNode
                    removeNode = removeNode.right
                }
                +0  -> {
                    if (prevNode == null) {
                        root = null
                    } else {
                        TODO("correct remove")
                    }
                    return true
                }
                -1 -> {
                    prevNode = removeNode
                    removeNode = removeNode.left
                }
            }
        }

        return false
    }

    fun find(key: K): Boolean {
        var curNode = root
        while (curNode != null) {
            when (key.compareTo(curNode.key)){
                +1  -> curNode = curNode.right
                +0  -> return true
                -1 -> curNode = curNode.left
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

    override fun iterator(): Iterator<K> = TreeIterator(root)

}