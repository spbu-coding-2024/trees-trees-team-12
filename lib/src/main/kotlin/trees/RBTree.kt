package trees

import trees.nodes.RBNode

class RBTree<K : Comparable<K>, V>() : AbstractBSTree<K, V, RBNode<K, V>>() {
    override fun insert(key: K, value: V) {
        TODO("Not yet implemented")
    }

    private fun getColor(node: RBNode<K, V>?): RBNode.Color {
        return node?.color ?: RBNode.Color.BLACK
    }

    fun rightRotate(node: RBNode<K, V>?) {
        val  leftChild: RBNode<K, V>? = node?.left
        node?.left = leftChild?.right
        node?.left?.parent = node
        leftChild?.right = node
        leftChild?.parent = node?.parent
        if (root == node) {
            root = leftChild
        } else if (node == node?.parent?.left) {
            node?.parent?.left = leftChild
        } else {
            node?.parent?.right = leftChild
        }
        node?.parent = leftChild
    }

    fun leftRotate(node: RBNode<K, V>?) {
        val rightChild: RBNode<K, V>? = node?.right
        node?.right = rightChild?.left
        node?.right?.parent = node
        rightChild?.left = node
        rightChild?.parent = node?.parent
        if (root == node) {
            root = rightChild
        } else if (node == node?.parent?.left) {
            node?.parent?.left = rightChild
        } else {
            node?.parent?.right = rightChild
        }
        node?.parent = rightChild
    }

    override fun delete(key: K) {
        TODO("Not yet implemented")
    }
}