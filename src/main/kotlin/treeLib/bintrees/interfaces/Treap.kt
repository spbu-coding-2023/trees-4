package treeLib.bintrees.interfaces

import treeLib.bintrees.interfaces.BinTree
import treeLib.nodes.TreapNode
import java.util.*

class Treap<K : Comparable<K>, V: Comparable<V> > : BinTree<K, V, TreapNode<K, V>>() {
    override var root: TreapNode<K, V>? = null
    override var amountOfNodes = 0
    private val priorQ = PriorityQueue<TreapNode<K, V>>(compareByDescending { it.value })


    //Получив родителя и найдя куда он идёт, мы связываем и передаём сюда
    //Он должен здесь дойти до него по обычной итерации дерева
    //и параллельно должно менять maxLim и minLim, по этому мы составим новый массив
    //и начнём строить дерево с нуля где node это корень
    fun rebuildTree(node: TreapNode<K, V>) {
        var maxLim: TreapNode<K, V>? = root
        var minLim: TreapNode<K, V>? = root
        var curRoot = root
        var reachedNode = false
        for (curNode in priorQ){
            if(reachedNode == false) {
                if (curNode.value > curRoot?.value ?: curNode.value) { //N_U_L_L-S_A_F_E_T_Y
                    minLim = curRoot
                    curRoot = curNode?.right // Тут надо подумать
                } else if (curNode.value < curRoot?.value ?: curNode.value) {//N_U_L_L-S_A_F_E_T_Y
                    maxLim = curRoot
                    curRoot = curNode?.left
                } else reachedNode = true
            }
            else {

            }
        }
    }

    override fun remove(key: K): V? {
        if(root == null) return null
        if(root?.key == key && amountOfNodes == 1) {
            priorQ.remove(root)
            amountOfNodes -= 1
            root = null
        }
        return null
    }

    override fun add(key: K, priority: V): TreapNode<K, V>? {
        if(root == null) {
            root = TreapNode(key, priority)
            amountOfNodes += 1
            priorQ.add(root)
            return root
        }

        //Должны вызвать функцию X, отработать случаи когда куда добавляется новый ключ
        //у нас нету ничего
        //и далее работать в rebuildTree со случаями, когда там что-то всё ж имеется
        //потому что она так и задумана, что применяется, когда нужно перестроить поддерево
        //А ежли его нету, то и перестраивать ничего не надо

        return root
    }


    //Здесь мы находим родителя нашего нового ключа
    //А далее по значению ключа(больше иль меньше родителя) выясняем куда оно идёт
    //и работаем исходя из этого
    fun x(curNode: TreapNode<K, V>, parent: TreapNode<K, V>, node: TreapNode<K, V>): TreapNode<K, V>{
        if(curNode.value < node.value) {
            if(curNode.key < node.key) {
                //We need to rebuild and build new tree with node.key as a root
                //Иль попытайся придумать что-то со списками
            }
        }
        if(curNode.key > node.key) {
            //if(curNode.left == null) return parent
            return x(curNode.left ?: return parent, curNode, node)
        }
        else if(curNode.key < node.key) {
            //if(curNode.right == null) return parent
            return x(curNode.right ?: return parent, curNode, node)
        }
        else return curNode
    }
}