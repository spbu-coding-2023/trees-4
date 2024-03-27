package treeLib.bintrees

import treeLib.bintrees.interfaces.BinTree
import treeLib.nodes.BSTNode

class BSTree<K : Comparable<K>, V> : BinTree<K, V, BSTNode<K, V>> {
	override fun add(key: K, value: V): V? {
		TODO("Not yet implemented")
	}

	override var root: BSTNode<K, V>?
		get() = TODO("Not yet implemented")
		set(value) {}
	override var amountOfNodes: Int
		get() = TODO("Not yet implemented")
		set(value) {}
}