package euler

import EulerTask
import utils.Math.findPrimesUntil

object Task51PrimeDigitReplacements : EulerTask("Consecutive prime sum") {

    private val primes = findPrimesUntil(1_000_000).toSet()

    override fun solution(): Long {
        var n = 1L

        while (true) {
            val number = n.toString()
            val length = number.length
            val masks = HashSet<String>()

            for (i in 0..length) {
                for (j in 0..length) {
                    for (k in 0..length) {
                        val sb = StringBuilder(number)
                        sb.insert(i, '*')
                        sb.insert(j, '*')
                        sb.insert(k, '*')
                        masks.add(sb.toString())
                    }
                }
            }

            for (mask in masks) {
                val family = ArrayList<Long>()

                for (i in 0..9) {
                    if (i == 0 && mask[0] == '*') {
                        continue
                    }

                    val prime = mask.replace('*', i.digitToChar()).toLong()

                    if (primes.contains(prime)) {
                        family.add(prime)
                    }
                }

                if (family.size >= 8) {
                    return family.min()
                }
            }

            n++
        }
    }
}
