package treesTests

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import trees.BSTree

class BSTreeTest
{
    private lateinit var tree: BSTree<Int, Int>

    @BeforeEach
    fun setUp() {
        tree = BSTree()
    }

    @Test
    fun `insert one node`() {
        tree.insert(1, 1)
        assertEquals(tree.root?.key, 1)
        assertEquals(tree.root?.value, 1)
        assertEquals(tree.find(1), 1)
    }

    @Test
    fun `insert one node with different values`() {
        for (p in 1..5) {
            tree.insert(1, p)
            assertEquals(tree.root?.key, 1)
            assertEquals(tree.root?.value, p)
            assertEquals(tree.find(1), p)
        }
    }

    @Test
    fun `insert 111 nodes`() {
        for (i in 1..111) {
            tree.insert(i, i)
            assertEquals(tree.find(i), i)
            assertEquals(tree.find(i - 1), null)
        }
    }

    @Test
    fun `delete from empty tree`() {
        tree.delete(1)
    }

    @Test
    fun `delete non-existent node `() {
        tree.insert(1, 1)
        tree.delete(2)
    }
}


