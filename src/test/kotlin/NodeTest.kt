import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import treeLib.nodes.BSTNode
import kotlin.test.*

class NodeTest {
    var node01 = BSTNode(1, 123)
    var node02 = BSTNode(2, 321, right = node01)
    var node2L = BSTNode(2, 321, left = node02)
    var node03 = BSTNode(3, "node02 и node2L отличаются лишь ссылками")

    @BeforeEach
    fun init() {
        node01 = BSTNode(1, 123)
        node02 = BSTNode(2, 321)
        node03 = BSTNode(3, "waiting for something to happen?")
    }

    @Nested
    inner class PublicNodeMethods {
        @Test
        @DisplayName("Compare to ==")
        fun compare0() {
            assertEquals(0, node02.compareTo(node2L))
        }

        @Test
        @DisplayName("Compare to >")
        fun compare1() {
            assertTrue(node02.compareTo(node01) > 0)
        }

        @Test
        @DisplayName("Compare to <")
        fun compare2() {
            assertTrue(node01.compareTo(node2L) < 0)
        }

        @Test
        @DisplayName("Hash code on one node twice")
        fun hashCode1() {
            assertEquals(node03.hashCode(), node03.hashCode())
        }

        @Test
        @DisplayName("Hash code on two equal nodes")
        fun hashCode2() {
            assertEquals(node02.hashCode(), node2L.hashCode())
        }

        @Test
        @DisplayName("Hash code on two different nodes")
        fun hashCode3() {
            assertNotEquals(node01.hashCode(), node02.hashCode())
        }

        @Test
        @DisplayName("Equals is true on one node")
        fun equals1() {
            assertTrue(node03.equals(node03))
        }

        @Test
        @DisplayName("Equals is true if node's key and value are equals")
        fun equals2() {
            assertTrue(node2L.equals(node02) and node02.equals(node2L))
        }

        @Test
        @DisplayName("Equals is not equal to null")
        fun equals3() {
            assertFalse(node03.equals(null))
        }

        @Test
        @DisplayName("Equals is false if nodes are different")
        fun equals4() {
            assertFalse(node01.equals(node02))
        }

        @Test
        @DisplayName("To string")
        fun toString1() {
            assertEquals(node01.toString(), "(1, 123)")
        }

        @Test
        @DisplayName("To pair")
        fun toPair1() {
            assertEquals(node01.toPair(), Pair(1, 123))
        }

        @Test
        @DisplayName("I mean we I believe that key() and value() works correctly, whatever")
        fun keyValue() {
            assertEquals(node03.key(), 3)
            assertEquals(node03.value(), "waiting for something to happen?")
        }
    }
}
