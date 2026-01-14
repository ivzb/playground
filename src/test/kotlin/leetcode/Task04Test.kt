package leetcode

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class Task04Test {

    private val task = Task04()

    @ParameterizedTest(name = "Test {index}: nums1={0}, nums2={1} -> expected={2}")
    @MethodSource("provideTestCases")
    fun twoSum(
        nums1: IntArray,
        nums2: IntArray,
        expected: Double
    ) {
        assertEquals(expected, task.findMedianSortedArrays(nums1, nums2))
    }

    companion object {
        @JvmStatic
        fun provideTestCases() = listOf(
            Arguments.of(
                intArrayOf(1, 3),
                intArrayOf(2),
                2.0,
            ),

            Arguments.of(
                intArrayOf(1, 2),
                intArrayOf(3, 4),
                2.5,
            ),
        )
    }
}