package trees

import trees.nodes.Node

public abstract class AbstractBSTree<K : Comparable<K>, V, NODE_T : Node<K, V, NODE_T>>(): Iterable<Pair<K, V>> {
    private var root: Node<K, V, NODE_T>? = null

    public fun find(key: K): V? {
        var current: Node<K, V, NODE_T>? = root
        while (current != null) {
            if (current.key == key) {
                return current.value
            }
            if (key > current.key) {
                current = current.right
            } else {
                current = current.left
            }
        }
        return null
    }

    abstract fun insert(key: K, value: V)
    abstract fun delete(key: K)

    override fun iterator(): Iterator<Pair<K, V>> {
        TODO("Not yet implemented")
    }
}