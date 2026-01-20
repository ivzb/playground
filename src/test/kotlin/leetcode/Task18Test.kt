package leetcode

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.collections.sortedWith

class Task18Test {

    private val task = Task18()

    @ParameterizedTest(name = "Test {index}: nums={0} -> expected={1}")
    @MethodSource("provideTestCases")
    fun threeSum(
        nums: IntArray,
        target: Int,
        expected: List<List<Int>>,
    ) {
        assertEquals(expected, task.fourSum(nums, target)
            .sortedWith(
                compareBy(
                    { (x) -> x},
                    { (_, y) -> y },
                    { (_, _, z) -> z }
                )
            )
        )
    }

    companion object {
        @JvmStatic
        fun provideTestCases() = listOf(
            Arguments.of(
                intArrayOf(1,0,-1,0,-2,2),
                0,
                listOf(listOf(-2,-1,1,2),listOf(-2,0,0,2),listOf(-1,0,0,1)),
            ),

            Arguments.of(
                intArrayOf(2,2,2,2,2),
                8,
                listOf(listOf(2,2,2,2)),
            ),

            Arguments.of(
                intArrayOf(1000000000,1000000000,1000000000,1000000000),
                -294967296,
                listOf<List<Int>>(),
            ),
        )
    }
}