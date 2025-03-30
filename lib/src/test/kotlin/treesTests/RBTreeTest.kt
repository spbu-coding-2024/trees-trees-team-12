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
        tree = RBTree<Int, Int>()
    }

    @Test
    fun `insert_one_node`() {
        tree.insert(1, 1)
        assertEquals(tree.root?.color, RBNode.Color.BLACK)
        assertEquals(tree.root?.key, 1)
        assertEquals(treeSize(tree), 1)
        assertEquals(tree.find(1), 1)
    }

    @Test
    fun `insert_ten_increasing_nums`() {
        for (i in 1 .. 10) {
            tree.insert(i, i)
            checkInvariant(tree.root)
            assertEquals(tree.find(i), i)
            assertEquals(tree.find(i + 1), null)
        }
        assertEquals(treeSize(tree), 10)
    }

    @Test
    fun `insert_20_decreasing_numbers`() {
        for (i in 20 downTo 1) {
            tree.insert(i, i)
            checkInvariant(tree.root)
            assertEquals(tree.find(i), i)
            assertEquals(tree.find(i - 1), null)
        }
        assertEquals(treeSize(tree), 20)
    }

    @Test
    fun `insert_20_shuffled_numbers_with_delete`() {
        var shuffled = (1 .. 20).shuffled()
        var size = 0
        for (i in shuffled) {
            tree.insert(i, i)
            size++
            checkInvariant(tree.root)
            assertEquals(tree.find(i), i)
            assertEquals(treeSize(tree), size)
        }
        assertEquals(treeSize(tree), 20)

        shuffled = shuffled.shuffled()

        for (i in shuffled) {
            tree.delete(i)
            size--
            checkInvariant(tree.root)
            assertEquals(tree.find(i), null)
            assertEquals(treeSize(tree), size)
        }
    }

    @RepeatedTest(100)
    fun `big_find_test`() {
        var shuffled = (1 .. 10000).shuffled().take(1000)
        for (i in shuffled) {
            tree.insert(i, i)
        }

        shuffled = shuffled.shuffled()

        for (i in shuffled) {
            assertEquals(tree.find(i), i)
        }
    }

    @Test
    fun `small_delete_test`() {
        for (i in 1 .. 100) {
            tree.insert(i, i)
            assertEquals(tree.find(i), i)
            assertEquals(treeSize(tree), i)
            checkInvariant(tree.root)
        }

        for (i in 100 downTo 1) {
            tree.delete(i)
            assertEquals(tree.find(i), null)
            assertEquals(treeSize(tree), i - 1)
            checkInvariant(tree.root)
        }
    }

    @RepeatedTest(20)
    fun `large_property_test`() {
        var shuffled = (1 .. 100000).shuffled().take(10000)
        for (i in shuffled) {
            tree.insert(i, i)
            assertEquals(tree.find(i), i)
            checkInvariant(tree.root)
        }

        assertEquals(treeSize(tree), 10000)

        shuffled = shuffled.shuffled()

        for (i in shuffled) {
            tree.delete(i)
            assertEquals(tree.find(i), null)
            checkInvariant(tree.root)
        }
        assertEquals(treeSize(tree), 0)
    }

    fun checkInvariant(node: RBNode<Int, Int>?): Int {
        if (node == null) return 1

        if (node.color == RBNode.Color.RED) {
            assertNotEquals(node.color, node.left?.color)
            assertNotEquals(node.color, node.right?.color)
        }

        val leftHeight: Int = checkInvariant(node.left)
        val rightHeight: Int = checkInvariant(node.right)

        assertEquals(leftHeight, rightHeight)

        return if (node.color == RBNode.Color.BLACK) leftHeight + 1 else leftHeight
    }

    internal fun getNodeColor(node: RBNode<Int, Int>?): RBNode.Color{
        return node?.color ?: RBNode.Color.BLACK
    }

    fun treeSize(tree: RBTree<Int, Int>): Int {
        return subTreeSize(tree.root)
    }

    fun subTreeSize(node: RBNode<Int, Int>?): Int {
        if (node == null) return 0
        return 1 + subTreeSize(node.left) + subTreeSize(node.right)
    }
}
