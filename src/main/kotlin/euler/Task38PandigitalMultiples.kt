package euler

import EulerTask
import utils.Math.isPandigital
import java.lang.Long.max

object Task38PandigitalMultiples : EulerTask("Pandigital multiples") {

    override fun solution(): Long {
        var best = 0L

        for (i in 1..10_000L) {
            var concatenatedProduct = ""

            for (n in 1..10) {
                val product = i * n

                if (!product.isPandigital()) {
                    break
                }

                concatenatedProduct += product

                if (concatenatedProduct.length > 9) {
                    break
                }

                if (concatenatedProduct.length == 9) {
                    val result = concatenatedProduct.toLong()

                    if (result.isPandigital()) {
                        best = max(best, result)
                    }
                }
            }
        }

        return best
    }

}
