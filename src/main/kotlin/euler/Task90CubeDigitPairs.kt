package euler

import EulerTask
import utils.Bitwise.hasBit
import utils.Bitwise.setBit
import utils.Combinatorics.combinations

object Task90CubeDigitPairs : EulerTask("Cube digit pairs") {

    override fun solution(): Int {
        val cubes = (1..9)
            .map { (it * it).toString().padStart(2, '0') }
            .map {
                listOf(
                    it.replace('6', '9'),
                    it.replace('9', '6'),
                )
                .distinct()
                .map { it.map { it.digitToInt() } }
            }

        val arrangements = combinations((0..9).toList(), 6).map {
            it.fold(0) { arrangement, n ->
                when {
                    it.contains(n) -> setBit(n, arrangement)
                    else -> arrangement
                }
            }
        }

        val seen = HashSet<Pair<Int, Int>>()
        var solutions = 0

        for (arrangement1 in arrangements) {
            for (arrangement2 in arrangements) {
                val canDisplayAllSquareNumbers = cubes.all { combination ->
                    combination.any { (i1, i2) ->
                        (hasBit(i1, arrangement1) && hasBit(i2, arrangement2)) ||
                        (hasBit(i1, arrangement2) && hasBit(i2, arrangement1))
                    }
                }

                if (!canDisplayAllSquareNumbers) {
                    continue
                }

                if (seen.contains(arrangement1 to arrangement2) || seen.contains(arrangement2 to arrangement1)) {
                    continue
                }

                seen.add(arrangement1 to arrangement2)
                solutions++
            }
        }

        return solutions
    }

}
