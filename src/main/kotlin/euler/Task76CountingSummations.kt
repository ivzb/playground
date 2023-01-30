package euler

import EulerTask

object Task76CountingSummations : EulerTask("Counting summations") {

    override fun solution(): Int {
        val n = 100
        val m = Array(n + 1) { IntArray(n + 1) }

        for (i in 1..n) {
            for (j in 1..n) {
                m[i][j] = when {
                    j == 1 || i == 1 -> 1
                    i < j -> m[i][i]
                    i == j -> m[i][i - 1] + 1
                    else -> m[i][j - 1] + m[i - j][j]
                }
            }
        }

        return m[n][n] - 1
    }

}
