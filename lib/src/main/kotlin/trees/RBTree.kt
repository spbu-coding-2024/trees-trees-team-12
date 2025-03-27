package trees

import trees.nodes.RBNode

class RBTree<K : Comparable<K>, V>() : AbstractBSTree<K, V, RBNode<K, V>>() {
    override fun insert(key: K, value: V) {
        val newNode: RBNode<K, V> = RBNode(key, value)
        newNode.color = RBNode.Color.RED
        var current : RBNode<K, V>? = root
        var currentParent: RBNode<K, V>? = null
        while (current != null) {
            currentParent = current
            current = if (newNode.key < current.key) current.left else current.right
        }

        if  (currentParent == null) {
            newNode .color = RBNode.Color.BLACK
            root = newNode
            return
        }

        newNode.parent = currentParent
        if (newNode.key < currentParent.key) {
            currentParent.left = newNode
        } else {
            currentParent.right = newNode
        }

        fixAfterInsert(newNode)
    }

    private fun fixAfterInsert(node: RBNode<K, V>?) {
        if (node == root) {
            node?.color = RBNode.Color.BLACK
            return
        }

        val parent: RBNode<K, V>? = node?.parent
        if (getColor(parent) == RBNode.Color.BLACK) {
            return
        }

        val grandpa: RBNode<K, V>? = parent?.parent
        val uncle: RBNode<K, V>? = if (parent == grandpa?.left) grandpa?.right else grandpa?.left

        if (getColor(uncle) == RBNode.Color.RED) {
            parent?.color = RBNode.Color.BLACK
            uncle?.color = RBNode.Color.BLACK
            grandpa?.color = RBNode.Color.RED
            fixAfterInsert(grandpa)
        } else {
            if (isLeft(node) == isLeft(parent)) {
                if (isLeft(node)) {
                    rightRotate(grandpa)
                } else {
                    leftRotate(grandpa) // TODO FIX FIX  FIX
                }
                parent?.color = RBNode.Color.BLACK
                uncle?.color = RBNode.Color.BLACK
                grandpa?.color = RBNode.Color.RED
            } else {
                if (isLeft(node)) {
                    rightRotate(parent)
                    leftRotate(grandpa)
                } else {
                    leftRotate(parent)
                    rightRotate(grandpa)
                }
                node?.color = RBNode.Color.BLACK
                grandpa?.color = RBNode.Color.RED
            }
        }
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

    private fun isLeft(node: RBNode<K, V>?): Boolean {
        if (node == root) {
            return false
        }
        return node == node?.parent?.left
    }

    override fun delete(key: K) {
        TODO("Not yet implemented")
    }
}