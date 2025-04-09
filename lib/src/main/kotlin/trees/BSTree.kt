package trees

import trees.nodes.BSNode

public class BSTree<K : Comparable<K>, V>() : AbstractBSTree<K, V, BSNode<K, V>>() {
    override fun insert(key: K, value: V) {
        root = insertRecursive(key, value, root)
    }

    private fun insertRecursive(key: K, value: V, node: BSNode<K, V>?): BSNode<K, V> {
        if (node == null) {
           return BSNode(key, value)
        }

        if (key < node.key) {
            node.left = insertRecursive(key, value, node.left)
        } else if (key > node.key) {
            node.right = insertRecursive(key, value, node.right)
        } else {
            node.value = value
        }
       return node
    }

    override fun delete(key: K) {
        root = deleteRecursive(root, key)
    }

    private fun deleteRecursive(node: BSNode<K, V>?, key: K): BSNode<K, V>? {

        if (node == null) {
            return null
        }

        if (key == node.key) {

            if (node.left == null && node.right == null) {
                return null
            }
            if (node.left == null) {
                return node.right
            }
            if (node.right == null) {
                return node.left
            }

            val smallestNode = getSmallestValue(node.right ?: throw IllegalArgumentException("Right node must not be null"))
            node.key = smallestNode.key
            node.value = smallestNode.value
            node.right = deleteRecursive(node.right, smallestNode.key)
            return node
        }

        if (key < node.key) {
            node.left = deleteRecursive(node.left, key)
        } else {
            node.right = deleteRecursive(node.right, key)
        }

        return node
    }

    private fun getSmallestValue(root: BSNode<K, V>): BSNode<K, V> {

        val leftNode = root.left
        if (leftNode == null) {
            return root
        }

        return getSmallestValue(leftNode)
    }

}
