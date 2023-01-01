package game_of_life

import readInput
import utils.Bounds.Companion.bounds
import utils.Point
import java.lang.StringBuilder

object GameOfLife {

    private const val ALIVE = 'O'
    private const val DEAD = '.'

    fun play() {
        var seed = parse("60p312")

        while (seed.isNotEmpty()) {
            println(print(seed))
            seed = nextGeneration(seed)
        }
    }

    private fun nextGeneration(cells: Set<Point>): Set<Point> {
        val alive = HashSet<Point>()

        for (cell in cells) {
            val adjacent = cell.adjacent()
            val aliveNeighbours = adjacent.intersect(cells)
            val deadNeighbours = adjacent.subtract(cells)

            if (aliveNeighbours.size == 2 || aliveNeighbours.size == 3) {
                alive.add(cell)
            }

            deadNeighbours.forEach { deadCell ->
                val deadCellAdjacent = deadCell.adjacent()
                val deadCellAliveNeighbours = deadCellAdjacent.intersect(cells)

                if (deadCellAliveNeighbours.size == 3) {
                    alive.add(deadCell)
                }
            }
        }

        return alive
    }

    private fun print(cells: Set<Point>): String {
        val bounds = cells.bounds()
        val sb = StringBuilder()

        for (y in bounds.min.y..bounds.max.y) {
            for (x in bounds.min.x..bounds.max.x) {
                sb.append(
                    when {
                        cells.contains(Point(x, y)) -> ALIVE
                        else -> DEAD
                    }
                )
            }

            sb.appendLine()
        }

        sb.appendLine()

        return sb.toString()
    }

    private fun parse(name: String): Set<Point> =
        readInput("game_of_life/$name.cells")
            .split('\n')
            .asSequence()
            .filter { it[0] != '!' }
            .mapIndexed { y, it ->
                it.toCharArray().mapIndexed { x, char ->
                    if (char == ALIVE) {
                        Point(x, y)
                    } else {
                        null
                    }
                }
            }
            .flatten()
            .filterNotNull()
            .toSet()
}