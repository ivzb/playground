package advent_of_code._2022

import Task
import advent_of_code._2022.Task22.Direction.*
import advent_of_code._2022.Task22.SIDE.*
import readInput
import utils.Bounds.Companion.bounds
import utils.Point
import java.lang.StringBuilder

typealias Board = HashMap<Point, Task22.Structure>

object Task22 : Task {

    private const val TURN_LEFT = "L"
    private const val TURN_RIGHT = "R"

    private val DIRECTION_MAPPING = mapOf(
        (UP to TURN_RIGHT) to RIGHT,
        (RIGHT to TURN_RIGHT) to DOWN,
        (DOWN to TURN_RIGHT) to LEFT,
        (LEFT to TURN_RIGHT) to UP,

        (UP to TURN_LEFT) to LEFT,
        (RIGHT to TURN_LEFT) to UP,
        (DOWN to TURN_LEFT) to RIGHT,
        (LEFT to TURN_LEFT) to DOWN,
    )

    private val DIRECTION_POSITIONS = mapOf(
        UP to '^',
        RIGHT to '>',
        DOWN to 'v',
        LEFT to '<',
    )

    private val DIRECTION_VALUES = mapOf(
        UP to 3,
        RIGHT to 0,
        DOWN to 1,
        LEFT to 2,
    )

    override fun partA() = solve(::opposite2D)

    override fun partB() = solve(::opposite3D)

    private enum class Direction(val delta: Point) {
        UP(Point(0, -1)),
        RIGHT(Point(1, 0)),
        DOWN(Point(0, 1)),
        LEFT(Point(-1, 0));

        fun position(): Char = DIRECTION_POSITIONS[this] ?: error("undefined direction position")
    }

    enum class Structure(val item: Char) {
        EmptySpace('.'),
        Wall('#');

        companion object {

            fun find(item: Char): Structure? = values().find { it.item == item }
        }
    }

    private fun opposite2D(board: Board, facingDirection: Direction, lastPosition: Point): Pair<Direction, Point> {
        return facingDirection to when (facingDirection) {
            UP -> board.row(lastPosition.x).maxBy { it.y }
            RIGHT -> board.col(lastPosition.y).minBy { it.x }
            DOWN -> board.row(lastPosition.x).minBy { it.y }
            LEFT -> board.col(lastPosition.y).maxBy { it.x }
        }
    }

    enum class SIDE { A, B, C, D, E, F }

    private fun sideOf(pos: Point): SIDE {
        if (pos.x in 50..99 && pos.y in 0..49) return A
        if (pos.x in 100..149 && pos.y in 0..49) return B
        if (pos.x in 50..99 && pos.y in 50..99) return C
        if (pos.x in 50..99 && pos.y in 100..149) return D
        if (pos.x in 0..49 && pos.y in 100..149) return E
        if (pos.x in 0..49 && pos.y in 150..199) return F
        throw java.lang.RuntimeException("Side does not exist for $pos")
    }

    private fun opposite3D(board: Board, facingDirection: Direction, currentPosition: Point): Pair<Direction, Point> {
        val currentSide = sideOf(currentPosition)

        return when {
            currentSide == A && facingDirection == UP -> RIGHT to Point(0, 3 * 50 + currentPosition.x - 50) // nextSide = F
            currentSide == A && facingDirection == LEFT -> RIGHT to Point(0, 2 * 50 + (50 - currentPosition.y - 1)) // nextSide = E
            currentSide == B && facingDirection == UP -> UP to Point(currentPosition.x- 100, 199) // nextSide = F
            currentSide == B && facingDirection == RIGHT -> LEFT to Point(99, (50 - currentPosition.y) + 2 * 50 - 1) // nextSide = D
            currentSide == B && facingDirection == DOWN -> LEFT to Point(99, 50 + (currentPosition.x - 2 * 50)) // nextSide = C
            currentSide == C && facingDirection == RIGHT -> UP to Point((currentPosition.y - 50) + 2 * 50, 49) // nextSide = B
            currentSide == C && facingDirection == LEFT -> DOWN to Point(currentPosition.y - 50, 100) // nextSide = E
            currentSide == E && facingDirection == LEFT -> RIGHT to Point(50, 50 - (currentPosition.y - 2 * 50) - 1) // nextSide = A
            currentSide == E && facingDirection == UP -> RIGHT to Point(50, 50 + currentPosition.x) // nextSide = C
            currentSide == D && facingDirection == DOWN -> LEFT to Point(49, 3 * 50 + (currentPosition.x - 50)) // nextSide = F
            currentSide == D && facingDirection == RIGHT -> LEFT to Point(149, 50 - (currentPosition.y - 50 * 2) - 1) // nextSide = B
            currentSide == F && facingDirection == RIGHT -> UP to Point((currentPosition.y - 3 * 50) + 50, 149) // nextSide = D
            currentSide == F && facingDirection == LEFT -> DOWN to Point(50 + (currentPosition.y - 3 * 50), 0) // nextSide = A
            currentSide == F && facingDirection == DOWN -> DOWN to Point(currentPosition.x + 100, 0) // nextSide = B
            else -> error("undefined side or direction")
        }
    }

    private fun solve(oppositeSide: (Board, Direction, Point) -> Pair<Direction, Point>): Int = parseInput()
        .let { (map, path) ->
            val board = buildBoard(map)
            val visitedPath = HashMap<Point, Direction>()

            var lastPosition = board.findStartingPoint()
            var facingDirection = RIGHT

            visitedPath[lastPosition] = facingDirection

//            Debug.println(visualize(board, visitedPath))
//            println(visualize3D(board, CUBE_SIZE))

            path.forEach { command ->
                if (command is Int) {
                    (0 until command).forEach steps@{
                        var newPosition = lastPosition + facingDirection.delta
                        val boardElement = board[newPosition]

                        if (boardElement != null) {
                            // check if new position is free to go
                            if (boardElement == Structure.Wall) {
                                return@steps
                            }
                        } else {
                            // move to opposite side if it's free
                            val (oppositeSideDirection, oppositeSidePosition) = oppositeSide(board, facingDirection, lastPosition)

                            val oppositeBoardElement = board[oppositeSidePosition] ?: error("invalid opposite board position")

                            // check if new direction position is free to go
                            if (oppositeBoardElement == Structure.Wall) {
                                return@steps
                            }

                            newPosition = oppositeSidePosition
                            facingDirection = oppositeSideDirection
                        }

                        lastPosition = newPosition
                        visitedPath[lastPosition] = facingDirection
                    }
                }

                if (command is String) {
                    val mapping = (facingDirection to command)
                    facingDirection = DIRECTION_MAPPING[mapping] ?: error("unknown mapping $mapping")
                    visitedPath[lastPosition] = facingDirection
                }

//                Debug.println(visualize(board, visitedPath))
            }

//            Debug.println(visualize(board, visitedPath))

            val row = lastPosition.y + 1
            val col = lastPosition.x + 1
            val direction = DIRECTION_VALUES[facingDirection] ?: error("undefined direction $facingDirection")

            val password = (1000 * row) + (4 * col) + direction

            password
        }

    private fun Board.row(row: Int): Collection<Point> {
        return this.filter { (p, _) -> p.x == row }.keys
    }

    private fun Board.col(col: Int): Collection<Point> {
        return this.filter { (p, _) -> p.y == col }.keys
    }

    private fun Board.findStartingPoint(): Point {
        return this
            .filter { (position, structure) -> structure == Structure.EmptySpace && position.y == 0 }
            .map { (position) -> position }
            .minBy { it.x }
    }

    private fun visualize(board: Board, visitedPath: Map<Point, Direction>): String {
        val bounds = board.keys.bounds()
        val sb = StringBuilder()

        (bounds.min.x..bounds.max.x).forEach { x ->
            (bounds.min.y..bounds.max.y).forEach { y ->
                val point = Point(x, y)

                val visited = visitedPath[point]?.position()
                val boardElement = board[point]?.item ?: ' '

                sb.append(visited ?: boardElement)
            }

            sb.appendLine()
        }

        return sb.toString()
    }

    private fun buildBoard(map: List<CharArray>): Board {
        val board = Board()

        map.forEachIndexed { row, _ ->
            map[row].forEachIndexed { col, _ ->
                val item = map[row][col]
                val structure = Structure.find(item)

                if (structure != null) {
                    val p = Point(col, row)
                    board[p] = structure
                }
            }
        }

        return board
    }

    private fun parseInput() = readInput("_2022/22")
        .split("\n\n")
        .let { (map, instructions) ->
            val board = map
                .split('\n')
                .map { it.toCharArray() }

            val path = instructions
                .replace("L", " L ")
                .replace("R", " R ")
                .split(' ')
                .map { it.toIntOrNull() ?: it }

            board to path
        }
}