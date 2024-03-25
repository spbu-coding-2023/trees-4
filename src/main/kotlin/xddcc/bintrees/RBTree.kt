package xddcc.bintrees

import xddcc.nodes.RBNode

class RBTree<K: Comparable<K>>: Iterable<K> {
    private var root: RBNode<K>? = null

    fun add(key: K) {
        val (uncle, dad, son) = addHelper(key)

        when {
            dad == null -> root = RBNode(key)
            son != null -> return   //Node already exist
            son == null -> if (dad.right == null) dad.right = RBNode(key) else dad.left = RBNode(key)
        }
    }

    private fun addHelper(key: K): Triple<RBNode<K>?, RBNode<K>?, RBNode<K>?> {
        var (uncle, dad, son) =
            Triple<RBNode<K>?, RBNode<K>?, RBNode<K>?>(null, null, root)
        while (son != null) {
            val keyCompare = key.compareTo(son.key)
            when {
                keyCompare > 0 -> {
                    uncle = dad
                    dad = son
                    son = son.left
                }
                keyCompare < 0 -> {
                    uncle = dad
                    dad = son
                    son = son.right
                }
                else -> return Triple(uncle, dad, son)
            }
        }
        return Triple(uncle, dad, son)
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