import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import treeLib.bintrees.BSTree
import treeLib.bintrees.interfaces.Treap
import treeLib.nodes.TreapNode
import kotlin.test.assertEquals

class TreapTest {
    var baum = Treap<Int, Int>()

    @Nested
    inner class TestingRemoveMethod {

        @BeforeEach
        fun setUp() {
            baum.add(20,100)
            baum.add(50,90)
            baum.add(35,80)
            baum.add(40,70)
            baum.add(30,60)
            baum.add(70,50)
            baum.add(60,40)
            baum.add(80,30)
        }

        @Test
        fun removeNodeWithoutChildren() {
            val rt = baum.root()?.right?.left

            baum.remove(40)
            assertEquals(null, rt?.right)

            baum.remove(30)
            assertEquals(null, rt?.left)

            baum.remove(35)
            assertEquals(null, baum.root()?.right?.left)
        }

        @Test
        fun removeNodeWithOnlyOneChild() {
            baum.remove(60)
            baum.remove(80)
            baum.remove(70)
            assertEquals(null, baum.root()?.right?.right)

            baum.remove(50)
            assertEquals(TreapNode(35, 80), baum.root()?.right)
        }

        @Test
        fun removeNodeWithTwoChildrenFirstCase() {
            val rt = baum.root()?.right
            baum.remove(35)

            assertEquals(TreapNode(40, 70), rt?.left)
            assertEquals(TreapNode(30,60), rt?.left?.left)
        }

        @Test
        fun removeNodeWithTwoChildrenSecondCase() {
            val rt = baum.root()?.right
            baum.remove(70)

            assertEquals(TreapNode(60, 40), rt?.right)
            assertEquals(TreapNode(80, 30), rt?.right?.right)
        }

        @Test
        fun removeNodeWithTwoChildrenThirdCase() {
            val rt = baum.root()
            baum.remove(50)

            assertEquals(TreapNode(35,80), rt?.right)
            assertEquals(TreapNode(30,60), rt?.right?.left)
            assertEquals(TreapNode(40,70), rt?.right?.right)
            assertEquals(TreapNode(70,50), rt?.right?.right?.right)
            assertEquals(TreapNode(60,40), rt?.right?.right?.right?.left)
            assertEquals(TreapNode(80,30), rt?.right?.right?.right?.right)
        }

        @Test
        fun removeTheOnlyNode() {
            baum.clear()
            baum.add(20, 50)
            baum.remove(20)
            assertEquals(null, baum.root())
        }

        @Test
        fun removeRootWithOnlyRightChild() {
            baum.remove(20)
            assertEquals(TreapNode(50, 90), baum.root())
        }

        @Test
        fun removeRootWithOnlyLeftChild() {
            baum.clear()
            baum.add(2, 20)
            baum.add(0, 10)
            baum.remove(2)

            assertEquals(TreapNode(0, 10), baum.root())
        }

        @Test
        fun removeRootWithTwoChildren() {
            baum.add(10, 95)
            baum.add(15, 45)
            baum.add(0, 65)
            baum.remove(20)
            val rt = baum.root()

            assertEquals(TreapNode(10, 95), rt)
            assertEquals(TreapNode(0,65), rt?.left)
            assertEquals(TreapNode(15,45), rt?.right?.left?.left?.left)
            assertEquals(TreapNode(50, 90), rt?.right)
        }

        @Test
        fun removeReturnNull() {
            assertEquals(null, baum.remove(20))
            assertEquals(null, baum.remove(50))
            assertEquals(null, baum.remove(60))
            assertEquals(null, baum.remove(70))
        }
    }


    @Nested
    inner class TestingAddingMethod {

        @Test
        fun addingRoot() {
            baum.add(24, 50)
            assertEquals(TreapNode(24, 50), baum.root())
        }


        @Test
        fun addingLikeInBSTree() {
            baum.add(4, 60)
            baum.add(7, 50)
            baum.add(2, 40)
            baum.add(10, 30)
            baum.add(-1, 20)
            baum.add(3, 10)
            baum.add(1, 0)

            val rt = baum.root()

            assertEquals(TreapNode(4, 60), baum.root())
            assertEquals(TreapNode(7,50), rt?.right)
            assertEquals(TreapNode(2,40), rt?.left)
            assertEquals(TreapNode(10,30), rt?.right?.right)
            assertEquals(TreapNode(-1,20), rt?.left?.left)
            assertEquals(TreapNode(3,10), rt?.left?.right)
            assertEquals(TreapNode(1,0), rt?.left?.left?.right)
        }

        @Test
        fun changingRootToLeft() {
            baum.add(2, 10)
            baum.add(4,20)

            assertEquals(TreapNode(4,20), baum.root())
            assertEquals(TreapNode(2,10), baum.root()?.left)
        }

        @Test
        fun changingRootToRight() {
            baum.add(4, 10)
            baum.add(2,20)

            assertEquals(TreapNode(2,20), baum.root())
            assertEquals(TreapNode(4,10), baum.root()?.right)
        }

        @Test
        fun addingLikeInBSTreeAndChangingByAddingRootLeftAndRightChildren() {
            baum.add(4, 60)
            baum.add(7, 50)
            baum.add(2, 40)
            baum.add(10, 30)
            baum.add(-1, 20)
            baum.add(3, 10)
            baum.add(1, 0)
            baum.add(11, 55)
            baum.add(0, 45)

            val rt = baum.root()

            assertEquals(TreapNode(4, 60), baum.root())
            assertEquals(TreapNode(11,55), rt?.right)
            assertEquals(TreapNode(0,45), rt?.left)
            assertEquals(TreapNode(7,50), rt?.right?.left)
            assertEquals(TreapNode(10,30), rt?.right?.left?.right)
            assertEquals(TreapNode(2,40), rt?.left?.right)
            assertEquals(TreapNode(3,10), rt?.left?.right?.right)
            assertEquals(TreapNode(1,0), rt?.left?.right?.left)
            assertEquals(TreapNode(-1,20), rt?.left?.left)
        }

        @Test
        fun changingSubTreesFarFromRoot() {
            assertEquals(TreapNode(20,100), baum.add(20,100))
            baum.add(50,90)
            baum.add(35,80)
            assertEquals(TreapNode(40,70), baum.add(40,70))
            baum.add(30,60)
            baum.add(70,50)
            baum.add(60,40)
            assertEquals(TreapNode(80,30), baum.add(80,30))
            baum.add(32, 85)
            baum.add(75, 65)

            val rt = baum.root()

            assertEquals(TreapNode(20,100), rt)
            assertEquals(TreapNode(32,85), rt?.right?.left)
            assertEquals(TreapNode(75,65), rt?.right?.right)
            assertEquals(TreapNode(30, 60), rt?.right?.left?.left)
            assertEquals(TreapNode(35,80), rt?.right?.left?.right)
            assertEquals(TreapNode(40,70), rt?.right?.left?.right?.right)
            assertEquals(TreapNode(80,30), rt?.right?.right?.right)
            assertEquals(TreapNode(70,50), rt?.right?.right?.left)
            assertEquals(TreapNode(60,40), rt?.right?.right?.left?.left)
        }

        @Test
        fun addingAlreadyExistingKeys() {
            baum.add(20,100)
            baum.add(50,90)
            baum.add(35,80)
            baum.add(40,70)
            baum.add(30,60)

            assertEquals(null, baum.add(35, 80))
            assertEquals(null, baum.add(35, 40))
            assertEquals(null, baum.add(20, 100))
            assertEquals(null, baum.add(30, 80))
        }
    }
}