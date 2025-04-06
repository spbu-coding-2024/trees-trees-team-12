package trees

import trees.nodes.RBNode

class RBTree<K : Comparable<K>, V>() : AbstractBSTree<K, V, RBNode<K, V>>() {
    override fun insert(key: K, value: V) {
        val oldNode: RBNode<K, V>? = findNode(key)
        if (oldNode != null) {
            oldNode.value = value
            return
        }

        val newNode: RBNode<K, V> = RBNode(key, value)
        var current: RBNode<K, V>? = root
        var currentParent: RBNode<K, V>? = null
        while (current != null) {
            currentParent = current
            current = if (newNode//        newNode.color = RBNode.Color.RED.key < current.key) current.left else current.right
        }

        if (currentParent == null) {
            newNode.color = RBNode.Color.BLACK
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
                    leftRotate(grandpa)
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

    private fun rightRotate(node: RBNode<K, V>?) {
        val leftChild: RBNode<K, V>? = node?.left
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

    private fun leftRotate(node: RBNode<K, V>?) {
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

    private fun childCount(node: RBNode<K, V>?): Int {
        var acc: Int = 0

        if (node?.left != null) {
            acc++
        }
        if (node?.right != null) {
            acc++
        }

        return acc
    }

    private fun findMax(node: RBNode<K, V>?): RBNode<K, V>? {
        var maxNode: RBNode<K, V>? = node
        while (maxNode?.right != null) {
            maxNode = maxNode.right
        }
        return maxNode
    }

    private fun swapNodesValues(firstNode: RBNode<K, V>?, secondNode: RBNode<K, V>?) {
        val tmp: Pair<K, V> = Pair(firstNode?.key ?: throw IllegalArgumentException(), firstNode.value)
        firstNode.key = secondNode?.key ?: throw IllegalArgumentException()
        firstNode.value = secondNode.value ?: throw IllegalArgumentException()
        secondNode.key = tmp.first
        secondNode.value = tmp.second
    }

    override fun delete(key: K) {
        var nodeForDelete: RBNode<K, V> = findNode(key) ?: return

        if (childCount(nodeForDelete) == 2) {
            val leftMax: RBNode<K, V> = findMax(nodeForDelete.left) ?: throw IllegalArgumentException()
            swapNodesValues(nodeForDelete, leftMax)
            nodeForDelete = leftMax
        }

        if (nodeForDelete == root) {
            root = nodeForDelete.right ?: nodeForDelete.left
        }

        if (childCount(nodeForDelete) == 0) {
            if (getColor(nodeForDelete) == RBNode.Color.RED) {
                if (isLeft(nodeForDelete)) {
                    nodeForDelete.parent?.left = null
                    return
                }
                nodeForDelete.parent?.right = null
                return
            }
            val parent: RBNode<K, V>? = nodeForDelete.parent
            val isLeftDeleted: Boolean = isLeft(nodeForDelete)
            if (isLeft(nodeForDelete)) {
                parent?.left = null
            } else {
                parent?.right = null
            }
            fixAfterDelete(parent, isLeftDeleted)
        } else {
            val childNode: RBNode<K, V>? = nodeForDelete.left ?: nodeForDelete.right
            childNode?.parent = nodeForDelete.parent
            childNode?.color = RBNode.Color.BLACK
            if (isLeft(nodeForDelete)) {
                nodeForDelete.parent?.left = childNode
                return
            }
            nodeForDelete.parent?.right = childNode
        }
    }

    private fun fixAfterDelete(parent: RBNode<K, V>?, isLeftForFix: Boolean) {
        var brother: RBNode<K, V>? = if (isLeftForFix) parent?.right else parent?.left
        if (isLeftForFix) {
            if (getColor(parent) == RBNode.Color.RED) {
                if (getColor(brother?.left) == RBNode.Color.RED || getColor(brother?.right) == RBNode.Color.RED) {
                    val redChild: RBNode<K, V>? =
                        if (getColor(brother?.right) == RBNode.Color.RED) brother?.right else brother?.left
                    if (!isLeft(redChild)) {
                        leftRotate(parent)
                        parent?.color = RBNode.Color.BLACK
                        brother?.color = RBNode.Color.RED
                        redChild?.color = RBNode.Color.BLACK
                    } else {
                        rightRotate(brother)
                        leftRotate(parent)
                        parent?.color = RBNode.Color.BLACK
                    }
                } else {
                    parent?.color = RBNode.Color.BLACK
                    brother?.color = RBNode.Color.RED
                }
            } else {
                if (getColor(brother) == RBNode.Color.RED) {
                    leftRotate(parent)
                    parent?.color = RBNode.Color.RED
                    brother?.color = RBNode.Color.BLACK
                    fixAfterDelete(parent, isLeftForFix)
                } else {
                    if (getColor(brother?.left) == RBNode.Color.RED || getColor(brother?.right) == RBNode.Color.RED) {
                        val redChild: RBNode<K, V>? =
                            if (getColor(brother?.left) == RBNode.Color.RED) brother?.left else brother?.right
                        if (!isLeft(redChild)) {
                            leftRotate(parent)
                            redChild?.color = RBNode.Color.BLACK
                        } else {
                            rightRotate(brother)
                            leftRotate(parent)
                            redChild?.color = RBNode.Color.BLACK
                        }
                    } else {
                        brother?.color = RBNode.Color.RED
                        fixAfterDelete(parent?.parent, isLeft(parent))
                    }
                }
            }
        } else {
            if (getColor(parent) == RBNode.Color.RED) {
                if (getColor(brother?.left) == RBNode.Color.RED || getColor(brother?.right) == RBNode.Color.RED) {
                    val redChild: RBNode<K, V>? =
                        if (getColor(brother?.left) == RBNode.Color.RED) brother?.left else brother?.right
                    if (isLeft(redChild)) {
                        rightRotate(parent)
                        parent?.color = RBNode.Color.BLACK
                        brother?.color = RBNode.Color.RED
                        redChild?.color = RBNode.Color.BLACK
                    } else {
                        leftRotate(brother)
                        rightRotate(parent)
                        parent?.color = RBNode.Color.BLACK
                    }
                } else {
                    parent?.color = RBNode.Color.BLACK
                    brother?.color = RBNode.Color.RED
                }
            } else {
                if (getColor(brother) == RBNode.Color.RED) {
                    rightRotate(parent)
                    parent?.color = RBNode.Color.RED
                    brother?.color = RBNode.Color.BLACK
                    fixAfterDelete(parent, isLeftForFix)
                } else {
                    if (getColor(brother?.left) == RBNode.Color.RED || getColor(brother?.right) == RBNode.Color.RED) {
                        val redChild: RBNode<K, V>? =
                            if (getColor(brother?.left) == RBNode.Color.RED) brother?.left else brother?.right
                        if (isLeft(redChild)) {
                            rightRotate(parent)
                            redChild?.color = RBNode.Color.BLACK
                        } else {
                            leftRotate(brother)
                            rightRotate(parent)
                            redChild?.color = RBNode.Color.BLACK
                        }
                    } else {
                        brother?.color = RBNode.Color.RED
                        fixAfterDelete(parent?.parent ?: return, isLeft(parent))
                    }
                }
            }
        }
    }
}
