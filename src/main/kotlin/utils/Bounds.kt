package utils

import kotlin.math.max
import kotlin.math.min

data class Bounds(val min: Point, val max: Point) {

    operator fun plus(other: Bounds): Bounds = Bounds(this.min + other.min, this.max + other.max)

    fun isWithinX(other: Bounds): Boolean =
        this.min.x >= other.min.x && this.max.x <= other.max.x

    fun isWithinY(other: Bounds): Boolean =
        this.min.y >= other.min.y && this.max.y <= other.max.y

    fun isWithin(other: Bounds): Boolean =
        isWithinX(other) && isWithinY(other)

    companion object {

        fun Collection<Bounds>.rebound(): Bounds {
            var minX = Int.MAX_VALUE
            var maxX = Int.MIN_VALUE
            var minY = Int.MAX_VALUE
            var maxY = Int.MIN_VALUE

            forEach { (min, max) ->
                minX = min(minX, min.x)
                maxX = max(maxX, max.x)
                minY = min(minY, min.y)
                maxY = max(maxY, max.y)
            }

            return Bounds(
                min = Point(minX, minY),
                max = Point(maxX, maxY)
            )
        }

        fun Collection<Point>.bounds(): Bounds {
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