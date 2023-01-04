package euler

import EulerTask
import utils.Math.isPandigital

object Task32PandigitalProducts : EulerTask("Pandigital products") {

    override fun solution(): Long {
        val products = HashSet<Long>()

        for (multiplicand in 1..1_000L) {
            if (!multiplicand.isPandigital()) continue
            val multiplicandDigits = multiplicand.toString().toSet()

            for (multiplier in multiplicand..2_000L) {
                if (!multiplier.isPandigital()) continue
                val multiplierDigits = multiplier.toString().toSet()

                val product = multiplicand * multiplier

                if (!product.isPandigital()) continue
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


}
