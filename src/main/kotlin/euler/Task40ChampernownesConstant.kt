package euler

import EulerTask
import utils.Math.product

object Task40ChampernownesConstant : EulerTask("Champernowne's constant") {

    override fun solution(): Int = solution_brute_force()

    private fun solution_brute_force(): Int {
        val result = StringBuilder()
        result.append('.')

        var n = 0

        while (result.length <= 1_000_000) {
            n++
            result.append(n)
        }

        return listOf(
            result[1],
            result[10],
            result[100],
            result[1_000],
            result[10_000],
            result[100_000],
            result[1_000_000],
        ).map { it.digitToInt() }.product()
    }

    private fun solution_math(): Int {
        var length = 0
        var n = 1
        var next = 1
        var prev = 0
        var sum = 1

        while (length < 1_000_000) {
            val s = n.toString()
            length += s.length

            if (length >= next) {
                sum *= s[next - prev - 1].digitToInt()
                next *= 10
            }

            prev = length
            n++
        }

        return sum
    }

}
