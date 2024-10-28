package treeLib.bintrees.interfaces

import treeLib.bintrees.BSTree
import treeLib.bintrees.interfaces.BinTree
import treeLib.nodes.BSTNode
import treeLib.nodes.TreapNode
import java.util.*

class Treap<K : Comparable<K>, V: Comparable<V> > : BinTree<K, V, TreapNode<K, V>>() {
    override var root: TreapNode<K, V>? = null
    override var amountOfNodes = 0
    private var subTree = PriorityQueue<TreapNode<K, V>>(compareByDescending { it.value })


    fun collectNodes(node: TreapNode<K, V>) {
        subTree.add(node)
        //Есть ощущения, что то, что снизу, не совсем корректно в плане NULL-SAFETY
        if(node.right != null) node.right?.let{ collectNodes(it) }
        if(node.left != null) node.left?.let{ collectNodes(it) }
    }

    fun buildSubTree(): TreapNode<K, V> {
        var baum = Treap<K, V>()
        for(node in subTree) baum.add(node.key, node.value)
        baum.root?.let { baum.collectNodes(it) }
        subTree = baum.subTree  //После такого оно сохранит свойство автоматической сортировки иль як?
        return subTree.first()
        /*
        subTree.clean()
        baum.root?.let { return it }
         */
        //Может быть можно не собирать массив, а просто взять корень и уже с ним работать?
    }

    internal fun findParent(key: K): TreapNode<K, V>? {
        var parent = this.root
        if (root?.key == key) return null

        while (parent != null && (parent.right != null || parent.left != null)) {
            if (parent.right != null && parent.right?.key == key || parent.left != null && parent.left?.key == key) return parent

            parent = if (key > parent.key) parent.right
            else parent.left
        }

        return null
    }

    override fun remove(key: K): V? {
        if(root == null) return null
        /*if(root?.key == key && amountOfNodes == 1) {
            amountOfNodes -= 1
            root = null
        }*/

        val parent = this.findParent(key)
        var count = 0
        val curNode = if (parent?.right != null && parent.right?.key == key) parent.right
        else parent?.left
        if(curNode?.left != null) count++
        if(curNode?.right != null) count++

        if(count == 0) {
            if(parent?.right == curNode) parent?.right = null
            else parent?.left == null
        }
        if(count == 1) {
            if(parent?.right == curNode) parent?.right = curNode?.right ?: curNode?.left
            else parent?.left = curNode?.right ?: curNode?.left
        }
        if(count == 2) {
            curNode?.right?.let { collectNodes(it) }
            curNode?.left?.let { collectNodes(it) }
            val result = buildSubTree()
            if(parent == root) root = result
            else if(parent?.right == curNode) parent?.right = result
            else parent?.left == result
        }
        return null
    }

    override fun add(key: K, priority: V): TreapNode<K, V>? {
        val newNode = TreapNode(key, priority)
        if(root == null) {
            root = newNode
            amountOfNodes += 1
            return root
        }
        var parent: TreapNode<K, V>? = null
        root?.let { parent = x(it, it, newNode) }

        parent?.let{
            if(it.key < key) {
                if(it.right == null) it.right = newNode
                else {
                    newNode.right = it.right
                    collectNodes(newNode)
                    it.right = buildSubTree()
                }
            }
            else if(it.key < key)( //Не забудь подумать о случае, когда добавленный ключ вже существует
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


    fun x(curNode: TreapNode<K, V>, parent: TreapNode<K, V>, node: TreapNode<K, V>): TreapNode<K, V>{
        if(curNode.value < node.value) {
            if(curNode == parent) return node
            return parent
        }
        if(curNode.key > node.key) {
            if(curNode.left == null) return curNode
            return x(curNode.left ?: return parent, curNode, node)
        }
        else if(curNode.key < node.key) {
            if(curNode.right == null) return curNode
            return x(curNode.right ?: return parent, curNode, node)
        }
        else return curNode //Не забыть про этот случай
    }
}