package euler

import EulerTask
import utils.Math.findPrimesUntil

object Task88ProductSumNumbers : EulerTask("Product-sum numbers") {

    private val primes = findPrimesUntil(13_000).toSet()

    override fun solution(): Int {
        val allProducts = products()
        val solutions = HashSet<Int>()

        main@for (k in 2.. 12_000) {
            for (n in k .. 12_200) {
                for (product in allProducts[n]) {
                    val sum = product.sum() + (k - product.size)

                    if (sum == n) {
                        solutions.add(n)
                        continue@main
                    }
                }
            }
        }

        return solutions.sum()
    }

    private fun products(): Array<List<List<Int>>> {
        val all = Array<List<List<Int>>>(12_201) { listOf() }

        for (n in 2..12_200) {
            if (primes.contains(n.toLong())) {
                continue
            }

            val MAXLN = 20
            val mp = Array(MAXLN) { 0 }

            val r = ArrayList<List<Int>>()

            fun print(length: Int) {
                val result = ArrayList<Int>()

                for (i in 1..length) {
                    result.add(mp[i])
                }

                r.add(result)
            }

            fun devNum(n: Int, pos: Int) {
                if (n == 1) {
                    print(pos - 1)
                } else {
                    for (k in n downTo 2) {
                        mp[pos] = k

                        if (mp[pos] <= mp[pos - 1] && n % k == 0) {
                            devNum(n / k, pos + 1)
                        }
                    }
                }
            }

            mp[0] = n + 1
            devNum(n, 1)

            all[n] = r
        }

        return all
    }

}
