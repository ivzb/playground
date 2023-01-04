package utils

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import utils.Math.isPalindrome
import utils.Math.reversed

class MathTests {

    @Test
    fun findPrimes() {
        val expected = listOf(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409, 419, 421, 431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, 503, 509, 521, 523, 541)
        val actual = Math.findPrimes().take(100).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun isPalindrome() {
        listOf(3, 66, 101, 9009, 50905).forEach { palindrome -> assertTrue(palindrome.isPalindrome()) }
        listOf(65, 103, 9019, 51905).forEach { nonPalindrome -> assertFalse(nonPalindrome.isPalindrome()) }
    }

    @Test
    fun reverse() {
        mapOf(
            5 to 5,
            12 to 21,
            23 to 32,
            123 to 321
        ).forEach { (input, expected) ->
            val actual = input.reversed()
            assertEquals(expected, actual)
        }
    }

}