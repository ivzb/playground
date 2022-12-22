package utils

data class Cube(val x: Int, val y: Int, val z: Int) {

    constructor(coords: List<Int>) : this(coords[0], coords[1], coords[2])

    val neighbours: Set<Cube>
        get() = setOf(
            Cube(x - 1, y, z),
            Cube(x + 1, y, z),
            Cube(x, y + 1, z),
            Cube(x, y - 1, z),
            Cube(x, y, z + 1),
            Cube(x, y, z - 1),
        )

    val min = listOf(x, y, z).min()
    val max = listOf(x, y, z).max()

    enum class Side(val item: Char, val cube: Cube) {
        TOP('2', Cube(0, 1, 0)),
        BOTTOM('4', Cube(0, -1, 0)),
        FRONT('1', Cube(0, 0, 1)),
        BACK('5', Cube(0, 0, -1)),
        RIGHT('6', Cube(1, 0, 0)),
        LEFT('3', Cube(-1, 0, 0));

        override fun toString(): String {
            return item.toString()
        }

        companion object {

            private val cubeSides by lazy { values().associateBy { it.cube } }

            fun find(cube: Cube): Side {
                return cubeSides[cube] ?: error("invalid cube side")
            }
        }
    }

    val side by lazy { Side.find(this) }

    val adjacentSides: List<Side> by lazy {
        when (side) {
            Side.TOP -> listOf(Side.FRONT, Side.BACK, Side.RIGHT, Side.LEFT)
            Side.BOTTOM -> listOf(Side.FRONT, Side.BACK, Side.RIGHT, Side.LEFT)
            Side.FRONT -> listOf(Side.TOP, Side.BOTTOM, Side.RIGHT, Side.LEFT)
            Side.BACK -> listOf(Side.TOP, Side.BOTTOM, Side.RIGHT, Side.LEFT)
            Side.RIGHT -> listOf(Side.TOP, Side.BOTTOM, Side.FRONT, Side.BACK)
            Side.LEFT -> listOf(Side.TOP, Side.BOTTOM, Side.FRONT, Side.BACK)
        }
    }

    val left: Side by lazy {
        when (side) {
            Side.TOP -> Side.LEFT
            Side.BOTTOM -> Side.LEFT
            Side.FRONT -> Side.LEFT
            Side.BACK -> Side.LEFT
            Side.RIGHT -> Side.TOP
            Side.LEFT -> Side.BOTTOM
        }
    }

    val right: Side by lazy {
        when (side) {
            Side.TOP -> Side.RIGHT
            Side.BOTTOM -> Side.RIGHT
            Side.FRONT -> Side.RIGHT
            Side.BACK -> Side.RIGHT
            Side.RIGHT -> Side.BOTTOM
            Side.LEFT -> Side.TOP
        }
    }

    val top: Side by lazy {
        when (side) {
            Side.TOP -> Side.FRONT
            Side.BOTTOM -> Side.BACK
            Side.FRONT -> Side.TOP
            Side.BACK -> Side.TOP
            Side.RIGHT -> Side.FRONT
            Side.LEFT -> Side.FRONT
        }
    }

    val bottom: Side by lazy {
        when (side) {
            Side.TOP -> Side.FRONT
            Side.BOTTOM -> Side.BACK
            Side.FRONT -> Side.BOTTOM
            Side.BACK -> Side.TOP
            Side.RIGHT -> Side.FRONT
            Side.LEFT -> Side.FRONT
        }
    }
}
