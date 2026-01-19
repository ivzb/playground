package leetcode

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class Task16Test {

    private val task = Task16()

    @ParameterizedTest(name = "Test {index}: nums={0} -> expected={1}")
    @MethodSource("provideTestCases")
    fun threeSum(
        nums: IntArray,
        target: Int,
        expected: Int,
    ) {
        assertEquals(expected, task.threeSumClosest(nums, target))
    }

    companion object {
        @JvmStatic
        fun provideTestCases() = listOf(
            Arguments.of(
                intArrayOf(-1,2,1,-4),
                1,
                2,
            ),

            Arguments.of(
                intArrayOf(0,0,0),
                1,
                0,
            ),

            Arguments.of(
                intArrayOf(4,0,5,-5,3,3,0,-4,-5),
                -2,
                -2,
            ),
        )
    }
}