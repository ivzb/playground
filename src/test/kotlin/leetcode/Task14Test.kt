package leetcode

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class Task14Test {

    private val task = Task14()

    @ParameterizedTest(name = "Test {index}: height={0} -> expected={1}")
    @MethodSource("provideTestCases")
    fun longestCommonPrefix(
        strs: Array<String>,
        expected: String,
    ) {
        assertEquals(expected, task.longestCommonPrefix(strs))
    }

    companion object {
        @JvmStatic
        fun provideTestCases() = listOf(
            Arguments.of(
                arrayOf("flower","flow","flight"),
                "fl",
            ),

            Arguments.of(
                arrayOf("dog","racecar","car"),
                "",
            ),
        )
    }
}