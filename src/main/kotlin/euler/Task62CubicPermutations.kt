package euler

import EulerTask
import utils.Math.digits

object Task62CubicPermutations : EulerTask("Cubic permutations") {

    override fun solution(): Long {
        val cubicPermutations = HashMap<String, ArrayList<Long>>()

        for (n in 1..10_000L) {
            val cube = n * n * n
            val permutation = cube.digits().sorted().toString()
            val list = cubicPermutations.getOrPut(permutation) { ArrayList() }

            list.add(cube)

            if (list.size == 5) {
                return list.min()
            }
        }

        return -1
    }

}
