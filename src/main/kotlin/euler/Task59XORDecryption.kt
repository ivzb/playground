package euler

import EulerTask
import readInput
import java.lang.StringBuilder

object Task59XORDecryption : EulerTask("XOR decryption") {

    override fun solution(): Int {
        val input = parseInput()
        val start = 'a'.code
        val end = 'z'.code

        for (i in start..end) {
            for (j in start..end) {
                for (k in start..end) {
                    val password = arrayOf(i, j, k)
                    val result = StringBuilder()

                    for ((index, encrypted) in input.withIndex()) {
                        val key = password[index % password.size]
                        val decrypted = encrypted xor key
                        result.append(decrypted.toChar())
                    }

                    if (result.contains("Euler")) {
                        return result.sumOf { it.code }
                    }
                }
            }
        }

        return -1
    }

    private fun parseInput() = readInput("euler/59")
        .split(",")
        .map { it.toInt() }

}
