package treeLib.bintrees

import treeLib.bintrees.interfaces.BinTree
import treeLib.nodes.BSTNode
import treeLib.nodes.TreeNode

class BSTree<K : Comparable<K>, V> : BinTree<K, V, BSTNode<K, V>>() {
	override var root: BSTNode<K, V>? = null
	override var amountOfNodes = 0

	fun addPairs(vararg pairs: Pair<K, V>): Boolean {
		for(pair in pairs){
			if( this.add(pair.first, pair.second) == null ) return false
		}
		return true
	}

	fun removePairs(vararg keys: K): Boolean{
		for(key in keys){
			if( this.remove(key) == null ) return false
		}
		return true
	}

	override fun add(key: K, value: V): BSTNode<K, V>? {
		if (root == null){
			this.root = BSTNode(key, value)
			this.amountOfNodes += 1
			return root
		}
		var curNode = this.root
		while (curNode != null) {
			if (key > curNode.key) {
				if (curNode.right == null) {
					curNode.right = BSTNode(key, value)
					this.amountOfNodes += 1
					return curNode.right
				}
				curNode = curNode.right
			} else if (key < curNode.key) {
				if (curNode.left == null) {
					curNode.left = BSTNode(key, value)
					this.amountOfNodes += 1
					return curNode.left
				}
				curNode = curNode.left
			} else return null
		}
		return null
	}

	override fun remove(key: K): V? {
		var count = 0
		var curNode: BSTNode<K, V>? = null
		var parent = this.findParent(key)
		if(this.root?.key == key) parent = BSTNode(root!!.key, root!!.value, root)
		if(parent == null) return null
		curNode = if(parent.right != null && parent.right?.key == key) parent.right
		else parent.left
		if (curNode?.right != null) count++
		if (curNode?.left != null) count++
		if (count == 0) {
			if (parent.right === curNode){
				if(curNode == root) this.root = null
				else parent.right = null
			}
			else parent.left = null
		} else if (count == 1) {
			if (curNode?.left == null) {
				if (parent.right === curNode){
					if(curNode == root) this.root = root?.right
					else parent.right = curNode?.right
				}
				else parent.left = curNode?.right
			} else {
				if (parent.right === curNode){
					if(curNode == root) this.root = this.root?.left
					else parent.right = curNode.left
				}
				else parent.left = curNode.left
			}
		} else {
			var child = curNode?.right
			var parent_child = curNode
			while (child!!.left != null) {
				if (child.left!!.left == null) parent_child = child
				child = child.left
			}
			curNode?.key = child.key
			curNode?.value = child.value
			if(curNode?.right != child) parent_child!!.left = child.right
			else curNode.right = child.right
		}
		this.amountOfNodes -= 1
		return curNode?.value
	}


	fun findParent(key: K): BSTNode<K, V>? {
		var parent = this.root
		if (parent == null || root!!.key == key) return null
		while (parent?.isThereChild() == true) {
			if ((parent.right != null && parent.right?.key == key) || (parent.left != null && parent.left?.key == key)) return parent
			else {
				if (key > parent.key) {
					if (parent.right == null) return null
					parent = parent.right
				} else {
					if (parent.left == null) return null
					parent = parent.left
				}
			}
		}
		return null
	}


	fun deleteSubTree(key: K): Boolean{
		val parent: BSTNode<K, V>? = this.findParent(key)
		if(parent == null) return false
		if(parent.right != null && parent.right?.key == key) parent.right = null

		else parent.left = null
		return true
	}


	fun getSubTree(key: K): BSTree<K, V>?{
		val parent: BSTNode<K, V>? = this.findParent(key)
		val child: BSTree<K, V> = BSTree()
		if(parent == null) return null
		//На будущее, нужно добавить детей к child.add()
		if(parent.right != null && parent.right?.key == key) child.add(key, parent.right!!.value)

		else child.add(key, parent.left!!.value)
		return child
	}
}
