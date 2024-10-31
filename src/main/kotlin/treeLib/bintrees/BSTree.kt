package treeLib.bintrees

import treeLib.bintrees.interfaces.BinTree
import treeLib.nodes.BSTNode

class BSTree<K : Comparable<K>, V> : BinTree<K, V, BSTNode<K, V>>() {
    override var root: BSTNode<K, V>? = null
    override var amountOfNodes = 0

    fun addPairs(vararg pairs: Pair<K, V>): Boolean {
        var flag = true

        for (pair in pairs) {
            if (this.add(pair.first, pair.second) == null) flag = false
        }

        return flag
    }

    fun removePairs(vararg keys: K): Boolean {
        var flag = true

        for (key in keys) {
            if (this.remove(key) == null) flag = false
        }

        return flag
    }

    override fun add(key: K, value: V): BSTNode<K, V>? {
        if (root == null) {
            this.root = BSTNode(key, value)
            this.amountOfNodes += 1
            return root
        }

        var curNode = this.root

        while (curNode != null && curNode.key != key) {
            curNode = if (key > curNode.key) {
                if (curNode.right == null) {
                    curNode.right = BSTNode(key, value)
                    this.amountOfNodes += 1

                    return curNode.right
                }

                curNode.right
            }

            else{
                if (curNode.left == null) {
                    curNode.left = BSTNode(key, value)
                    this.amountOfNodes += 1

                    return curNode.left
                }

                curNode.left
            }
        }
        return null
    }

    override fun remove(key: K): V? {
        var count = 0
        val curNode: BSTNode<K, V>?
        var parent = this.findParent(key)

        if (this.root?.key == key) parent = root?.let { BSTNode(it.key, it.value, it) }
        if (parent == null) return null

        curNode = if (parent.right != null && parent.right?.key == key) parent.right
        else parent.left

        // Hier wir count amount of children
        if (curNode?.right != null) count++
        if (curNode?.left != null) count++

        //We have no children
        if (count == 0) {

            if (parent.right == curNode) {
                if (curNode == root) this.root = null
                else parent.right = null
            }

            else parent.left = null
        }

        //We've got ein child
        else if (count == 1) {
            if (curNode?.left == null) {
                if (parent.right == curNode) {
                    if (curNode == root) this.root = root?.right
                    else parent.right = curNode?.right
                } else parent.left = curNode?.right
            }

            else {
                if (parent.right == curNode) {
                    if (curNode == root) this.root = this.root?.left
                    else parent.right = curNode.left
                } else parent.left = curNode.left
            }
        }

        //Wir haben zwei kinder
        else {
            var child: BSTNode<K, V>? = curNode?.right
            var parent_child = curNode

            while (child?.left != null) {
                if (child.left?.left == null) parent_child = child
                child = child.left
            }

            if(child == null) return null
            curNode?.key = child.key
            curNode?.value = child.value

            if (curNode?.right != child) parent_child?.left = child.right
            else curNode.right = child.right
        }

        this.amountOfNodes -= 1
        return curNode?.value
    }


    internal fun findParent(key: K): BSTNode<K, V>? {
        var parent = this.root
        if (root?.key == key) return null

        while (parent != null && (parent.right != null || parent.left != null)) {
            if (parent.right != null && parent.right?.key == key || parent.left != null && parent.left?.key == key) return parent

            parent = if (key > parent.key) parent.right
            else parent.left
        }

        return null
    }


    fun deleteSubTree(key: K): Boolean {
        val parent: BSTNode<K, V> = this.findParent(key) ?: return false

        if (parent.right != null && parent.right?.key == key) parent.right = null
        else parent.left = null

        var nodes = 0
        for (i in this) nodes++
        amountOfNodes = nodes

        return true
    }


    fun getSubTree(key: K): BSTree<K, V>? {
        val parent: BSTNode<K, V> = this.findParent(key) ?: return null
        val childTree: BSTree<K, V> = BSTree()
        val child: BSTNode<K, V>?

        if (parent.right != null && parent.right?.key == key) {
            child = childTree.add(key, parent.right?.value ?: return null)
            child?.right = parent.right?.right
            child?.left = parent.right?.left
        }

        else {
            child = parent.left?.let { childTree.add(key, it.value)}
            child?.right = parent.left?.right
            child?.left = parent.left?.left
        }

        var nodes = 0
        for (i in childTree) nodes++
        childTree.amountOfNodes = nodes

        return childTree
    }
}
