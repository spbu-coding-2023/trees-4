package treeLib.bintrees.interfaces

interface Tree<K : Comparable<K>, V> {
	fun add(key: K, value: V)
}