package utils

object Graph {

    fun draw(matrix: List<List<Char>>, path: List<Point>): String {
        val directions = ArrayList<ArrayList<Char>>()

        (matrix.indices).forEachIndexed { row, _ ->
            directions.add(ArrayList())
            (matrix[row].indices).forEach { _ ->
                directions[row].add('.')
            }
        }

        (0 until path.size - 1).forEachIndexed { index, _ ->
            val prev = path[index]
            val current = path[index + 1]
            val delta = prev - current

            directions[prev.x][prev.y] = when (delta.invert().toDirection()) {
                Direction.UP -> '^'
                Direction.RIGHT -> '<'
                Direction.DOWN -> 'v'
                Direction.LEFT -> '>'
                else -> error("wrong direction")
            }
        }

        return directions.joinToString("\n") { it.joinToString("") }
    }
}