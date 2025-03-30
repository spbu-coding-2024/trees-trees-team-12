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
        root = delete(root, key)
    }

    private fun delete(node: AVLNode<K, V>?, key: K): AVLNode<K, V>? {
        if (node == null) return node
        
        when {
            key < node.key -> node.left = delete(node.left, key)
            key > node.key -> node.right = delete(node.right, key)
            else -> { // Found node for deleting
                if (node.left == null || node.right == null) {
                    val temp = node.left ?: node.right
                    return temp  // One or no child case
                } else {
                    // Node with two children
                    val temp = minValueNode(node.right!!)
                    node.key = temp.key
                    node.value = temp.value
                    node.right = delete(node.right, temp.key)
                }
            }
        }

        if (node == null) return node
        
        // Update height
        node.height = 1 + maxOf(height(node.left), height(node.right))
        val balance = getBalance(node)
        
        // Balance
        // Left-Left Case
        if (balance > 1 && getBalance(node.left) >= 0)
            return rightRotate(node)
        // Left-Right Case
        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = leftRotate(node.left!!)
            return rightRotate(node)
        }
        // Right-Right Case
        if (balance < -1 && getBalance(node.right) <= 0)
            return leftRotate(node)
        // Right-Left Case
        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rightRotate(node.right!!)
            return leftRotate(node)
        }
        return node
    }


}