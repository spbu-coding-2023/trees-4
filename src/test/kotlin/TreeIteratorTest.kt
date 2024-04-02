import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import treeLib.bintrees.BSTree
import treeLib.nodes.BSTNode
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class TreeIteratorTest {
	var bft31 = BSTree<Int, Int>()

	@BeforeEach
	fun init() {
		bft31 = BSTree()
		var k = 32
		for (i in listOf(1, 2, 4, 8, 16)) {
			for (j in 0..<i)
				bft31.add(k / 2 + k * j, k / 2 + k * j)
			k /= 2
		}
	}

	@Test
	@DisplayName("Standard (in-order) iterator")
	fun iterator1() {
		val nodes = List(31) { BSTNode(it + 1, it + 1) }
		var index = 0
		for (node in bft31) {
			assertEquals(nodes[index++], node)
		}
	}

	@Test
	@DisplayName("Pre-order iterator")
	fun iterator2() {
		val nodes = listOf(
			BSTNode(16, 16), BSTNode(8, 8), BSTNode(4, 4),
			BSTNode(2, 2), BSTNode(1, 1), BSTNode(3, 3),
			BSTNode(6, 6), BSTNode(5, 5), BSTNode(7, 7),
			BSTNode(12, 12), BSTNode(10, 10), BSTNode(9, 9),
			BSTNode(11, 11), BSTNode(14, 14), BSTNode(13, 13),
			BSTNode(15, 15), BSTNode(24, 24), BSTNode(20, 20),
			BSTNode(18, 18), BSTNode(17, 17), BSTNode(19, 19),
			BSTNode(22, 22), BSTNode(21, 21), BSTNode(23, 23),
			BSTNode(28, 28), BSTNode(26, 26), BSTNode(25, 25),
			BSTNode(27, 27), BSTNode(30, 30), BSTNode(29, 29),
			BSTNode(31, 31)
		)

		var index = 0
		val iterator = bft31.preOrderIterator()
		while (iterator.hasNext())
			assertEquals(nodes[index++], iterator.next())
	}

	@Test
	@DisplayName("Post-order iterator")
	fun iterator3() {
		val nodes = listOf(
			BSTNode(1, 1), BSTNode(3, 3), BSTNode(2, 2),
			BSTNode(5, 5), BSTNode(7, 7), BSTNode(6, 6),
			BSTNode(4, 4), BSTNode(9, 9), BSTNode(11, 11),
			BSTNode(10, 10), BSTNode(13, 13), BSTNode(15, 15),
			BSTNode(14, 14), BSTNode(12, 12), BSTNode(8, 8),
			BSTNode(17, 17), BSTNode(19, 19), BSTNode(18, 18),
			BSTNode(21, 21), BSTNode(23, 23), BSTNode(22, 22),
			BSTNode(20, 20), BSTNode(25, 25), BSTNode(27, 27),
			BSTNode(26, 26), BSTNode(29, 29), BSTNode(31, 31),
			BSTNode(30, 30), BSTNode(28, 28), BSTNode(24, 24),
			BSTNode(16, 16)
		)

		var index = 0
		val iterator = bft31.postOrderIterator()
		while (iterator.hasNext())
			assertEquals(nodes[index++], iterator.next())
	}

	@Test
	@DisplayName("Iteration in empty tree")
	fun iterator4() {
		bft31.clear()
		assertFalse(bft31.iterator().hasNext())
	}
}
