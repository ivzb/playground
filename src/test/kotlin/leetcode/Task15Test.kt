package leetcode

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class Task15Test {

    private val task = Task15()

    @ParameterizedTest(name = "Test {index}: nums={0} -> expected={1}")
    @MethodSource("provideTestCases")
    fun threeSum(
        nums: IntArray,
        expected: List<List<Int>>,
    ) {
        assertEquals(expected, task.threeSum(nums).sortedBy { (x, y, z) -> x })
    }

    companion object {
        @JvmStatic
        fun provideTestCases() = listOf(
            Arguments.of(
                intArrayOf(-1, 0, 1, 2, -1, -4),
                listOf(listOf(-1, -1, 2), listOf(-1, 0, 1)),
            ),

            Arguments.of(
                intArrayOf(0, 1, 1),
                listOf<List<Int>>(),
            ),

            Arguments.of(
                intArrayOf(0, 0, 0),
                listOf(listOf(0, 0, 0)),
            ),

            Arguments.of(
                intArrayOf(2,-3,0,-2,-5,-5,-4,1,2,-2,2,0,2,-4,5,5,-10),
                listOf(listOf(-10,5,5),listOf(-5,0,5),listOf(-4,2,2),listOf(-3,-2,5),listOf(-3,1,2),listOf(-2,0,2)),
            ),
        )
    }
}