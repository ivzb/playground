package utils

data class Point(val x: Int = 0, val y: Int = 0) {

    operator fun plus(other: Point): Point = Point(this.x + other.x, this.y + other.y)
    operator fun minus(other: Point): Point = Point(this.x - other.x, this.y - other.y)
    operator fun minus(other: Pair<Int, Int>): Point = this - Point(other.first, other.second)

    fun coerceIn(min: Int, max: Int) = Point(x.coerceIn(min, max), y.coerceIn(min, max))

    fun invert(): Point = Point(this.y, this.x)

    fun toDirection(): Direction = Direction.from(this)

    override fun toString(): String = "($x,$y)"

    companion object {

        fun Pair<Int, Int>.toPoint(): Point = Point(first, second)
    }

}