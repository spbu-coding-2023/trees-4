package xddcc.bintrees

import xddcc.nodes.RBNode

class RBTree<K: Comparable<K>>: Iterable<K> {
    private var root: RBNode<K>? = null

    fun add(key: K) {
        val treeBranch = ArrayDeque<RBNode<K>>()
        val newNode = RBNode(key)
        if (root == null) {
            root = newNode
        } else {
            var curNode = root
            while (curNode != null) {
                treeBranch.addFirst(curNode)
                when {
                    newNode > curNode -> curNode = curNode.right
                    newNode < curNode -> curNode = curNode.left
                    else -> TODO("private fun replace(father, new)")
                }
            }
            val father = treeBranch.first()
            if (newNode > father) {
                father.right = newNode
            } else {
                father.left = newNode
            }
        }

        balanceAdd(treeBranch)
    }

    private fun balanceAdd(treeBranch: ArrayDeque<RBNode<K>>) {
        var (son, parent, grandparent) =
            Triple(treeBranch.removeFirst(), treeBranch.removeFirst(), treeBranch.removeFirst())

        while (parent.red) {
            if (parent === grandparent.left) {
                val uncle = grandparent.right
                if (uncle?.red == true) {
                    parent.red = false
                    uncle.red = false
                    grandparent.red = true

                    son = grandparent
                    parent = treeBranch.removeFirst()
                    grandparent = treeBranch.removeFirst()
                } else {
                    if (son === parent.right) {
                        son = parent
                        parent = grandparent
                        grandparent = treeBranch.removeFirst()
                        TODO("leftRotate(son)")
                    }
                    parent.red = false
                    grandparent.red = true
                    TODO("rightRotate(grandfather)")
                }
            } else if (parent == grandparent.right){
                val uncle = grandparent.left
                if (uncle?.red == true) {
                    parent.red = false
                    uncle.red = false
                    grandparent.red = true

                    son = grandparent
                    parent = treeBranch.removeFirst()
                    grandparent = treeBranch.removeFirst()
                } else {
                    if (son === parent.left) {
                        son = parent
                        parent = grandparent
                        grandparent = treeBranch.removeFirst()
                        TODO("rightRotate(son)")
                    }
                    parent.red = false
                    grandparent.red = true
                    TODO("leftRotate(grandfather)")
                }
            }
        }

        root?.red = false
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