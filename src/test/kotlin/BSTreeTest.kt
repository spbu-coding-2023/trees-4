import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import treeLib.bintrees.BSTree
import treeLib.nodes.BSTNode

class BSTreeTest {
	var baum = BSTree<Int, String>()

	@Test
	fun findParent() {
		baum.add(4, "qwerty")
		baum.add(2, "Ge")
		baum.add(5, "rma")
		baum.add(-1, "ny")
		baum.add(3, "the")
		baum.add(40, "best")
		baum.add(44, "Ja")

		assertEquals(BSTNode(5, "rma"), baum.findParent(40)) //Parent with ein Kind

		assertEquals(BSTNode(2, "Ge"), baum.findParent(3)) //Parent with zwei Kinder

		assertEquals(baum.root(), baum.findParent(5)) //When Parent
		assertEquals(baum.root(), baum.findParent(2)) //is root

		assertNull(baum.findParent(4)) //Parent where child is root

		assertNull(baum.findParent(34)) //Finding No existing node's parent
	}


	@Nested
	inner class TestingAddMethods {

		@Test
		fun addToEmptyTree() {
			assertEquals(BSTNode(4, "qwerty"), baum.root())
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
			assertEquals(BSTNode(5, "rma"), rt?.right)
			assertEquals(BSTNode(3, "the"), rt?.left?.right)
			assertEquals(BSTNode(-1, "ny"), rt?.left?.left)
			assertEquals(BSTNode(44, "Ja"), rt?.right?.right?.right)
		}

		@Test
		fun checkAmountsOfNodes() {
			assertEquals(7, baum.countNodes())
			assertEquals(BSTNode(10, "Was"), baum.add(10, "Was"))
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
		fun addingExistingNodeButWithDifferentValues() {
			assertNull(baum.add(5, "Deutschland"))
			assertNotEquals("Deutschland", baum.root()?.right?.value)

			assertNull(baum.add(-1, "Deutschland"))
			assertNotEquals("Deutschland", baum.root()?.left?.left?.value)

			assertNull(baum.add(4, "Deutschland"))
			assertNotEquals("Deutschland", baum.root()?.value)
		}

		@Test
		fun orderMatters() {
			baum.clear()
			assertTrue(baum.addPairs(Pair(4, "Vier"), Pair(5, "Funf"), Pair(40, "Vierzig"), Pair(44, "Vierundvierzig")))
			var rt = baum.root()

			assertEquals(5, rt?.right?.key)
			assertEquals(44, rt?.right?.right?.right?.key)

			baum.clear()
			baum.addPairs(Pair(4, "Vier"), Pair(44, "Vierundvierzig"), Pair(40, "Vierzig"), Pair(5, "Funf"))
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
			for (key in listOf(10, 13, 12, 11, 20, 15, 25, -7, 5, -10, 9)) {
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

			baum.add(12, "test")
			baum.remove(11)
			assertEquals(BSTNode(12, "test"), rt?.right?.left)

			baum.remove(25)
			baum.remove(20)
			assertEquals(BSTNode(15, "test"), rt?.right?.right)
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
			assertEquals(BSTNode(15, "test"), rt?.right?.right?.left)
		}

		@Test
		fun removeNodeWithZweiKinderFifthCase() {
			baum.clear()

			baum.addPairs(Pair(-50, "Notf"), Pair(2, "Zwei"), Pair(101, "One hundred and One"))
			for (key in listOf(-30, 99, 95, 90, 78, 56, 45, 34, 23, 14, 7, 3, 5, 8)) {
				baum.add(key, "test")
			}

			val rt = baum.root()

			baum.remove(2)
			assertEquals(BSTNode(3, "test"), rt?.right)

			baum.remove(3)
			assertEquals(BSTNode(5, "test"), rt?.right)

			val ls = listOf(99, 95, 90, 78, 56, 45, 34, 23, 14, 7, 8, 5).sorted()
			for (i in 0..(ls.size - 2)) {
				baum.remove(ls[i])
				assertEquals(BSTNode(ls[i + 1], "test"), rt?.right)
			}

			assertNull(rt?.right?.right?.left)
		}

		@Test
		fun removeNotExistingOderAlreadyRemovedNode() {
			assertNull(baum.remove(62))
			assertNull(baum.remove(-24))
			assertFalse(baum.removePairs(-5, 6, 8, 3))

			assertEquals(11, baum.countNodes())

			assertEquals("test", baum.remove(13))
			assertNull(baum.remove(13))

			assertEquals(10, baum.countNodes())
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
		fun removeRootWithoutKinder() {
			baum.clear()

			baum.add(4, "Heil")
			baum.remove(4)

			assertNull(baum.root())
		}

		@Test
		fun removeRootWithOnlyRightKinder() {
			baum.clear()

			for (key in 0..10) baum.add(key, "test")

			for (i in 0..9) {
				baum.remove(i)
				assertEquals(BSTNode(i + 1, "test"), baum.root())
			}

			baum.remove(10)

			assertNull(baum.root())
			assertEquals(0, baum.countNodes())
		}

		@Test
		fun removeRootWithOnlyLeftKinder() {
			baum.clear()

			for (key in 0 downTo -10) baum.add(key, "test")

			for (i in 0 downTo -9) {
				baum.remove(i)
				assertEquals(BSTNode(i - 1, "test"), baum.root())
				assertEquals(10 + i, baum.countNodes())
			}

			baum.remove(-10)

			assertNull(baum.root())
			assertEquals(0, baum.countNodes())
		}

		@Test
		fun removeRootWithTwoKinder() {
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

			for (i in 0..10) {
				assertNotNull(baum.root())
				baum.remove(baum.root()?.key!!.toInt())
			}

			assertNull(baum.root())
			assertEquals(0, baum.countNodes())
		}

	}


	@Nested
	inner class SubTree {
		@BeforeEach
		fun setUp() {
			for (key in listOf(10, 13, 12, 11, 20, 15, 25, -7, 5, -10, 9)) {
				baum.add(key, "test")
			}
		}

		@Test
		fun deleteTheWholeRightAndLeftSubtee() {

			assertTrue(baum.deleteSubTree(13))
			assertNull(baum.root()?.right)

			assertTrue(baum.deleteSubTree(-7))
			assertNull(baum.root()?.left)

			assertEquals(1, baum.countNodes())
		}

		@Test
		fun deleteRootSubTree() {
			//You can't delete root in this way
			assertFalse(baum.deleteSubTree(10))

			assertEquals(BSTNode(10, "test"), baum.root())
			assertEquals(11, baum.countNodes())
		}

		@Test
		fun deleteSubTreeWithOnlyEinNode() {
			val rt = baum.root()

			baum.deleteSubTree(-10)
			assertEquals(BSTNode(-7, "test"), rt?.left)
			assertNull(rt?.left?.left)

			baum.deleteSubTree(25)
			assertEquals(BSTNode(20, "test"), rt?.right?.right)
			assertNull(rt?.right?.right?.right)

			baum.deleteSubTree(11)
			assertEquals(BSTNode(12, "test"), rt?.right?.left)
			assertNull(rt?.right?.left?.left)
		}

		@Test
		fun deleteNotExistingSubTree() {
			assertFalse(baum.deleteSubTree(34))
			assertEquals(11, baum.countNodes())
		}

		@Test
		fun deleteSubTreeByAlreadyRemovedKey() {
			baum.remove(-7)
			assertFalse(baum.deleteSubTree(-7))
			assertEquals(10, baum.countNodes())
		}

		@Test
		fun getSubTree() {
			var neuBaum = baum.getSubTree(13)

			assertEquals(BSTNode(13, "test"), neuBaum?.root())
			assertEquals(6, neuBaum?.countNodes())


			neuBaum = baum.getSubTree(-7)

			assertEquals(BSTNode(-7, "test"), neuBaum?.root())
			assertEquals(4, neuBaum?.countNodes())

			assertEquals(11, baum.countNodes())
		}

		@Test
		fun getRootSubTree() {
			//You can't get SubTree where current tree's root is new root
			assertNull(baum.getSubTree(10))
		}

		@Test
		fun getSubTreeByNotExistingKey() {
			assertNull(baum.getSubTree(23))
			assertNull(baum.getSubTree(-5))
			assertNull(baum.getSubTree(0))
		}
	}
}