package trees

import trees.nodes.Node

public abstract class AbstractBSTree<K : Comparable<K>, V, NODE_T : Node<K, V, NODE_T>>(): Iterable<Pair<K, V>> {
    protected var root: NODE_T? = null

    public fun find(key: K): V? {
        return findNode(key)?.value
    }

    protected fun findNode(key: K) : NODE_T? {
        var current: NODE_T? = root
        while (current != null) {
            if (current.key == key) {
                return current
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
        return object : Iterator<Pair<K, V>> {
            var queue: ArrayDeque<Pair<K, V>> = ArrayDeque()
            var isAddedToQueue: Boolean = false
            override fun hasNext(): Boolean {
                if (!isAddedToQueue) {
                    inOrderTraversal(root)
                    isAddedToQueue = true
                }
                return queue.isNotEmpty()
            }

            override fun next(): Pair<K, V> {
                return queue.removeFirst()
            }

            fun inOrderTraversal(node: Node<K, V, NODE_T>?) {
                if (node != null) {
                    inOrderTraversal(node.left)
                    queue.add(Pair<K, V>(node.key, node.value))
                    inOrderTraversal(node.right)
                }
            }
        }
    }
}
