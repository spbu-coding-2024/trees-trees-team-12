package trees

import trees.nodes.RBNode

class RBTree<K : Comparable<K>, V>() : AbstractBSTree<K, V, RBNode<K, V>>() {
    override fun insert(key: K, value: V) {
        TODO("Not yet implemented")
    }

    override fun delete(key: K) {
        TODO("Not yet implemented")
    }
}