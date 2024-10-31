package treeLib.bintrees

import treeLib.bintrees.interfaces.BinTree
import treeLib.nodes.TreapNode
import java.util.*

class Treap<K : Comparable<K>, V: Comparable<V> > : BinTree<K, V, TreapNode<K, V>>() {
    override var root: TreapNode<K, V>? = null
    override var amountOfNodes = 0
    private var subTree = PriorityQueue<TreapNode<K, V>>(compareByDescending { it.value })


    fun collectNodes(node: TreapNode<K, V>) {
        subTree.add(node)
        if(node.right != null) node.right?.let{ collectNodes(it) }
        if(node.left != null) node.left?.let{ collectNodes(it) }
    }

    fun buildSubTree(): TreapNode<K, V>? {
        val baum = Treap<K, V>()
        for(node in subTree) baum.add(node.key, node.value)
        subTree.clear()
        return baum.root()
    }

    fun isThereSuckKey(key: K): TreapNode<K, V>? {
        var curNode = root

        while(curNode != null){
            curNode.let{
                if(it.key < key) curNode = it.right
                else if(it.key > key) curNode = it.left
                else return curNode
            }
        }


        return curNode
    }

    override fun remove(key: K): V? {
        val curNode = isThereSuckKey(key) ?: return null
        var parent: TreapNode<K, V>? = null
        root?.let { parent = findParent(it, it, curNode) }

        var count = 0
        if(curNode.left != null) count++
        if(curNode.right != null) count++


        if(count == 0) {
            if(curNode == root) root = null
            else if(parent?.right == curNode) parent?.right = null
            else parent?.left = null
        }

        else if(count == 1) {
            if(curNode == root) root = root?.right ?: root?.left
            else if(parent?.right == curNode) parent?.right = curNode.right ?: curNode.left
            else parent?.left = curNode.right ?: curNode.left
        }

        else if(count == 2) {
            curNode.right?.let { collectNodes(it) }
            curNode.left?.let { collectNodes(it) }
            val result = buildSubTree()
            if(curNode == root) root = result
            else if(parent?.right == curNode) parent?.right = result
            else parent?.left = result
        }


        return null //Why return anything else?
    }

    override fun add(key: K, priority: V): TreapNode<K, V>? {
        if(isThereSuckKey(key) != null) return null
        val newNode = TreapNode(key, priority)

        if(root == null) {
            root = newNode
            amountOfNodes += 1

            return root
        }

        var parent: TreapNode<K, V>? = null
        root?.let { parent = findParent(it, it, newNode) }

        parent?.let{
            if(it.key < key) {
                if(it.right == null) it.right = newNode
                else {
                    newNode.right = it.right
                    collectNodes(newNode)
                    it.right = buildSubTree()
                }
            }

            else if(it.key > key)(
                if(it.left == null) it.left = newNode
                else{
                    newNode.left = it.left
                    collectNodes(newNode)
                    it.left = buildSubTree()
                }
            )

            else {
                newNode.right = root
                collectNodes(newNode)
                root = buildSubTree()
            }

            amountOfNodes += 1

            return newNode
        }


        return root
    }


    fun findParent(curNode: TreapNode<K, V>, parent: TreapNode<K, V>, node: TreapNode<K, V>): TreapNode<K, V>{
        if(curNode.value <= node.value) {
            if(curNode == parent && node != root) return node
            return parent
        }

        if(curNode.key > node.key) return findParent(curNode.left ?: return curNode, curNode, node)
        else if(curNode.key < node.key) return findParent(curNode.right ?: return parent, curNode, node)
        else return curNode
    }
}