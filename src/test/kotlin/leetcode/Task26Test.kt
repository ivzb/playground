package leetcode

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.collections.sortedWith

class Task26Test {

    private val task = Task26()

    @ParameterizedTest(name = "Test {index}: nums={0} -> expected={1}")
    @MethodSource("provideTestCases")
    fun removeDuplicates(
        nums: IntArray,
        expected: Int,
    ) {
        assertEquals(expected, task.removeDuplicates(nums))
    }

    companion object {
        @JvmStatic
        fun provideTestCases() = listOf(
            Arguments.of(
                intArrayOf(1,1,2),
                2,
            ),

            Arguments.of(
                intArrayOf(0,0,1,1,1,2,2,3,3,4),
                5,
            ),
        )
    }
}