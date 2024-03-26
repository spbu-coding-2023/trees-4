package treeLib.bintrees

interface Tree<K : Comparable<K>, V> {
	fun add(key: K, value: V)
}