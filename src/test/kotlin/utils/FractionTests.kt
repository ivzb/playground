package utils

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class FractionTests {

    @Test
    fun fraction_plus() {
        mapOf(
            Fraction(1, 4) + Fraction(1, 4) to Fraction(2, 4),
            Fraction(1, 3) + Fraction(1, 6) to Fraction(3, 6),
            Fraction(1, 3) + Fraction(1, 5) to Fraction(8, 15),
            Fraction(1, 1) + Fraction(2, 5) to Fraction(7, 5),
        ).forEach { (actual, expected) ->
            Assertions.assertEquals(expected, actual)
        }
    }
}