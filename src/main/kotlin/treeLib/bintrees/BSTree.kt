package treeLib.bintrees

import treeLib.bintrees.interfaces.BinTree
import treeLib.nodes.BSTNode

class BSTree<K : Comparable<K>, V> : BinTree<K, V, BSTNode<K, V>>() {
	override var root: BSTNode<K, V>? = null
	override var amountOfNodes = 1


	fun addPairs(keys: List<K>, values: List<V>): Boolean {
		if (keys.size != values.size) return false
		for (curKey in keys.indices) {
			this.add(keys[currentKey], values[currentKey])
		}
		return true
	}

	override fun add(key: K, value: V): Node_T? {
		if (root == null) this.root = BSTNode(key, value)
		var curNode = this.root
		while (curNode != null) {
			if (key > curNode.key) {
				if (curNode.right == null) {
					curNode.right = BSTNode(key, value)
					return curNode.right
				}
				curNode = curNode.right
			} else if (key < curNode.key) {
				if (curNode.left == null) {
					curNode.left = BSTNode(key, value)
					return curNode.left
				}
				curNode = curNode.left
			} else return null
		}
		this.amountOfNodes += 1
		return null
	}

	override fun remove(key: K): V? {
		var count = 0
		var curNode: BSTNode<K, V>? = null
		var parent = this.findParent(key)
		if(parent == null) return null
		if(parent.right != null && parent.right.key == key) curNode = parent.right
		else curNode = parent.left
		if (curNode.right != null) count++
		if (curNode.left != null) count++
		if (count == 0) {
			if (parent.right === curNode) parent.right = null
			else parent.left = null
		} else if (count == 1) {
			if (curNode.left == null) {
				if (parent.right === x) parent_x.right = curNode.right
				else parent.left = curNode.right
			} else {
				if (parent.right === curNode) parent.right = curNode.left
				else parent.left = curNode.left
			}
		} else {
			var child = curNode.right
			var parent_child = curNode
			while (child!!.left != null) {
				if (child.left!!.left == null) parent_child = child
				child = child.left
			}
			curNode.key = child.key
			curNode.value = child.value
			parent_child!!.left = child.right
		}
		this.amountOfNodes -= 1
		return curNode.value
	}


	fun changeVal(key: K, newValue: V): Boolean {
		var curNode = this.root
		while (curNode != null) {
			curNode = if (key > curNode.key) curNode.right
			else if (key < curNode.key) curNode.left
			else {
				curNode.value = newValue
				return true
			}
		}
		return false
	}

	fun findParent(key: K): Node_T?{
		var parent = this.root
		if(parent == null || root.key == key) return null
		while(parent.isThereChild == true){
			if( (parent.right != null && parent.right.key == key) || (parent.left != null && parent.left.key == key)) return parent
			else{
				if(key > parent.key){
					if(parent.right == null) return null
					parent = parent.right
				}
				else{
					if(parent.left == null) return null
					parent = parent.left
				}
			}
		}
	}

	fun deleteSubTree(key: K): Boolean{
		var parent: BSTNode<K, V>? = this.findParent(key)
		if(parent == null) return false
		if(parent.right != null && parent.right.key == key) parent.right = null
		else parent.left = null
		return true
	}

	fun getSubTree(key: K): BSTree<K, V>?{
		var parent: BSTNode<K, V>? = this.findParent(key)
		var child: BSTree<K, V> = BSTree()
		if(parent == null) return null
		if(parent.right != null && parent.right.key == key) child.add(key, parent.right.value)
		else child.add(key, parent.left.value)
		return child
	}
}
