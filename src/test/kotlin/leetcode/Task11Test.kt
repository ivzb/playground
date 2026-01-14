package leetcode

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class Task11Test {

    private val task = Task11()

    @ParameterizedTest(name = "Test {index}: height={0} -> expected={1}")
    @MethodSource("provideTestCases")
    fun maxArea(
        height: IntArray,
        expected: Int,
    ) {
        assertEquals(expected, task.maxArea(height))
    }

    companion object {
        @JvmStatic
        fun provideTestCases() = listOf(
            Arguments.of(
                intArrayOf(1,8,6,2,5,4,8,3,7),
                49,
            ),

            Arguments.of(
                intArrayOf(1, 1),
                1,
            ),
        )
    }
}