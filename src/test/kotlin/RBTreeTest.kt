import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import treeLib.bintrees.RBTree
import treeLib.nodes.RBNode
import kotlin.test.*

class RBTreeTest {
    var emptyTree = RBTree<Int, Int>()

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
        var tree3Node = RBTree<Int, Int>()
        var bft15 = RBTree<Int, Int>()

        @BeforeEach
        fun initTree() {
            tree3Node = RBTree()
            tree3Node.add(0, 0)
            tree3Node.add(10, 10)
            tree3Node.add(-10, -10)

            bft15 = RBTree()    //15 node tree (balanced)
            var k = 16
            for (i in listOf(1, 2, 4, 8)) {  //amount of nodes on level
                for (j in 0..<i)
                    bft15.add(k / 2 + k * j, k / 2+ k * j)
                k /= 2
            }
        }

        @Test
        @DisplayName("Remove from empty tree")
        fun removeEmpty() {
            val removeResult = emptyTree.remove(Int.MAX_VALUE)
            assertNull(removeResult)
            assertNull(emptyTree.root)
        }

        @Test
        @DisplayName("Remove nonexistent node")
        fun removeNothing() {
            val removeResult = tree3Node.remove(999)
            assertNull(removeResult)
            assertEquals(RBNode(0, 0), tree3Node.root)
            assertEquals(RBNode(10, 10), tree3Node.root!!.right)
            assertEquals(RBNode(-10, -10), tree3Node.root!!.left)
        }

        @Test
        @DisplayName("Remove root without children")
        fun removeRoot() {
            emptyTree.add(1, 1)
            val removeResult = emptyTree.remove(1)
            assertEquals(1, removeResult)
            assertEquals(null, emptyTree.root())
        }

        @Test
        @DisplayName("Remove root with children")
        fun removeChildfull() {
            val removeResult = tree3Node.remove(0)
            assertEquals(0, removeResult)
            assertEquals(RBNode(-10, -10), tree3Node.root)
            assertEquals(RBNode(10, 10), tree3Node.root!!.right)
        }


        @Test
        @DisplayName("Remove node without children")
        fun removeWithoutChildren() {
            emptyTree.add(0, 0)
            emptyTree.add(10, 999)
            assertEquals(999, emptyTree.remove(10))
            assertTrue { emptyTree.root!!.right == null && emptyTree.root!!.left == null }
        }

        @Test
        @DisplayName("Remove node with child, right ver")
        fun removeWithChildR() {
            tree3Node.add(20, 20)
            val removeResult = tree3Node.remove(10)
            assertEquals(10, removeResult)
            val root = tree3Node.root
            assertTrue { !root!!.right!!.isRed }
            assertEquals(RBNode(20, 20), root!!.right)
        }

        @Test
        @DisplayName("Remove node with child, left ver")
        fun removeWithChildL() {
            tree3Node.add(-20, -20)
            val removeResult = tree3Node.remove(-10)
            assertEquals(-10, removeResult)
            val root = tree3Node.root
            assertTrue { !root!!.left!!.isRed }
            assertEquals(RBNode(-20, -20), root!!.left)
        }

        @Test
        @DisplayName("Remove node with two children")
        fun removeWithChildren() {
            val removeResult = bft15.remove(8)
            assertEquals(8, removeResult)
            val  root = bft15.root
            assertEquals(RBNode(7, 7), root)
            assertNull(root!!.left!!.right!!.right)
        }

        @Test
        @DisplayName("Son of removed node has red brother, right ver")
        fun remove1L() {
            bft15.add(16, 16)
            bft15.remove(9)
            bft15.remove(11)
            val removeResult =  bft15.remove(10)
            assertEquals(10, removeResult)
            val root = bft15.root
            assertTrue { !root!!.right!!.isRed }
            assertTrue { !root!!.right!!.left!!.isRed }
            assertTrue { root!!.right!!.left!!.right!!.isRed }
            assertTrue { !root!!.right!!.right!!.isRed }
            assertTrue { root!!.right!!.right!!.right!!.isRed }
        }

        @Test
        @DisplayName("Son of removed node has red brother, left ver")
        fun remove1R() {
            bft15.add(0, 0)
            bft15.remove(5)
            bft15.remove(7)
            val removeResult =  bft15.remove(6)
            assertEquals(6, removeResult)
            val root = bft15.root
            assertTrue { !root!!.left!!.isRed }
            assertTrue { !root!!.left!!.right!!.isRed }
            assertTrue { root!!.left!!.right!!.left!!.isRed }
            assertTrue { !root!!.left!!.left!!.isRed }
            assertTrue { root!!.left!!.left!!.left!!.isRed }
        }

        //in next remove tests brother means brother of the removed node's son
        @Test
        @DisplayName("Black brother don't have children, right ver")
        fun remove2R() {
            bft15.add(16, 16)
            bft15.remove(16)
            val removeResult = bft15.remove(15)
            assertEquals(15, removeResult)
            val root = bft15.root
            assertTrue { !root!!.right!!.right!!.isRed }
            assertNull(root!!.right!!.right!!.right)
            assertTrue { root.right!!.right!!.left!!.isRed }
        }

        @Test
        @DisplayName("Black brother don't have children, left ver")
        fun remove2L() {
            bft15.add(0, 0)
            bft15.remove(0)
            val removeResult = bft15.remove(1)
            assertEquals(1, removeResult)
            val root = bft15.root
            assertTrue { !root!!.left!!.left!!.isRed }
            assertNull(root!!.left!!.left!!.left)
            assertTrue { root.left!!.left!!.right!!.isRed }
        }

        @Test
        @DisplayName("Black brother have 2 black children, right ver")
        fun remove3R() {

        }

        @Test
        @DisplayName("Black brother have 2 black children, left ver")
        fun remove3L() {

        }

        @Test
        @DisplayName("Black brother have only right red child, right ver")
        fun remove4R() {
            bft15.remove(7)
            bft15.remove(5)
            bft15.remove(1)
            val removeResult = bft15.remove(6)
            assertEquals(6, removeResult)
            val colorResultComparison = arrayListOf(false, true, false, false, true, false,
                true, true, true, false, true)
            for (node in bft15)
                assertEquals(colorResultComparison.removeFirst(), node.isRed)
        }

        @Test
        @DisplayName("Black brother have only right red child, left ver")
        fun remove4L() {
            bft15.remove(9)
            bft15.remove(11)
            bft15.remove(15)
            val removeResult = bft15.remove(10)
            assertEquals(10, removeResult)
            val colorResultComparison = arrayListOf(true, false, true, true, true, false,
                true, false, false, true, false)
            for (node in bft15)
                assertEquals(colorResultComparison.removeFirst(), node.isRed)
        }

        @Test
        @DisplayName("Black brother have only left red child, right ver")
        fun remove5R() {
            bft15.remove(7)
            bft15.remove(5)
            bft15.remove(3)
            val removeResult = bft15.remove(6)
            assertEquals(6, removeResult)
            val colorResultComparison = arrayListOf(false, true, false, false, true, false,
                true, true, true, false, true)
            for (node in bft15)
                assertEquals(colorResultComparison.removeFirst(), node.isRed)
        }

        @Test
        @DisplayName("Black brother have only left red child, left ver")
        fun remove5L() {
            bft15.remove(9)
            bft15.remove(11)
            bft15.remove(13)
            val removeResult = bft15.remove(10)
            assertEquals(10, removeResult)
            val colorResultComparison = arrayListOf(true, false, true, true, true, false,
                true, false, false, true, false)
            for (node in bft15)
                assertEquals(colorResultComparison.removeFirst(), node.isRed)
        }

        @Test
        @DisplayName("Black brother have 2 red children, right ver")
        fun remove6R() {
            bft15.add(16, 16)
            bft15.add(17, 17)
            val removeResult = bft15.remove(13)
            assertEquals(13, removeResult)
            val root = bft15.root
            assertTrue { root!!.right!!.right!!.isRed }
            assertTrue { !root!!.right!!.right!!.right!!.isRed }
            assertTrue { !root!!.right!!.right!!.left!!.isRed }
            assertTrue { root!!.right!!.right!!.left!!.right!!.isRed }
            assertEquals(RBNode(16, 16), root!!.right!!.right)
            assertEquals(RBNode(17, 17), root!!.right!!.right!!.right)
            assertEquals(RBNode(14, 14), root!!.right!!.right!!.left)
            assertEquals(RBNode(15, 15), root!!.right!!.right!!.left!!.right)
        }

        @Test
        @DisplayName("Black brother have 2 red children, left ver")
        fun remove6L() {
            bft15.add(0, 0)
            bft15.add(-1, -1)
            val removeResult = bft15.remove(3)
            assertEquals(3, removeResult)
            val root = bft15.root
            assertTrue { root!!.left!!.left!!.isRed }
            assertTrue { !root!!.left!!.left!!.left!!.isRed }
            assertTrue { !root!!.left!!.left!!.right!!.isRed }
            assertTrue { root!!.left!!.left!!.right!!.left!!.isRed }
            assertEquals(RBNode(0, 0), root!!.left!!.left)
            assertEquals(RBNode(-1, -1), root.left!!.left!!.left)
            assertEquals(RBNode(2, 2), root.left!!.left!!.right)
            assertEquals(RBNode(1, 1), root.left!!.left!!.right!!.left)
        }
    }
}