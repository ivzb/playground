import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Task01Test {

    @Test
    fun run() {
        val actual = Task01().run()

        assertEquals("5", actual)
    }
}