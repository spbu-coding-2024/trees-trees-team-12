package trees

import trees.nodes.RBNode

class RBTree<K : Comparable<K>, V>() : AbstractBSTree<K, V, RBNode<K, V>>() {
    override fun insert(key: K, value: V) {
        TODO("Not yet implemented")
    }

    private fun getColor(node: RBNode<K, V>?): RBNode.Color {
        return node?.color ?: RBNode.Color.BLACK
    }

    override fun delete(key: K) {
        TODO("Not yet implemented")
    }
}