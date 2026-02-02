package leetcode

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.collections.sortedWith

class Task33Test {

    private val task = Task33()

    @ParameterizedTest(name = "Test {index}: nums={0}, target={1} -> expected={2}")
    @MethodSource("provideTestCases")
    fun search(
        nums: IntArray,
        target: Int,
        expected: Int,
    ) {
        val actual = task.search(nums, target)
        assertEquals(expected, actual)
    }

    companion object {
        @JvmStatic
        fun provideTestCases() = listOf(
            Arguments.of(
                intArrayOf(4,5,6,7,0,1,2),
                0,
                4,
            ),

            Arguments.of(
                intArrayOf(4,5,6,7,0,1,2),
                3,
                -1,
            ),

            Arguments.of(
                intArrayOf(1),
                0,
                -1,
            ),

            Arguments.of(
                intArrayOf(1),
                2,
                -1,
            ),

            Arguments.of(
                intArrayOf(1, 3),
                3,
                1,
            ),

            Arguments.of(
                intArrayOf(3, 1),
                3,
                0,
            ),

            Arguments.of(
                intArrayOf(1, 3, 5),
                3,
                1,
            ),

            Arguments.of(
                intArrayOf(3, 5, 1),
                3,
                0,
            ),

            Arguments.of(
                intArrayOf(3, 5, 1),
                5,
                1,
            ),
        )
    }
}