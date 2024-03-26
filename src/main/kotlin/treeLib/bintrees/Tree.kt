package treeLib.bintrees

interface Tree<K : Comparable<K>, V, TREE_TYPE> {
	fun add(key: K, value: V)
	fun initTree(data: List<Pair<K, V>>): TREE_TYPE
}