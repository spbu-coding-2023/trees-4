import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import treeLib.bintrees.BSTree
import treeLib.bintrees.interfaces.Treap
import treeLib.nodes.TreapNode
import kotlin.test.assertEquals

class TreapTest {
    var baum = Treap<Int, Int>()


    @Nested
    inner class TestingAddingMethod {

        @Test
        fun addingRoot() {
            baum.add(24, 50)
            assertEquals(TreapNode(24, 50), baum.root())
        }


        @Test
        fun addingLikeInBST() {
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
        fun changinRoot() {
            baum.add(2, 10)
            baum.add(4,20)

            assertEquals(TreapNode(4,20), baum.root())
            assertEquals(TreapNode(2,10), baum.root()?.left)
        }
    }
}