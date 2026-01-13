package leetcode.task01_TwoSum

import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class SolutionTest {

    private val solution = Solution()

    @ParameterizedTest(name = "Test {index}: nums={0}, target={1} -> expected={2}")
    @MethodSource("provideTestCases")
    fun twoSum(
        nums: IntArray,
        target: Int,
        expected: IntArray
    ) {
        assertArrayEquals(expected, solution.twoSum(nums, target))
    }

    companion object {
        @JvmStatic
        fun provideTestCases() = listOf(
            Arguments.of(
                intArrayOf(2, 7, 11, 15),
                9,
                intArrayOf(0, 1)
            ),

            Arguments.of(
                intArrayOf(3, 2, 4),
                6,
                intArrayOf(1, 2)
            ),

            Arguments.of(
                intArrayOf(3, 3),
                6,
                intArrayOf(0, 1)
            )
        )
    }
}