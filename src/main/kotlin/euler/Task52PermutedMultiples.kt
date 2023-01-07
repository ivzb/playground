package euler

import EulerTask
import utils.Combinatorics.isPermutation

object Task52PermutedMultiples : EulerTask("Permuted multiples") {

    override fun solution(): Long {
        var n = 0L

        while (true) {
            n++

            if (!isPermutation(n, n * 2)) {
                continue
            }

            if (!isPermutation(n, n * 3)) {
                continue
            }

            if (!isPermutation(n, n * 4)) {
                continue
            }

            if (!isPermutation(n, n * 5)) {
                continue
            }

            if (!isPermutation(n, n * 6)) {
                continue
            }

            return n
        }
    }
}
