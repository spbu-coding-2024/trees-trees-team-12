package trees

import trees.nodes.AVLNode

public class AVLTree<K : Comparable<K>, V>() : AbstractBSTree<K, V, AVLNode<K, V>>() {
    override fun insert(key: K, value: V) {
        root = insert(root, key, value)
    }

    private fun insert(node: AVLNode<K, V>?, key: K, value: V): AVLNode<K, V> {
        if (node == null) {
            return AVLNode(key, value)
        }
        when {
            key < node.key -> node.left = insert(node.left, key, value)
            key > node.key -> node.right = insert(node.right, key, value)
            else -> {
                node.value = value
                return node
            }
        }
        // Update height
        node.height = 1 + maxOf(height(node.left), height(node.right))
        val balance = getBalance(node)
        
        // Balance
        // Left-Left case
        if (balance > 1 && key < node.left!!.key)
            return rightRotate(node)
        // Right-Right case
        if (balance < -1 && key > node.right!!.key)
            return leftRotate(node)
        // Left-Right case
        if (balance > 1 && key > node.left!!.key) {
            node.left = leftRotate(node.left!!)
            return rightRotate(node)
        }
        // Right-Left case
        if (balance < -1 && key < node.right!!.key) {
            node.right = rightRotate(node.right!!)
            return leftRotate(node)
        }
        return node
    }

    override fun delete(key: K) {
        TODO("Not yet implemented")
    }
}