package treesTests

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import trees.AVLTree
import trees.nodes.AVLNode
import kotlin.math.abs
import kotlin.random.Random

class AVLTreeTest {
    private lateinit var tree: AVLTree<Int, Int>
    
    @BeforeEach
    fun setUp() {
        tree = AVLTree()
    }

    @Test
    fun `insert one node`() {
        tree.insert(1, 1)
        assertEquals(1, tree.find(1))
        assertEquals(1, treeSize(tree))
        checkInvariant(tree.root)
    }

    @Test
    fun `insert ten increasing numbers`() {
        for (i in 1..10) {
            tree.insert(i, i)
            checkInvariant(tree.root)
            assertEquals(i, tree.find(i))
            assertNull(tree.find(i + 1))
        }
        assertEquals(10, treeSize(tree))
    }

    @Test
    fun `insert twenty decreasing numbers`() {
        for (i in 20 downTo 1) {
            tree.insert(i, i)
            checkInvariant(tree.root)
            assertEquals(i, tree.find(i))
            assertNull(tree.find(i - 1))
        }
        assertEquals(20, treeSize(tree))
    }

    @Test
    fun `insert twenty shuffled numbers with delete`() {
        var shuffled = (1..20).shuffled()
        var size = 0
        for (i in shuffled) {
            tree.insert(i, i)
            size++
            checkInvariant(tree.root)
            assertEquals(i, tree.find(i))
            assertEquals(size, treeSize(tree))
        }
        assertEquals(20, treeSize(tree))

        shuffled = shuffled.shuffled()

        for (i in shuffled) {
            tree.delete(i)
            size--
            checkInvariant(tree.root)
            assertNull(tree.find(i))
            assertEquals(size, treeSize(tree))
        }
    }

    @RepeatedTest(100)
    fun `big find test with random keys`() {
        val keys = (1..10000).shuffled().take(1000)
        for (i in keys) {
            tree.insert(i, i)
        }
        val shuffled = keys.shuffled()
        for (i in shuffled) {
            assertEquals(i, tree.find(i))
        }
    }

    @Test
    fun `small delete test`() {
        for (i in 1..100) {
            tree.insert(i, i)
            assertEquals(i, tree.find(i))
            assertEquals(i, treeSize(tree))
            checkInvariant(tree.root)
        }
        for (i in 100 downTo 1) {
            tree.delete(i)
            assertNull(tree.find(i))
            assertEquals(i - 1, treeSize(tree))
            checkInvariant(tree.root)
        }
    }

    @Test
    fun `delete non-existing key`() {
        for (i in 1..10) {
            tree.insert(i, i)
        }
        val initialSize = treeSize(tree)
        tree.delete(100)
        assertEquals(initialSize, treeSize(tree))
    }

    @Test
    fun `duplicate key update test`() {
        tree.insert(5, 50)
        assertEquals(50, tree.find(5))
        tree.insert(5, 500)
        assertEquals(500, tree.find(5))
        assertEquals(1, treeSize(tree))
        checkInvariant(tree.root)
    }

    @RepeatedTest(20)
    fun `large property test with random insertions and deletions`() {
        val keys = (1..100000).shuffled().take(10000)
        for (i in keys) {
            tree.insert(i, i)
            checkInvariant(tree.root)
            assertEquals(i, tree.find(i))
        }
        assertEquals(10000, treeSize(tree))

        val shuffled = keys.shuffled()
        for (i in shuffled) {
            tree.delete(i)
            checkInvariant(tree.root)
            assertNull(tree.find(i))
        }
        assertEquals(0, treeSize(tree))
    }

    @Test
    fun `random insert-delete mix test`() {
        val random = Random(42)
        val inserted = mutableSetOf<Int>()
        repeat(1000) {
            val x = random.nextInt(0, 500)
            tree.insert(x, x)
            inserted.add(x)
            checkInvariant(tree.root)
        }
        val deletionList = inserted.toList().shuffled(random).take(500)
        for (x in deletionList) {
            tree.delete(x)
            inserted.remove(x)
            checkInvariant(tree.root)
        }

        for (x in inserted) {
            assertEquals(x, tree.find(x))
        }
    }

    // Helping function for checking the AVL tree invariant:
    // for each node, the difference in the heights of the left and right
    // subtrees should not exceed 1
    private fun checkInvariant(node: AVLNode<Int, Int>?): Int {
        if (node == null) return 0
        val leftHeight = checkInvariant(node.left)
        val rightHeight = checkInvariant(node.right)
        assertTrue(abs(leftHeight - rightHeight) <= 1)
        return 1 + maxOf(leftHeight, rightHeight)
    }

    private fun treeSize(tree: AVLTree<Int, Int>): Int {
        return subtreeSize(tree.root)
    }

    private fun subtreeSize(node: AVLNode<Int, Int>?): Int {
        if (node == null) return 0
        return 1 + subtreeSize(node.left) + subtreeSize(node.right)
    }
}
