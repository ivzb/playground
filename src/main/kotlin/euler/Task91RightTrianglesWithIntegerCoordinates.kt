package euler

import EulerTask
import utils.Point

object Task91RightTrianglesWithIntegerCoordinates : EulerTask("Right triangles with integer coordinates") {

    override fun solution(): Int {
        var counter = 0
        val o = Point(0, 0)

        for (x1 in 0 .. 50) {
            for (y1 in 0 .. 50) {
                val p = Point(x1, y1)

                for (x2 in 0 .. 50) {
                    for (y2 in 0 .. 50) {
                        val q = Point(x2, y2)
                        val isNotDuplicate = q.y - q.x < p.y - p.x

                        if (isNotDuplicate && isRightTriangle(o, p, q)) {
                            counter++
                        }
                    }
                }
            }
        }

        return counter
    }

    private fun isRightTriangle(o: Point, p: Point, q: Point): Boolean {
        // Calculate the sides
        val a = Math.pow((p.x - o.x).toDouble(), 2.0).toInt() + Math.pow((p.y - o.y).toDouble(), 2.0).toInt()
        val b = Math.pow((q.x - p.x).toDouble(), 2.0).toInt() + Math.pow((q.y - p.y).toDouble(), 2.0).toInt()
        val c = Math.pow((q.x - o.x).toDouble(), 2.0).toInt() + Math.pow((q.y - o.y).toDouble(), 2.0).toInt()

        // Check Pythagoras Formula
        return (a > 0 && b > 0 && c > 0 && (a == b + c || b == a + c || c == a + b))
    }


}
