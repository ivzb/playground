package euler

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class EulerTests {

    @Test
    fun task1_multiples_of_3_or_5() {
        val expected = 233168
        val actual = Task01MultiplesOf3Or5.solution()
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun task2_even_fibonacci_numbers() {
        val expected = 4613732
        val actual = Task02EvenFibonacciNumbers.solution()
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun task3_largest_prime_factor() {
        val expected = 6857
        val actual = Task03LargestPrimeFactor.solution()
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun task4_largest_palindrome_product() {
        val expected = 906609
        val actual = Task04LargestPalindromeProduct.solution()
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun task5_smallest_multiple() {
        val expected = 232792560
        val actual = Task05SmallestMultiple.solution()
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun task6_sum_square_difference() {
        val expected = 25164150
        val actual = Task06SumSquareDifference.solution()
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun task7_10001st_prime() {
        val expected = 104743
        val actual = Task07_10001stPrime.solution()
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun task8_largest_product_in_a_series() {
        val expected = 23514624000
        val actual = Task08LargestProductInASeries.solution()
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun task9_largest_product_in_a_series() {
        val expected = 31875000
        val actual = Task09SpecialPythagoreanTriplet.solution()
        Assertions.assertEquals(expected, actual)
    }

}