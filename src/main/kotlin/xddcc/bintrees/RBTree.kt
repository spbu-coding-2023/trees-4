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
                curNode = when {
                    newNode > curNode -> curNode.right
                    newNode < curNode -> curNode.left
                    else -> curNode   //endless loop for equal keys
                }
            }
            val parent = treeBranch.first()
            if (newNode > parent) {
                parent.right = newNode
            } else {
                parent.left = newNode
            }
            treeBranch.addFirst(newNode)
        }
        balanceAdd(treeBranch)
    }

    private fun balanceAdd(treeBranch: ArrayDeque<RBNode<K>>) {
        var (son, parent, grandparent) =
            Triple(treeBranch.removeFirstOrNull(), treeBranch.removeFirstOrNull(), treeBranch.removeFirstOrNull())

        while (parent != null && parent.red) {
            if (parent === grandparent?.left) {
                val uncle = grandparent.right
                if (uncle?.red == true) {
                    parent.red = false
                    uncle.red = false
                    grandparent.red = true

                    son = grandparent
                    parent = treeBranch.removeFirstOrNull()
                    grandparent = treeBranch.removeFirstOrNull()
                } else /*uncle == black or null*/ {
                    if (son === parent.right) {
                        son = parent
                        parent = grandparent
                        grandparent = treeBranch.removeFirst()
                        rotateLeft(son, parent)
                    }
                    parent.red = false
                    grandparent.red = true
                    rotateRight(grandparent, treeBranch.firstOrNull())
                }
            } else if (parent === grandparent?.right){
                val uncle = grandparent.left
                if (uncle?.red == true) {
                    parent.red = false
                    uncle.red = false
                    grandparent.red = true

                    son = grandparent
                    parent = treeBranch.removeFirstOrNull()
                    grandparent = treeBranch.removeFirstOrNull()
                } else /*uncle == black or null*/ {
                    if (son === parent.left) {
                        son = parent
                        parent = grandparent
                        grandparent = treeBranch.removeFirstOrNull()
                        rotateRight(son, parent)
                    }
                    parent.red = false
                    if (grandparent != null) {//why safe call
                        grandparent.red = true
                        rotateLeft(grandparent, treeBranch.firstOrNull())
                    }
                }
            } else /*grandparent == null*/{
                parent.red = false
            }
        }

        root?.red = false
    }

    private fun rotateRight(node: RBNode<K>, parent: RBNode<K>?) {
        val nodeLeft = node.left
        node.left = nodeLeft?.right
        nodeLeft?.right = node

        if (parent != null) {
            if (node > parent)
                parent.right = nodeLeft
            else
                parent.left = nodeLeft
        } else /* node == root */ {
            root = nodeLeft
        }
    }

    private fun rotateLeft(node: RBNode<K>, parent: RBNode<K>?) {
        val nodeRight = node.right
        node.right = nodeRight?.left
        nodeRight?.left = node

        if (parent != null) {
            if (node > parent)
                parent.right = nodeRight
            else
                parent.left = nodeRight
        } else /* node == root */ {
            root = nodeRight
        }
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