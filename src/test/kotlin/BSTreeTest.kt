import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import treeLib.bintrees.BSTree
import treeLib.nodes.BSTNode
import kotlin.math.abs
import org.junit.jupiter.api.Test

class BSTreeTest {
    var baum = BSTree<Int, String>()


    @Test
    fun findParent() {

    }






    @Nested
    inner class TestingAddMethods {

        @Test
        fun addToEmptyTree() {
            assertEquals("qwerty", baum.root()?.value)
            assertEquals(4, baum.root()?.key)
        }

        @BeforeEach
        fun setUp() {
            baum.add(4, "qwerty")
            baum.add(2, "Ge")
            baum.add(5, "rma")
            baum.add(-1, "ny")
            baum.add(3, "the")
            baum.add(40, "best")
            baum.add(44, "Ja")
        }

        @Test
        fun checkWaysToNodes() {
            val rt = baum.root()
            assertEquals(BSTNode(5,"rma"), rt?.right)
            assertEquals(BSTNode(3, "the"), rt?.left?.right)
            assertEquals(BSTNode(-1, "ny"), rt?.left?.left)
            assertEquals(BSTNode(44, "Ja"), rt?.right?.right?.right)
        }

        @Test
        fun checkAmountsOfNodes() {
            assertEquals(7, baum.countNodes())
            assertEquals(BSTNode(10, "Was"),baum.add(10, "Was"))
            assertEquals(8, baum.countNodes())
        }

        @Test
        fun addPairs() {
            baum.addPairs(Pair(1, "ein"), Pair(7, "sieben"), Pair(6, "sechs"))
            assertEquals(10, baum.countNodes())
            assertEquals(1, baum.root()?.left?.left?.right?.key)
            assertEquals(6, baum.root()?.right?.right?.left?.left?.key)
        }

        @Test
        fun addAlreadyExistingNodes() {
            assertNull(baum.add(5, "rma"))
            assertNull(baum.add(3, "the"))
            assertNull(baum.add(40, "best"))
            assertNull(baum.add(44, "Ja"))
            assertEquals(7, baum.countNodes())
        }

        @Test
        fun orderMatters() {
            baum.clear()
            assertTrue(baum.addPairs(Pair(4, "Vier"), Pair(5, "Funf"), Pair(40, "Vierzig"), Pair(44,"Vierundvierzig")))
            var rt = baum.root()
            assertEquals(5, rt?.right?.key)
            assertEquals(44, rt?.right?.right?.right?.key)
            baum.clear()
            baum.addPairs(Pair(4, "Vier"), Pair(44,"Vierundvierzig"), Pair(40, "Vierzig"), Pair(5, "Funf"))
            rt = baum.root()
            assertEquals(5, rt?.right?.left?.left?.key)
            assertEquals(44, rt?.right?.key)
            //In other words, with different order we have different ways to nodes
        }
    }

    @Nested
    inner class Remove {
        @BeforeEach
        fun setUp() {
            for(key in listOf(10, 13, 12, 11, 20, 15, 25, -7, 5, -10, 9)){
                baum.add(key, "test")
            }
        }

        @Test
        fun removeNodesWithNoKinder() {
            val rt = baum.root()
            assertTrue(baum.removePairs(-10, 11))
            baum.remove(9)
            assertNull(rt?.left?.left)
            assertNull(rt?.right?.left?.left)
            assertNull(rt?.left?.right?.right)
        }

        @Test
        fun removeNodesWithEinKind() {
            val rt = baum.root()
            baum.removePairs(12)
            baum.remove(5)
            assertEquals(9, rt?.left?.right?.key)
            assertNull(rt?.left?.right?.right)
            assertEquals(11, rt?.right?.left?.key)
            assertNull(rt?.right?.left?.left)
        }

        @Test
        fun removeNodeWithZweiKinderFirstCase() {
            //I don't know how to describe any of these cases, just image current tree and I hope you'll get it
            val rt = baum.root()
            baum.remove(-7)
            assertEquals(5, rt?.left?.key)
            assertEquals(-10, rt?.left?.left?.key)
            assertEquals(9, rt?.left?.right?.key)
            assertNull(rt?.left?.right?.right)
        }

        @Test
        fun removeNodeWithZweiKinderSecondCase() {
            val rt = baum.root()
            assertEquals("test", baum.remove(13))
            assertEquals(15, rt?.right?.key)
            assertEquals(12, rt?.right?.left?.key)
            assertEquals(20, rt?.right?.right?.key)
            assertEquals(25, rt?.right?.right?.right?.key)
            assertNull(rt?.right?.right?.left)
        }

        @Test
        fun removeNodeWithZweiKinderThirdCase() {
            val rt = baum.root()
            baum.addPairs(Pair(16, "test"))
            baum.remove(13)
            assertEquals(16, rt?.right?.right?.left?.key)
        }

        @Test
        fun removeNodeWithZweiKinderFourthCase() {
            val rt = baum.root()
            baum.changeVal(25, "Sieg")
            baum.remove(20)
            assertNull(rt?.right?.right?.right)
            assertEquals(BSTNode(25, "Sieg"), rt?.right?.right)
            assertEquals(BSTNode(15,"test"), rt?.right?.right?.left)
        }

        @Test
        fun removeNodeWithZweiKinderFifthCase() {
            baum.clear()
            baum.addPairs(Pair(-50, "Notf"), Pair(2, "Zwei"), Pair(101, "One hundred and One"))
            for(key in listOf(-30, 99, 95, 90, 78, 56, 45, 34, 23, 14, 7, 3, 5, 8)){
                baum.add(key, "test")
            }
            val rt = baum.root()
            baum.remove(2)
            assertEquals(BSTNode(3, "test"), rt?.right)
            baum.remove(3)
            assertEquals(BSTNode(5, "test"), rt?.right)
            val ls = listOf(99, 95, 90, 78, 56, 45, 34, 23, 14, 7, 8, 5).sorted()
            for(i in 0..(ls.size-2) ){
                baum.remove(ls[i])
                assertEquals(BSTNode(ls[i + 1], "test"), rt?.right)
            }
            assertNull(rt?.right?.right?.left)
        }

        @Test
        fun removeNotExistingOderAlreadyRemovedNode() {
            assertNull(baum.remove(62))
            assertNull(baum.remove(-24))
            assertFalse(baum.removePairs(5,6,8,3))
            assertEquals("test", baum.remove(13))
            assertNull(baum.remove(13))
        }

        @Test
        fun removeAndChangingAmountOfNode() {
            val rt = baum.root()
            assertEquals(11, baum.countNodes())
            baum.remove(11)
            assertEquals(10, baum.countNodes())
            baum.remove(20)
            assertEquals(9, baum.countNodes())
            baum.remove(5)
            assertEquals(8, baum.countNodes())
            baum.remove(-7)
            assertEquals(7, baum.countNodes())
        }

        @Test
        fun removeRoot() {
            baum.remove(10)
            assertEquals(BSTNode(11, "test"), baum.root())
            baum.remove(11)
            assertEquals(BSTNode(12, "test"), baum.root())
            baum.remove(12)
            assertEquals(BSTNode(13, "test"), baum.root())
            baum.remove(13)
            assertEquals(BSTNode(15, "test"), baum.root())
        }

        @Test
        fun howMuchDoWeNeedToDeleteTheWholeTreeByRemovingEachSingleNode() {
            val len = 11
            for(i in 0..5){
                assertNotNull(baum.root())
                baum.remove(baum.root()?.key!!.toInt())
            }
            baum.remove(25)
            assertEquals(BSTNode(-7, "test"), baum.root()?.left)
            assertNull(baum.root())
        }
    }
}