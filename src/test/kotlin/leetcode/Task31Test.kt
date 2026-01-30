package leetcode

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.collections.sortedWith

class Task31Test {

    private val task = Task31()

    @ParameterizedTest(name = "Test {index}: nums={0} -> expected={1}")
    @MethodSource("provideTestCases")
    fun nextPermutation(
        nums: IntArray,
        expected: IntArray,
    ) {
        task.nextPermutation(nums)
        assertEquals(expected.toList(), nums.toList())
    }

    companion object {
        @JvmStatic
        fun provideTestCases() = listOf(
            Arguments.of(
                intArrayOf(1,2,3),
                intArrayOf(1,3,2),
            ),

            Arguments.of(
                intArrayOf(3,2,1),
                intArrayOf(1,2,3),
            ),

            Arguments.of(
                intArrayOf(1,1,5),
                intArrayOf(1,5,1),
            ),
        )
    }
}