package treesTests

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import trees.RBTree
import trees.nodes.RBNode

class RBTreeTest {
    private lateinit var tree: RBTree<Int, Int>

    @BeforeEach
    fun setUp() {
        tree = RBTree()
    }

    @Test
    fun `insert one node`() {
        tree.insert(1, 1)
        assertEquals(tree.root?.color, RBNode.Color.BLACK)
        assertEquals(tree.root?.key, 1)
        assertEquals(treeSize(tree), 1)
        assertEquals(tree.find(1), 1)
        assertTrue(isBinarySearchTree(tree.root))
    }

    @Test
    fun `insert ten increasing nums`() {
        for (i in 1..10) {
            tree.insert(i, i)
            checkInvariant(tree.root)
            assertEquals(tree.find(i), i)
            assertEquals(tree.find(i + 1), null)
            assertTrue(isBinarySearchTree(tree.root))
        }
        assertEquals(treeSize(tree), 10)
    }

    @Test
    fun `insert 20 decreasing numbers`() {
        for (i in 20 downTo 1) {
            tree.insert(i, i)
            checkInvariant(tree.root)
            assertEquals(tree.find(i), i)
            assertEquals(tree.find(i - 1), null)
            assertTrue(isBinarySearchTree(tree.root))
        }
        assertEquals(treeSize(tree), 20)
    }

    @Test
    fun `replacing value in node when inserting existing key`() {
        tree.insert(1, 1)
        tree.insert(1, 2)
        assertEquals(tree.root?.value, 2)
    }

    @Test
    fun `insert 20 shuffled numbers with_delete`() {
        var shuffled = (1..20).shuffled()
        var size = 0
        for (i in shuffled) {
            tree.insert(i, i)
            size++
        }

        shuffled = shuffled.shuffled()

        for (i in shuffled) {
            tree.delete(i)
            size--
            checkInvariant(tree.root)
            assertEquals(tree.find(i), null)
            assertEquals(treeSize(tree), size)
            assertTrue(isBinarySearchTree(tree.root))
        }
        assertEquals(treeSize(tree), 0)
    }

    @Test
    fun `delete from empty tree`() {
        tree.delete(1)
    }

    @Test
    fun `delete node that is not in the tree`() {
        for (i in 1..10) {
            tree.insert(i, i)
        }
        checkInvariant(tree.root)
        assertEquals(treeSize(tree), 10)
        tree.delete(11)
        checkInvariant(tree.root)
        assertEquals(treeSize(tree), 10)
    }

    @RepeatedTest(100)
    fun `big find test`() {
        var shuffled = (1..10000).shuffled().take(1000)
        for (i in shuffled) {
            tree.insert(i, i)
            assertTrue(isBinarySearchTree(tree.root))
        }

        shuffled = shuffled.shuffled()

        for (i in shuffled) {
            assertEquals(tree.find(i), i)
            assertTrue(isBinarySearchTree(tree.root))
        }
    }

    @Test
    fun `small delete test`() {
        for (i in 1..100) {
            tree.insert(i, i)
            assertEquals(tree.find(i), i)
            assertEquals(treeSize(tree), i)
            checkInvariant(tree.root)
            assertTrue(isBinarySearchTree(tree.root))
        }

        for (i in 100 downTo 1) {
            tree.delete(i)
            assertEquals(tree.find(i), null)
            assertEquals(treeSize(tree), i - 1)
            checkInvariant(tree.root)
            assertTrue(isBinarySearchTree(tree.root))
        }
    }

    @RepeatedTest(100)
    fun `large property test`() {
        var shuffled = (1..100000).shuffled().take(1000)
        for (i in shuffled) {
            tree.insert(i, i)
            assertEquals(tree.find(i), i)
            checkInvariant(tree.root)
            assertTrue(isBinarySearchTree(tree.root))
        }

        assertEquals(treeSize(tree), 1000)


        shuffled = shuffled.shuffled()

        for (i in shuffled) {
            tree.delete(i)
            assertEquals(tree.find(i), null)
            checkInvariant(tree.root)
            assertTrue(isBinarySearchTree(tree.root))
        }
        assertEquals(treeSize(tree), 0)
    }

    private fun checkInvariant(node: RBNode<Int, Int>?): Int {
        if (node == null) return 1

        if (node.color == RBNode.Color.RED) {
            assertNotEquals(node.color, getNodeColor(node.left))
            assertNotEquals(node.color, getNodeColor(node.right))
        }

        val leftHeight: Int = checkInvariant(node.left)
        val rightHeight: Int = checkInvariant(node.right)

        assertEquals(leftHeight, rightHeight)

        return if (node.color == RBNode.Color.BLACK) leftHeight + 1 else leftHeight
    }

    private fun getNodeColor(node: RBNode<Int, Int>?): RBNode.Color {
        return node?.color ?: RBNode.Color.BLACK
    }

    private fun treeSize(tree: RBTree<Int, Int>): Int {
        return subTreeSize(tree.root)
    }

    private fun subTreeSize(node: RBNode<Int, Int>?): Int {
        if (node == null) return 0
        return 1 + subTreeSize(node.left) + subTreeSize(node.right)
    }

    private fun isBinarySearchTree(node: RBNode<Int, Int>?): Boolean {
        if (node == null) return true
        return isBinarySearchTree(node.left) && isBinarySearchTree(node.right) &&
                node.key >= findMaxKey(node.left) &&
                node.key <= findMinKey(node.right)
    }

    private fun findMinKey(node: RBNode<Int, Int>?): Int {
        var current: RBNode<Int, Int>? = node
        while (current?.left != null) {
            current = current.left
        }
        return current?.key ?: Int.MAX_VALUE
    }

    private fun findMaxKey(node: RBNode<Int, Int>?): Int {
        var current: RBNode<Int, Int>? = node
        while (current?.right != null) {
            current = current.right
        }
        return current?.key ?: Int.MIN_VALUE
    }
}
