package euler

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SolutionTests {

    @Test
    fun task1_multiples_of_3_or_5() {
        val expected = 233168
        val actual = Task01.solution()
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun task2_even_fibonacci_numbers() {
        val expected = 4613732
        val actual = Task02.solution()
        Assertions.assertEquals(expected, actual)
    }
}