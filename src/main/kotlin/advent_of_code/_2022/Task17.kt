package advent_of_code._2022

import Task
import readInput
import utils.Bounds
import utils.Bounds.Companion.bounds
import utils.Bounds.Companion.rebound
import utils.Point
import java.lang.Math.abs
import java.lang.StringBuilder
import java.util.LinkedList

typealias Rock = List<Point>
typealias Chamber = List<Point>

object Task17 : Task {

    private const val LEFT_JET = '<'
    private const val RIGHT_JET = '>'

    private const val FALLING_ROCK = '@'
    private const val FALLEN_ROCK = '#'
    private const val SPACE = '.'

    private const val WALL_HORIZONTAL = '-'
    private const val WALL_VERTICAL = '|'
    private const val WALL_CORNER = '+'

    private const val SPAWN_LEFT_OFFSET = 2
    private const val SPAWN_BOTTOM_OFFSET = 4

    private val ROCKS = listOf(
        // ####
        listOf(
            Point(0, 0),
            Point(1, 0),
            Point(2, 0),
            Point(3, 0),
        ),

//    .#.
//    ###
//    .#.
        listOf(
            Point(1, 0),

            Point(0, 1),
            Point(1, 1),
            Point(2, 1),

            Point(1, 2),
        ),

//    ..#
//    ..#
//    ###
        listOf(
            Point(2, 0),

            Point(2, 1),

            Point(0, 2),
            Point(1, 2),
            Point(2, 2),
        ),

//    #
//    #
//    #
//    #
        listOf(
            Point(0, 0),
            Point(0, 1),
            Point(0, 2),
            Point(0, 3),
        ),

//    ##
//    ##
        listOf(
            Point(0, 0),
            Point(1, 0),

            Point(0, 1),
            Point(1, 1),
        )
    )

    private val FLOOR = listOf(
        Point(0, 0),
        Point(1, 0),
        Point(2, 0),
        Point(3, 0),
        Point(4, 0),
        Point(5, 0),
        Point(6, 0),
    )

    private const val WALL_LEFT = -1
    private const val WALL_RIGHT = 7

    private val GRAVITY_DELTA = Point(0, 1)

    private val JET_DELTAS = mapOf(
        LEFT_JET to Point(-1, 0),
        RIGHT_JET to Point(1, 0),
    )

    override fun partA() = simulate(2023)

    override fun partB() = simulate(1_000_000_000_000)

    data class Situation(val rockIndex: Int, val jetIndex: Int)

    data class Pattern(val chamberHeight: Int, val fallenRocks: Long)

    private fun simulate(times: Long) =
        parseInput()
            .let { jetPattern ->
                var rockCounter = 0L
                var jetCounter = 0
                var chamber: List<Point> = FLOOR
                var fallingRock: Rock? = null

                val situations = HashSet<Situation>()
                val heights = ArrayList<Int>()
                var patternStart: Pattern? = null

                while (true) {
                    val chamberBounds = chamber.bounds()

                    val jetIndex = jetCounter % jetPattern.size

                    if (fallingRock == null) {
                        val rockIndex = (rockCounter % ROCKS.size).toInt()
                        fallingRock = spawnRock(rockIndex, chamberBounds)
                        rockCounter++

                        val situation = Situation(rockIndex, jetIndex)

                        if (situations.contains(situation)) {
                            val pattern = Pattern(chamberHeight = abs(chamberBounds.min.y), fallenRocks = rockCounter)

                            if (patternStart == null) {
                                // found the pattern start
                                patternStart = pattern
                                situations.clear()
                                heights.clear()
                            } else {
                                // found the pattern end
                                val patternEnd = pattern

                                val heightPerCycle = patternEnd.chamberHeight - patternStart.chamberHeight
                                val rocksPerCycle = patternEnd.fallenRocks - patternStart.fallenRocks

                                val totalRocks = times
                                val cyclesNeeded = totalRocks / rocksPerCycle

                                val remainingRocks = (times - (rocksPerCycle*cyclesNeeded)).toInt()
                                val remainingHeight = heights[remainingRocks - 5]

                                val finalHeight = (heightPerCycle * cyclesNeeded) + remainingHeight
                                return@let finalHeight
                            }
                        }

                        situations.add(situation)

                        if (patternStart != null) {
                            heights.add(abs(chamberBounds.min.y) - patternStart.chamberHeight)
                        }
                    }

                    val jet = jetPattern[jetIndex]
                    jetCounter++
                    val jetDelta = JET_DELTAS[jet] ?: error("no delta for jet $jet")
                    val fallingRockJetMovement = moveRock(fallingRock, jetDelta)

                    if (hitTheWall(fallingRockJetMovement) || hitTheFloor(chamber, fallingRockJetMovement)) {
                        // don't move
                    } else {
                        fallingRock = fallingRockJetMovement
                    }

                    val fallingRockGravityMovement = moveRock(fallingRock, GRAVITY_DELTA)

                    if (hitTheFloor(chamber, fallingRockGravityMovement)) {
                        chamber = fallingRock + chamber
                        fallingRock = null
                    } else {
                        fallingRock = fallingRockGravityMovement
                    }

                    if (rockCounter == times) {
                        break
                    }
                }

                abs(chamber.bounds().min.y)
            }

    private fun spawnRock(rockIndex: Int, chamberBounds: Bounds): Rock {
        val rock = ROCKS[rockIndex]
        val leftOffset = chamberBounds.min.x + SPAWN_LEFT_OFFSET
        val bottomOffset = chamberBounds.min.y - (rock.bounds().max.y + SPAWN_BOTTOM_OFFSET)
        val delta = Point(leftOffset, bottomOffset)
        return moveRock(rock, delta)
    }

    private fun moveRock(rock: Rock, delta: Point): Rock {
        return rock.map { it + delta }
    }

    private fun hitTheFloor(chamber: Chamber, rock: Rock): Boolean {
        return chamber.intersect(rock).any()
    }

    private fun hitTheWall(rock: Rock): Boolean {
        return rock.any { it.x <= WALL_LEFT || it.x >= WALL_RIGHT }
    }

    fun print(chamber: Chamber, fallingRock: Rock?): String {
        val bounds = listOfNotNull(chamber.bounds(), fallingRock?.bounds()).rebound()
        val sb = StringBuilder()

        (bounds.min.y..bounds.max.y).forEach { y ->
            sb.append(if (y == 0) WALL_CORNER else WALL_VERTICAL)

            (bounds.min.x..bounds.max.x).forEach { x ->
                val p = Point(x, y)

                if (fallingRock?.contains(p) == true) {
                    sb.append(FALLING_ROCK)
                } else if (chamber.contains(p)) {
                    sb.append(
                        if (FLOOR.contains(p)) {
                            WALL_HORIZONTAL
                        } else {
                            FALLEN_ROCK
                        }
                    )
                } else {
                    sb.append(SPACE)
                }
            }

            sb.append(if (y == 0) WALL_CORNER else WALL_VERTICAL)
            sb.append('\n')
        }

        return sb.toString()
    }

    private fun parseInput() = readInput("_2022/17")
        .toCharArray()
        .toList()

}