package trees

import trees.nodes.AVLNode

public class AVLTree<K : Comparable<K>, V>() : AbstractBSTree<K, V, AVLNode<K, V>>() {
    override fun insert(key: K, value: V) {
        TODO("Not yet implemented")
    }

    override fun delete(key: K) {
        TODO("Not yet implemented")
    }
}