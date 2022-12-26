package euler

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SolutionTests {

    @Test
    fun task1() {
        val expected = 233168
        val actual = Task01.solution()
        Assertions.assertEquals(expected, actual)
    }
}