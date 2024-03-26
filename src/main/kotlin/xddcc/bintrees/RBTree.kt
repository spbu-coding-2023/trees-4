package xddcc.bintrees

import xddcc.bintrees.interfaces.BinTree
import xddcc.bintrees.interfaces.TreeBalancer
import xddcc.nodes.RBNode

class RBTree<K: Comparable<K>, V> : BinTree<K, V, RBNode<K, V>>, TreeBalancer<K, V, RBNode<K, V>> {
    override var root: RBNode<K, V>? = null
    override var size: Int = 0

    override fun add(key: K, value: V): Pair<K, V>? {
        val treeBranch = ArrayDeque<RBNode<K, V>>()
        val newNode = RBNode(key, value)
        if (root == null) {
            root = newNode
        } else {
            var curNode = root
            while (curNode != null) {
                treeBranch.addFirst(curNode)
                val nextNode = when {
                    newNode > curNode -> curNode.right
                    newNode < curNode -> curNode.left
                    else -> curNode
                }
                if (nextNode === curNode) {   //replace
                    val parent = treeBranch.first()
                    newNode.left = curNode.left
                    newNode.right = curNode.right
                    newNode.isRed = curNode.isRed
                    if (newNode > parent) {
                        parent.right = newNode
                    } else {
                        parent.left = newNode
                    }
                } else {
                    curNode = nextNode
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
        balancerAdd(treeBranch)
        return Pair(key, value)
    }

    override fun balancerAdd(treeBranch: ArrayDeque<RBNode<K, V>>) {
        var (son, parent, grandparent) =
            Triple(treeBranch.removeFirstOrNull(), treeBranch.removeFirstOrNull(), treeBranch.removeFirstOrNull())

        while (parent != null && parent.isRed) {
            if (parent === grandparent?.left) {
                val uncle = grandparent.right
                if (uncle?.isRed == true) {
                    parent.isRed = false
                    uncle.isRed = false
                    grandparent.isRed = true

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
                    parent.isRed = false
                    grandparent.isRed = true
                    rotateRight(grandparent, treeBranch.firstOrNull())
                }
            } else if (parent === grandparent?.right){
                val uncle = grandparent.left
                if (uncle?.isRed == true) {
                    parent.isRed = false
                    uncle.isRed = false
                    grandparent.isRed = true

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
                    parent.isRed = false
                    if (grandparent != null) {//why safe call
                        grandparent.isRed = true
                        rotateLeft(grandparent, treeBranch.firstOrNull())
                    }
                }
            } else /*grandparent == null*/{
                parent.isRed = false
            }
        }

        root?.isRed = false
    }

    override fun remove(key: K): Pair<K, V>? {
        var prevNode: RBNode<K, V>? = null
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
                    TODO()
                }
                -1 -> {
                    prevNode = removeNode
                    removeNode = removeNode.left
                }
            }
        }

        TODO()
    }

    override fun balancerRemove(treeBranch: ArrayDeque<RBNode<K, V>>) {
        TODO("Not yet implemented")
    }
}