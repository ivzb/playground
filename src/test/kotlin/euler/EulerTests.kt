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
        val expected = 6857L
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
        val expected = 104743L
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

    @Test
    fun task10_summation_of_primes() {
        val expected = 142913828922
        val actual = Task10SummationOfPrimes.solution()
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun task11_largest_product_in_a_grid() {
        val expected = 70600674L
        val actual = Task11LargestProductInAGrid.solution()
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun task12_highly_divisible_triangular_number() {
        val expected = 76576500L
        val actual = Task12HighlyDivisibleTriangularNumber.solution()
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun task13_large_sum() {
        val expected = 5537376230L
        val actual = Task13LargeSum.solution()
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun task14_longest_collatz_sequence() {
        val expected = 837799L
        val actual = Task14LongestCollatzSequence.solution()
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun task15_lattice_paths() {
        val expected = 137846528820
        val actual = Task15LatticePaths.solution()
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun task16_power_of_digit_sum() {
        val expected = 1366
        val actual = Task16PowerDigitSum.solution()
        Assertions.assertEquals(expected, actual)
    }

}