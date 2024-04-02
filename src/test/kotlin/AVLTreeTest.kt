import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import treeLib.bintrees.AVLTree
import treeLib.nodes.AVLNode
import kotlin.math.abs
import org.junit.jupiter.api.Test


class AVLTreeTest {
	var tree = AVLTree<Int, String>()

	@Nested
	inner class InsertionTests {
		@Test
		fun useAddOnEmptyTree() {
			tree.clear()
			tree.add(0, "a")
			assertEquals(0, tree.root()?.key)
			assertEquals("a", tree.root()?.value)
		}

		@BeforeEach
		fun setUp() {
			tree.add(0, "a")
			tree.add(1, "b")
			tree.add(-1, "b")
		}

		@Test
		fun addByKey () {
			assertEquals(-1, tree.root()?.left?.key)
			assertEquals("b", tree.root()?.left?.value)
			assertEquals(1, tree.root()?.right?.key)
			assertEquals("b", tree.root()?.right?.value)
		}

		@Test
		fun addDoesNothingWhenKeyExists() {
			tree.add(0, "a")
			tree.add(1, "b")
			tree.add(-1, "b")
			assertEquals(null, tree.root()?.right?.right)
			assertEquals(null, tree.root()?.right?.left)
		}
	}

	@Nested
	inner class RemoveTests {

		@BeforeEach
		fun setUp() {
			for (i in 0..10) {
				tree.add(i, "test")
			}
		}

		@Test
		fun removeOnRoot() {
			val key : Int? = tree.root()?.key
			if (key != null) {
				tree.remove(key)
				assertNotNull(tree.root())
				for (i in 0..10) {
					if (i == key) {
						assertNull(tree.findByKey(key))
					} else {
						assertNotNull(tree.findByKey(i))
					}
				}
			} else {
				fail()
			}
		}

		@Test
		fun removeNodeWithNoChildren(){
			tree.remove(10)
			assertNull(tree.findByKey(10))
			for (i in 0..9) {
				assertNotNull(tree.findByKey(i))
			}
		}

		@Test
		fun removeNodeWithTwoChildren(){
			tree.remove(7)
			assertNull(tree.findByKey(7))
			for (i in 0..10) {
				if (i != 7) {
					assertNotNull(tree.findByKey(i))
				}
			}
		}

		@Test
		fun removeNonExistentNode() {
			assertNull(tree.remove(999))
			for (i in 0..10) {
				assertNotNull(tree.findByKey(i))
			}
		}
	}

	@Nested
	inner class BalanceTests {
		private fun isBalanced(node: AVLNode<Int, String>?): Boolean {
			fun height(node: AVLNode<Int, String>?): Int {
				if (node == null) {
					return 0
				}
				return node.height
			}

			fun balanceFactor(node: AVLNode<Int, String>?): Int {
				if (node == null) {
					throw NullPointerException("Node cannot be null.")
				}
				return height(node.right) - height(node.left)
			}

			if (node == null) return true

			if (abs(balanceFactor(node)) <= 1 && isBalanced(node.left) && isBalanced(node.right)) {
				return true
			}

			return false
		}

		@BeforeEach
		fun setUpRandomTree(){
			val ranLen = (2..100).random()
			for (i in 1..ranLen) {
				val ranItem = (1..10000).random()
				tree.add(ranItem, "test")
			}
		}

		@Test
		fun afterInsertAVLIsBalanced() {
			assertTrue(isBalanced(tree.root()))
		}

		@Test
		fun afterRemoveAVLIsBalanced() {
			tree.root()?.left?.let { tree.remove(it.key) }
			assertTrue(isBalanced(tree.root()))
		}

		@Test
		fun afterRootRemoveAVLIsBalanced(){
			val key : Int? = tree.root()?.key
			if (key != null) {
				tree.remove(key)
				assertTrue(isBalanced(tree.root()))
			}
		}
	}

	@Nested
	inner class InterfaceMethods {

		@BeforeEach
		fun setUp() {
			for (i in 0..10) {
				tree.add(i, "test")
			}
		}

		@Test
		fun findReturnsTrueNode() {
			val rightNode = tree.findByKey(7)
			val leftNode = tree.findByKey(1)
			assertEquals(tree.root()?.right, rightNode)
			assertEquals(tree.root()?.left, leftNode)
			assertNull(tree.findByKey(999))
		}

		@Test
		fun changeValChangesVal() {
			tree.changeVal(3, "tomato")
			tree.changeVal(7, "banana")
			tree.changeVal(1, "tea")

			assertEquals("tomato", tree.root()?.value)
			assertEquals("banana", tree.root()?.right?.value)
			assertEquals("tea", tree.root()?.left?.value)
			assertNull(tree.changeVal(99999, "tea"))
		}

		@Test
		fun findMax(){
			assertEquals(tree.findByKey(10), tree.max())
			tree.clear()
			assertNull(tree.max())
		}

		@Test
		fun findMin(){
			assertEquals(tree.findByKey(0), tree.min())
			tree.clear()
			assertNull(tree.min())
		}

		@Test
		fun countNodes() {
			assertEquals(11, tree.countNodes())
			tree.remove(7)
			assertEquals(10, tree.countNodes())
			tree.clear()
			assertEquals(0, tree.countNodes())
		}

		@Test
		fun height() {
			assertEquals(4, tree.height())
			tree.clear()
			assertEquals(0, tree.height())
		}
	}
}