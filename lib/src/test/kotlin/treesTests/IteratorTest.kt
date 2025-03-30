package treesTests

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import trees.RBTree
import trees.nodes.RBNode

class IteratorTest {
    private lateinit var tree: RBTree<Int, Int>

    @BeforeEach
    fun setUp() {
        tree = RBTree<Int, Int>()
    }

    @Test
    fun `iterate_with_nul_root`() {
        var countre: Int = 0
        for (pair in tree) {
            countre++
        }
        assertEquals(countre, 0)
    }

    @Test
    fun `iterator_test_1`() {
        for (i in 1 .. 5) {
            tree.insert(i, i)
        }
        var i: Int = 1
        for (pair in tree) {
            assertEquals(pair.first, i)
            i++
        }
    }

    @Test
    fun `iterator_test_2`() {
        for (i in 1 .. 100) {
            tree.insert(i, i)
        }
        var i: Int = 1
        for (pair in tree) {
            assertEquals(pair.first, i)
            i++
        }
    }
}