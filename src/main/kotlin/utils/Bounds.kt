package utils

import kotlin.math.max
import kotlin.math.min

data class Bounds(val min: Point, val max: Point) {

    companion object {

        fun Collection<Point>.toBounds(): Bounds {
            var minX = Int.MAX_VALUE
            var maxX = Int.MIN_VALUE
            var minY = Int.MAX_VALUE
            var maxY = Int.MIN_VALUE

            forEach { (x, y) ->
                minX = min(minX, x)
                maxX = max(maxX, x)
                minY = min(minY, y)
                maxY = max(maxY, y)
            }

            return Bounds(
                min = Point(minX, minY),
                max = Point(maxX, maxY)
            )
        }
    }
}