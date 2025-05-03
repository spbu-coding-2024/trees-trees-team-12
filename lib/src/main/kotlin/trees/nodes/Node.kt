package trees.nodes

abstract class Node<K : Comparable<K>, V, NODE_T : Node<K, V, NODE_T>>(internal var key: K, internal var value: V) {
    internal var left: NODE_T? = null
    internal var right: NODE_T? = null
}