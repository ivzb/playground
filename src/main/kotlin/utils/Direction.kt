package utils

enum class Direction(val delta: Point) {
    UP(Point(0, 1)),
    RIGHT(Point(1, 0)),
    DOWN(Point(0, -1)),
    LEFT(Point(-1, 0));

    companion object {

        fun from(delta: Point): Direction =
            values().find { it.delta == delta } ?: error("unknown delta $delta")
    }
}