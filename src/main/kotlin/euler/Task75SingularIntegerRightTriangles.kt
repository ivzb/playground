package euler

import EulerTask

object Task75SingularIntegerRightTriangles : EulerTask("Singular integer right triangles") {

    data class Triangle(val a: Int, val b: Int, val c: Int) {

        val perimeter: Int by lazy { a + b + c }

        operator fun times(n: Int): Triangle = Triangle(a * n, b * n, c * n)

        override fun toString(): String = "$a, $b, $c = $perimeter"
    }

    override fun solution(): Int {
        val limit = 1_500_000
        val initial = Triangle(3, 4, 5)

        val q = ArrayDeque<Triangle>()
        q.add(initial)

        val primitives = ArrayDeque<Triangle>()
        primitives.add(initial)

        val perimeters = IntArray(limit + 1)
        perimeters[initial.perimeter] = 1

        val transformations = arrayOf(::t1, ::t2, ::t3)

        while (q.isNotEmpty()) {
            val triangle = q.removeLast()

            transformations.forEach { transformation ->
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

    private fun t1(t: Triangle): Triangle {
        val a = (-t.a) + (2 * t.b) + (2 * t.c)
        val b = (2 * -t.a) + (t.b) + (2 * t.c)
        val c = (2 * -t.a) + (2 * t.b) + (3 * t.c)
        return Triangle(a, b, c)
    }

    private fun t2(t: Triangle): Triangle {
        val a = (t.a) + (2 * t.b) + (2 * t.c)
        val b = (2 * t.a) + (t.b) + (2 * t.c)
        val c = (2 * t.a) + (2 * t.b) + (3 * t.c)
        return Triangle(a, b, c)
    }

    private fun t3(t: Triangle): Triangle {
        val a = (t.a) - (2 * t.b) + (2 * t.c)
        val b = (2 * t.a) - (t.b) + (2 * t.c)
        val c = (2 * t.a) - (2 * t.b) + (3 * t.c)
        return Triangle(a, b, c)
    }

}
