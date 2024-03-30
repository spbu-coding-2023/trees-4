package treeLib.bintrees

import treeLib.bintrees.interfaces.BinTree
import treeLib.nodes.BSTNode

class BSTree<K : Comparable<K>, V> : BinTree<K, V, BSTNode<K, V>> {
	override var root: BSTNode<K, V>? = null
	override var amountOfNodes =  1


	fun addPairs(keys: List<K>, values: List<V>): Boolean{
		if(keys.size != values.size) return false
		for(i in keys.indices){
			this.add(keys[i], values[i])
		}
		return true
	}

	override fun add(key: K, value: V): V? {
		if(root == null) root = BSTNode(key, value)
		var x = this.root
		while(x != null){
			if(key > x.key){
				if(x.right == null){
					x.right = BSTNode(key, value)
					break
				}
				x = x.right
			}
			else if(key < x.key){
				if(x.left == null){
					x.left = BSTNode(key, value)
					break
				}
				x = x.left
			}
			else return null
		}
		this.amountOfNodes += 1
		return value
	}

	override fun remove(key: K): V? {
		var x: BSTNode<K, V>? = root
		var parent_x: BSTNode<K, V>? = null
		var count = 0
		while(x != null){
			if(key == x.key){
				break
			}
			parent_x = x
			x = if(key > x.key){
				x.right
			} else{
				x.left
			}
		}
		if(x == null || parent_x == null) return null
		if(x.right != null) count++
		if(x.left != null) count++
		if(count == 0){
			if(parent_x.right === x)  parent_x.right = null
			else parent_x.left = null
		}
		else if(count == 1){
			if(x.left == null){
				if(parent_x.right === x)  parent_x.right = x.right
				else parent_x.left = x.right
			}
			else{
				if(parent_x.right === x)  parent_x.right = x.left
				else parent_x.left = x.left
			}
		}
		else{
			var child_x = x.right
			var parent_child_x = x
			while(child_x!!.left != null){
				if(child_x.left!!.left == null) parent_child_x = child_x
				child_x = child_x.left
			}
			x.key = child_x.key
			x.value = child_x.value
			parent_child_x!!.left = child_x.right
		}
		this.amountOfNodes -= 1
		return null
	}


	fun changeVal(key: K, newValue: V): Boolean {
		var x = root
		while(x != null){
			x = if(key > x.key) x.right
			else if(key < x.key) x.left
			else{
				x.value = newValue
				return true
			}
		}
		return false
	}
}