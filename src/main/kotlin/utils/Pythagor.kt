package utils

object Pythagor {

    val pythagoreanTriplets by lazy { arrayOf(::t1, ::t2, ::t3) }

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