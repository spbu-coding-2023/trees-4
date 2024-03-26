package treeLib.nodes

class AVLNode <K : Comparable<K>, V> (internal val key: K, internal var value: V) {
	internal var right: AVLNode<K, V>? = null
	internal var left: AVLNode<K, V>? = null
	internal var height: Int = 1
}