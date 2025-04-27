package treesTests

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import trees.BSTree

class BSTreeTest {
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
    fun `delete from empty tree`() {
        tree.delete(1)

        assertEquals(null, tree.root)
    }

    @Test
    fun `delete non-existent node `() {
        tree.insert(1, 1)
        tree.delete(2)

        assertEquals(null, tree.find(2))
        assertEquals(1, tree.find(1))
    }

    @Test
    fun `delete root node`() {
        tree.insert(2, 2)
        tree.insert(1, 1)
        tree.insert(3, 3)
        tree.delete(2)
        assertEquals(null, tree.find(2))
        assertEquals(1, tree.find(1))
        assertEquals(3, tree.find(3))
    }

    @Test
    fun `delete node with one child`() {
        tree.insert(3, 3)
        tree.insert(2, 2)
        tree.insert(1, 1)
        tree.delete(2)
        assertEquals(null, tree.find(2))
        assertEquals(1, tree.find(1))
        assertEquals(3, tree.find(3))
    }

    @Test
    fun `delete node with two children`() {
        tree.insert(2, 2)
        tree.insert(1, 1)
        tree.insert(4, 4)
        tree.insert(3, 3)
        tree.insert(5, 5)
        tree.delete(4)
        assertEquals(null, tree.find(4))
        assertEquals(3, tree.find(3))
        assertEquals(5, tree.find(5))
    }

    @Test
    fun `delete leaf node`() {
        tree.insert(2, 2)
        tree.insert(1, 1)
        tree.insert(3, 3)
        tree.delete(1)
        assertEquals(null, tree.find(1))
        assertEquals(2, tree.find(2))
        assertEquals(3, tree.find(3))
    }

    @Test
    fun `delete all nodes one by one`() {
        tree.insert(3, 3)
        tree.insert(1, 1)
        tree.insert(2, 2)
        tree.insert(4, 4)

        tree.delete(1)
        assertEquals(null, tree.find(1))

        tree.delete(2)
        assertEquals(null, tree.find(2))

        tree.delete(3)
        assertEquals(null, tree.find(3))

        tree.delete(4)
        assertEquals(null, tree.find(4))

        assertEquals(null, tree.root)
    }

    @Test
    fun `find in empty tree`() {
        assertEquals(null, tree.find(1))
    }

    @Test
    fun `change value by existing key`() {
        tree.insert(1, 11)
        tree.insert(1, 111)

        assertEquals(111, tree.find(1))
    }

    @Test
    fun `insert 10_000 nodes`() {
        for (p in 1..10_000) {
            tree.insert(p, p)
            assertEquals(p, tree.find(p))
        }
    }

    @Test
    fun `delete 1_000 nodes`() {
        for (p in 1..1_000) {
            tree.insert(p, p)
        }
        for (q in 1_000 downTo 1) {
            tree.delete(q)
            assertEquals(null, tree.find(q))
        }
    }
}







