package _2022

import Task
import _2022.Task23.Direction.*
import readInput
import utils.Bounds
import utils.Bounds.Companion.bounds
import utils.Point

typealias Grove = HashMap<Point, Char>

object Task23 : Task {

    private const val EMPTY = '.'
    private const val ELF = '#'

    private val DELTA_N = Point(0, -1)
    private val DELTA_NE = Point(1, -1)
    private val DELTA_E = Point(1, 0)
    private val DELTA_S = Point(0, 1)
    private val DELTA_SE = Point(1, 1)
    private val DELTA_SW = Point(-1, 1)
    private val DELTA_W = Point(-1, 0)
    private val DELTA_NW = Point(-1, -1)

    private enum class Direction(val delta: Point, val deltas: List<Point>) {
        NORTH(DELTA_N, listOf(DELTA_N, DELTA_NE, DELTA_NW)),
        SOUTH(DELTA_S, listOf(DELTA_S, DELTA_SE, DELTA_SW)),
        WEST(DELTA_W, listOf(DELTA_W, DELTA_NW, DELTA_SW)),
        EAST(DELTA_E, listOf(DELTA_E, DELTA_NE, DELTA_SE)),
    }

    private val ALL_DIRECTIONS = Direction.values()
        .map { it.deltas }
        .flatten()
        .toSet()

    private fun adjacent(position: Point, deltas: Collection<Point>): List<Point> =
        deltas.map { it + position }

    override fun partA() = solve(checkRound = 10)

    override fun partB() = solve(checkRound = null)

    private fun solve(checkRound: Int?) = parseInput()
        .let { input ->
            val grove = buildGrove(input)
            val directionQ = ArrayDeque(listOf(NORTH, SOUTH, WEST, EAST))
            var round = 0

            while (true) {
                round++

                val elfPositions = grove.elfPositions()
                val proposedMoves = HashMap<Point, ArrayList<Point>>()

                for (elfPosition in elfPositions) {
                    val adjacent = adjacent(elfPosition, ALL_DIRECTIONS)
                    val isElfAlone = adjacent.intersect(elfPositions).isEmpty()

                    if (isElfAlone) {
                        continue
                    }

                    for (direction in directionQ) {
                        val directionAdjacent = adjacent(elfPosition, direction.deltas)
                        val canMove = directionAdjacent.intersect(elfPositions).isEmpty()

                        if (canMove) {
                            val proposedPosition = elfPosition + direction.delta

                            if (!proposedMoves.containsKey(proposedPosition)) {
                                proposedMoves[proposedPosition] = ArrayList()
                            }

                            proposedMoves[proposedPosition]?.add(elfPosition)
                            break
                        }
                    }
                }

                for ((proposedPosition, elfPositions) in proposedMoves) {
                    if (elfPositions.size == 1) {
                        grove[proposedPosition] = ELF
                        grove[elfPositions.first()] = EMPTY
                    }
                }

                directionQ.addLast(directionQ.removeFirst())

                if (proposedMoves.isEmpty()) {
                    return@let round
                }

                if (round == checkRound) {
                    val bounds = grove.keys.bounds()
                    return@let grove.countEmpty(bounds)
                }
            }
        }

    private fun Grove.elfPositions() = this.filterValues { item -> item == ELF }.keys

    private fun Grove.isElf(position: Point) = this[position] == ELF

    private fun Grove.isEmpty(position: Point) = !isElf(position)

    private fun Grove.countEmpty(bounds: Bounds): Int =
        (bounds.min.y..bounds.max.y).map { y ->
            (bounds.min.x..bounds.max.x).map { x ->
                Point(x, y)
            }
        }
        .flatten()
        .count { position -> isEmpty(position) }

    private fun buildGrove(input: List<CharArray>): Grove =
        input.foldIndexed(Grove()) { x, grove, row ->
            row.forEachIndexed { y, item ->
                val position = Point(y, x)
                grove[position] = item
            }

            grove
        }

    private fun parseInput() = readInput("_2022/23")
        .split("\n")
        .map { it.toCharArray() }

}