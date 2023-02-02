package euler

import EulerTask
import utils.pow

object Task78CoinPartitions : EulerTask("Coin partitions") {

    override fun solution(): Int = solution_euler_partitions()

    private const val MODULUS = 1_000_000

    private fun solution_counting(): Int {
        var n = 1

        while (true) {
            val partitions = IntArray(n)
            partitions[0] = 1

            for (i in 1 until n) {
                for (j in i until n) {
                    partitions[j] = (partitions[j] + partitions[j - i]) % MODULUS
                }

                if (partitions[i] == 0) {
                    return i
                }
            }

            n *= 2
        }
    }

    private fun solution_euler_partitions(): Int {
        val m = 100_000
        val p = IntArray(m)
        p[0] = 1

        for (n in 1..m + 1) {
            var sum = 0

            for (k in 1..n + 1) {
                var a = n - k * (3 * k + 1) / 2

                a = if (a < 0) {
                    0
                } else {
                    p[a]
                }

                var b = n - k * (3 * k - 1) / 2

                if (b < 0) {
                    break
                } else {
                    b = p[b]
                }

                sum += (-1).pow(k + 1) * (a + b)
            }

            val r = sum % MODULUS
            p[n] = r

            if (r == 0) {
                return n
            }
        }

        return -1
    }

}
