import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import treeLib.bintrees.RBTree
import treeLib.nodes.RBNode
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RBTreeTest {
    private var emptyTree = RBTree<Int, Int>()

    @BeforeEach
    fun init() {
        emptyTree = RBTree()
    }

    @Nested
    @DisplayName("Add method tests")
	inner class AddMethod {
        @Test
        @DisplayName("Add node to the root")
        fun addRoot() {
            val root = emptyTree.add(1, 1974)
            assertEquals(RBNode(1, 1974), root)
            assertEquals(root, emptyTree.root)
        }

        @Test
        @DisplayName("Add node that already exist")
        fun addExisted() {
            emptyTree.add(1, 1974)
            assertEquals(null, emptyTree.add(1, 9999))
            assertEquals(RBNode(1, 1974), emptyTree.root)
        }

        @Test
        @DisplayName("Add without uncle, right ver")
        fun add1R() {
            val root = emptyTree.add(0, 0)!!
            val right = emptyTree.add(1, 1)!!
            val right2 = emptyTree.add(2, 2)!!
            assertEquals(root, emptyTree.root!!.left)
            assertEquals(right, emptyTree.root)
            assertEquals(right2, emptyTree.root!!.right)
            assertTrue { root.isRed && !right.isRed && right2.isRed }
        }

        @Test
        @DisplayName("Add without uncle, left ver")
        fun add1L() {
            val root = emptyTree.add(0, 0)!!
            val left = emptyTree.add(-1, -1)!!
            val left2 = emptyTree.add(-2, -2)!!
            assertEquals(left, emptyTree.root)
            assertTrue { root.isRed && !left.isRed && left2.isRed }
        }


        @Test
        @DisplayName("Add with red uncle, right ver")
        fun add2R() {
            val root = emptyTree.add(0, 0)!!
            val left = emptyTree.add(-1, -1)!!
            val right = emptyTree.add(1, 1)!!
            val right2 = emptyTree.add(2, 2)!!
            assertEquals(root, emptyTree.root)
            assertEquals(left, emptyTree.root!!.left)
            assertEquals(right, emptyTree.root!!.right)
            assertEquals(right2, emptyTree.root!!.right!!.right)
            assertTrue(!root.isRed && !left.isRed && !right.isRed && right2.isRed)
        }

        @Test
        @DisplayName("Add with red uncle, left ver")
        fun add2L() {
            val root = emptyTree.add(0, 0)!!
            val right = emptyTree.add(1, 1)!!
            val left = emptyTree.add(-1, -1)!!
            val left2 = emptyTree.add(-2, -2)!!
            assertEquals(root, emptyTree.root)
            assertEquals(right, emptyTree.root!!.right)
            assertEquals(left, emptyTree.root!!.left)
            assertEquals(left2, emptyTree.root!!.left!!.left)
            assertTrue(!root.isRed && !right.isRed && !left.isRed && left2.isRed)
        }

        @Test
        @DisplayName("Add with black uncle, right ver")
        fun add3R() {
            val root = emptyTree.add(0, 0)!!
            val left = emptyTree.add(-1, -1)!!
            val right = emptyTree.add(1, 1)!!
            val right2 = emptyTree.add(3, 3)!!
            val right15 = emptyTree.add(2, 2)!!
            assertEquals(root, emptyTree.root)
            assertEquals(left, emptyTree.root!!.left)
            assertEquals(right, emptyTree.root!!.right!!.left)
            assertEquals(right2, emptyTree.root!!.right!!.right)
            assertEquals(right15, emptyTree.root!!.right)
            assertTrue(
                !root.isRed && !left.isRed &&
                right.isRed && !right15.isRed && right2.isRed
            )
        }

        @Test
        @DisplayName("Add with black uncle, left ver")
        fun add3L() {
            val root = emptyTree.add(0, 0)!!
            val right = emptyTree.add(1, 1)!!
            val left = emptyTree.add(-1, -1)!!
            val left2 = emptyTree.add(-3, -3)!!
            val left15 = emptyTree.add(-2, -2)!!
            assertEquals(root, emptyTree.root)
            assertEquals(right, emptyTree.root!!.right)
            assertEquals(left, emptyTree.root!!.left!!.right)
            assertEquals(left2, emptyTree.root!!.left!!.left)
            assertEquals(left15, emptyTree.root!!.left)
            assertTrue(
                !root.isRed && !right.isRed &&
                    left.isRed && !left15.isRed && left2.isRed
            )
        }
    }

    @Nested
    @DisplayName("Remove method tests")
    inner class RemoveMethod {
        @Test
        @DisplayName("Remove from empty tree")
        fun removeEmpty() {
            assertEquals(null, emptyTree.remove(Int.MAX_VALUE))
        }

        @Test
        @DisplayName("Remove nonexistent node")
        fun removeNothing() {
            emptyTree.add(1, 1)
            emptyTree.add(2, 2)
            emptyTree.add(3, 3)
            emptyTree.add(4, 4)
            assertEquals(null, emptyTree.remove(0))
        }

        @Test
        @DisplayName("Remove root")
        fun removeRoot() {
            emptyTree.add(1, 1)
            val root = emptyTree.remove(1)
            assertEquals(1, root)
            assertEquals(null, emptyTree.root())
        }

        @Test
        @DisplayName("Remove node without children")
        fun remove1R() {
            emptyTree.add(0, 0)
            emptyTree.add(1, 999)
            assertEquals(999, emptyTree.remove(1))
            assertTrue { emptyTree.root!!.right == null && emptyTree.root!!.left == null }
        }

        @Test
        @DisplayName("Remove node without children")
        fun remove1L() {
            emptyTree.add(0, 0)
            emptyTree.add(-1, 666)
            assertEquals(666, emptyTree.remove(-1))
            assertTrue { emptyTree.root!!.right == null && emptyTree.root!!.left == null }
        }

        @Test
        @DisplayName("")
        fun remove2R() {
        }

        @Test
        @DisplayName("")
        fun remove2L() {

        }

        @Test
        @DisplayName("")
        fun remove3R() {

        }

        @Test
        @DisplayName("")
        fun remove3L() {

        }

        @Test
        @DisplayName("")
        fun remove4r() {

        }

        @Test
        @DisplayName("")
        fun remove4l() {

        }

        @Test
        @DisplayName("")
        fun remove5r() {

        }

        @Test
        @DisplayName("")
        fun remove5l() {

        }
    }
}