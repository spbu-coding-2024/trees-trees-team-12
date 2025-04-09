package treesTests

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import trees.BSTree
import trees.nodes.RBNode

class BSTreeTest
{
    private lateinit var tree: BSTree<Int, Int>

    @BeforeEach
    fun setUp()
    {
        tree = BSTree()
    }

    @Test
    fun `insert one node`()
    {
        tree.insert(1, 1)
        assertEquals(tree.root?.key, 1)
        assertEquals(tree.root?.value, 1)
        assertEquals(tree.find(1), 1)
    }

    @Test
    fun `insert one node with different values`()
    {
        for(p in 1..5)
        {
            tree.insert(1, p)
            assertEquals(tree.root?.key, 1)
            assertEquals(tree.root?.value, p)
            assertEquals(tree.find(1), p)
        }
    }


}