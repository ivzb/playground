package _2022

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Task13Test {

    @Test
    fun testParseTrees() {
        listOf(
            "[1,1,3,1,1]",
            "[1,1,5,1,1]",
            "[[1],[2,3,4]]",
            "[[1],4]",
            "[9]",
            "[[8,7,6]]",
            "[[4,4],4,4]",
            "[[4,4],4,4,4]",
            "[7,7,7,7]",
            "[7,7,7]",
            "[]",
            "[3]",
            "[[[]]]",
            "[[]]",
            "[1,[2,[3,[4,[5,6,7]]]],8,9]",
            "[1,[2,[3,[4,[5,6,0]]]],8,9]",
            "[10,5]"
        ).map { expected ->
            val actual = Task13.parseTree(expected).toString()
            assertEquals(expected, actual)
        }
    }

    @Test
    fun testIsInRightOrder() {
       testIsInRightOrder("[1,1,3,1,1]", "[1,1,5,1,1]", true)
    }

    @Test
    fun testIsInRightOrder2() {
        testIsInRightOrder("[[1],[2,3,4]]", "[[1],4]", true)
    }

    @Test
    fun testIsInRightOrder3() {
        testIsInRightOrder("[9]", "[[8,7,6]]", false)
    }

    @Test
    fun testIsInRightOrder4() {
        testIsInRightOrder("[[4,4],4,4]", "[[4,4],4,4,4]", true)
    }

    @Test
    fun testIsInRightOrder5() {
        testIsInRightOrder("[7,7,7,7]", "[7,7,7]", false)
    }

    @Test
    fun testIsInRightOrder6() {
        testIsInRightOrder("[]", "[3]", true)
    }

    @Test
    fun testIsInRightOrder7() {
        testIsInRightOrder("[[[]]]", "[[]]", false)
    }

    @Test
    fun testIsInRightOrder8() {
        testIsInRightOrder("[1,[2,[3,[4,[5,6,7]]]],8,9]", "[1,[2,[3,[4,[5,6,0]]]],8,9]", false)
    }

    private fun testIsInRightOrder(left: String, right: String, expected: Boolean) {
        val actual = Task13.isInRightOrder(left, right)
        assertEquals(expected, actual)
    }
}