package leetcode

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.collections.sortedWith

class Task27Test {

    private val task = Task27()

    @ParameterizedTest(name = "Test {index}: nums={0} -> expected={1}")
    @MethodSource("provideTestCases")
    fun removeElement(
        nums: IntArray,
        `val`: Int,
        expected: Int,
    ) {
        assertEquals(expected, task.removeElement(nums, `val`))
    }

    companion object {
        @JvmStatic
        fun provideTestCases() = listOf(
            Arguments.of(
                intArrayOf(3,2,2,3),
                3,
                2,
            ),

            Arguments.of(
                intArrayOf(0,1,2,2,3,0,4,2),
                2,
                5,
            ),
        )
    }
}