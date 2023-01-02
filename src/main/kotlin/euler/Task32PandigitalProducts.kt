package euler

import EulerTask

object Task32PandigitalProducts : EulerTask("Pandigital products") {

    override fun solution(): Int {
        val products = HashSet<Int>()

        for (multiplicand in 1..1_000) {
            if (!isPandigital(multiplicand)) continue
            val multiplicandDigits = multiplicand.toString().toSet()

            for (multiplier in multiplicand..2_000) {
                if (!isPandigital(multiplier)) continue
                val multiplierDigits = multiplier.toString().toSet()

                val product = multiplicand * multiplier

                if (!isPandigital(product)) continue
                val productDigits = product.toString().toSet()

                val distinctDigits: Set<Char> = multiplicandDigits + multiplierDigits + productDigits
                val allDigitsSize = multiplicandDigits.size + multiplierDigits.size + productDigits.size

                if (distinctDigits.size == 9 && distinctDigits.size == allDigitsSize && !distinctDigits.contains('0')) {
                    products += product
                }
            }
        }

        return products.sum()
    }

    private val cache = HashMap<Int, Boolean>()

    private fun isPandigital(number: Int): Boolean =
        cache.getOrPut(number) {
            val n = number.toString().toCharArray()
            n.size == n.distinct().size
        }

}
