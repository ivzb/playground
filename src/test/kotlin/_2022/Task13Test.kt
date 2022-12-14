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
        ).forEach { expected ->
            val actual = Task13.parseTree(expected).toString()
            assertEquals(expected, actual)
        }
    }

    @Test
    fun testIsInRightOrder() {
        listOf(
            true to ("[1,1,3,1,1]" to "[1,1,5,1,1]"),
            true to ("[[1],[2,3,4]]" to "[[1],4]"),
            false to ("[9]" to "[[8,7,6]]"),
            true to ("[[4,4],4,4]" to "[[4,4],4,4,4]"),
            false to ("[7,7,7,7]" to "[7,7,7]"),
            true to ("[]" to "[3]"),
            false to ("[[[]]]" to "[[]]"),
            false to ("[1,[2,[3,[4,[5,6,7]]]],8,9]" to "[1,[2,[3,[4,[5,6,0]]]],8,9]"),
        ).forEach { (expected, trees) ->
            val (left, right) = trees
            val actual = Task13.isInRightOrder(left, right)
            assertEquals(expected, actual)
        }

    }
}