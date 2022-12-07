import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class InputTest {

    @Test
    fun run() {
        mapOf(
            _2021.Task01 to (1832 to 1858),
            _2022.Task02 to (2147104 to 2044620088),

            _2022.Task01 to (66306 to 195292),
            _2022.Task02 to (13446 to 13509),
            _2022.Task03 to (7997 to 2545),
            _2022.Task04 to (466 to 865),
            _2022.Task05 to ("GRTSWNJHH" to "QLFQDBBHM"),
            _2022.Task06 to (1779 to 2635),
            _2022.Task07 to (2104783 to 5883165),
        ).forEach { task, (expectedPartA, expectedPartB) ->
            assertEquals(expectedPartA, task.partA())
            assertEquals(expectedPartB, task.partB())
        }
    }
}