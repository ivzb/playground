package advent_of_code._2022

import Task
import advent_of_code._2022.Task23.DirectionAdjacent.*
import readInput
import utils.Bounds
import utils.Bounds.Companion.bounds
import utils.Direction
import utils.Direction.*
import utils.Point

typealias Grove = HashMap<Point, Char>

object Task23 : Task {

    private const val EMPTY = '.'
    private const val ELF = '#'

    private enum class DirectionAdjacent(val direction: Direction, val directions: List<Direction>) {
        N(NORTH, listOf(NORTH, NORTH_EAST, NORTH_WEST)),
        S(SOUTH, listOf(SOUTH, SOUTH_EAST, SOUTH_WEST)),
        W(WEST, listOf(WEST, NORTH_WEST, SOUTH_WEST)),
        E(EAST, listOf(EAST, NORTH_EAST, SOUTH_EAST));

        val delta = this.direction.delta
        val deltas = this.directions.map { it.delta }
    }

    private val ALL_DIRECTIONS = DirectionAdjacent.values()
        .map { it.directions.map { it.delta } }
        .flatten()
        .toSet()

    override fun partA() = solve(checkRound = 10)

    override fun partB() = solve()

    private fun solve(checkRound: Int? = null) = parseInput()
        .let { input ->
            val grove = buildGrove(input)
            val directionQ = ArrayDeque(listOf(N, S, W, E))
            var round = 0

            while (true) {
                round++

                val elfPositions = grove.elfPositions()
                val proposedMoves = HashMap<Point, ArrayList<Point>>()

                for (elfPosition in elfPositions) {
                    val adjacent = elfPosition.adjacentTo(ALL_DIRECTIONS)
                    val isElfAlone = adjacent.intersect(elfPositions).isEmpty()

                    if (isElfAlone) {
                        continue
                    }

                    for (direction in directionQ) {
                        val directionAdjacent = elfPosition.adjacentTo(direction.deltas)
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