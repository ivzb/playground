package euler

import EulerTask
import utils.Pythagor.pythagoreanTriplets
import utils.Triangle

object Task75SingularIntegerRightTriangles : EulerTask("Singular integer right triangles") {

    override fun solution(): Int {
        val limit = 1_500_000
        val initial = Triangle(3, 4, 5)

        val q = ArrayDeque<Triangle>()
        q.add(initial)

        val primitives = ArrayDeque<Triangle>()
        primitives.add(initial)

        val perimeters = IntArray(limit + 1)
        perimeters[initial.perimeter] = 1

        while (q.isNotEmpty()) {
            val triangle = q.removeLast()

            pythagoreanTriplets.forEach { transformation ->
                transformation(triangle).let { primitive ->
                    if (primitive.perimeter <= limit) {
                        primitives.add(primitive)
                        q.add(primitive)
                        perimeters[primitive.perimeter]++
                    }
                }
            }
        }

        while (primitives.isNotEmpty()) {
            val primitive = primitives.removeFirst()
            var n = 2

            while (true) {
                val composite = primitive * n
                n++

                if (composite.perimeter > limit) {
                    break
                }

                perimeters[composite.perimeter]++
            }
        }

        return perimeters.count { it == 1 }
    }

}
