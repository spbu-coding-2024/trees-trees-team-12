package trees

import trees.nodes.BSNode

public class BSTree<K : Comparable<K>, V>() : AbstractBSTree<K, V, BSNode<K, V>>() {
    override fun insert(key: K, value: V) {
        TODO("Not yet implemented")
    }

    override fun delete(key: K) {
        TODO("Not yet implemented")
    }
}