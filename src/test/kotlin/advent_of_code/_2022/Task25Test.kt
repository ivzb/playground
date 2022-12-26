package advent_of_code._2022

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Task25Test {

    @Test
    fun convertFromSNAFU() {
        mapOf(
            "1=-0-2" to 1747L,
            "12111" to 906L,
            "2=0=" to 198L,
            "21" to 11L,
            "2=01" to 201L,
            "111" to 31L,
            "20012" to 1257L,
            "112" to 32L,
            "1=-1=" to 353L,
            "1-12" to 107L,
            "12" to 7L,
            "1=" to 3L,
            "122" to 37L,
            "2=01-0-2-0=-0==-1=01" to 30638862852576L,
        ).forEach { (input, expected) ->
            val actual = Task25.convertFromSNAFU(input)
            assertEquals(expected, actual)
        }
    }

    @Test
    fun convertToSNAFU() {
        mapOf(
            4890L to "2=-1=0",
            1L to "1",
            2L to "2",
            3L to "1=",
            4L to "1-",
            5L to "10",
            6L to "11",
            7L to "12",
            8L to "2=",
            9L to "2-",
            10L to "20",
            15L to "1=0",
            20L to "1-0",
            2022L to "1=11-2",
            12345L to "1-0---0",
            314159265L to "1121-1110-1=0",
            30638862852576L to "2=01-0-2-0=-0==-1=01",
        ).forEach { (input, expected) ->
            val actual = Task25.convertToSNAFU(input)
            assertEquals(expected, actual)
        }
    }
}