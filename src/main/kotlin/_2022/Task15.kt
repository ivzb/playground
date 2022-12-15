package _2022

import Task
import readInput
import utils.Bounds
import utils.Bounds.Companion.toBounds
import utils.Parse.ints
import utils.Point
import utils.Visualization
import java.lang.Math.abs
import java.math.BigInteger

object Task15 : Task {

    private const val SENSOR = 'S'
    private const val BEACON = 'B'
    private const val NO_BEACON = '#'

    // todo: CHANGE
    val Y = 2000000
//    val Y = 10

    override fun partA() = parseInput()
        .let {
            val tunnels = HashMap<Point, Char>()
            val closest = HashMap<Point, Point>()
            val sensors = ArrayList<Point>()
            val distances = HashMap<Pair<Point, Point>, Int>()

            it.map { (sensorX, sensorY, beaconX, beaconY) ->
                val sensor = Point(sensorX, sensorY)
                val beacon = Point(beaconX, beaconY)

                tunnels[sensor] = SENSOR
                tunnels[beacon] = BEACON

                sensors.add(sensor)
                closest[sensor] = beacon

                distances[sensor to beacon] = utils.Matrix.manhattanDistance(sensor, beacon)
            }

            val distancesBounds = ArrayList<Point>()

            closest.forEach { (sensor, beacon) ->
                val distance = cleanDistance(sensor, beacon, distances[sensor to beacon]!!)
                distance.forEach { (from, to) ->
                    distancesBounds.add(from)
                    distancesBounds.add(to)
                }
//                distance.forEach { (from, to) ->
//                    (from.x..to.x).forEach { x ->
//                        (from.y..to.y).forEach { y ->
//                            val p = Point(x, y)
//                            tunnels.putIfAbsent(p, NO_BEACON)
//                        }
//                    }
////                    tunnels.putIfAbsent(it, NO_BEACON)
//                }
            }
//            val tests = Point(8,7)
//            val testb = Point(2,10)
//
//            cleanDistance(tests, testb).forEach { (from, to) ->
//                    (from.x..to.x).forEach {x ->
//                        (from.y..to.y).forEach { y ->
//                            val p = Point(x, y)
//                            tunnels.putIfAbsent(p, NO_BEACON)
//                        }
//                    }
////                    tunnels.putIfAbsent(it, NO_BEACON)
//                }

            val points = tunnels.keys

//            val pr = Visualization.points(points) {
//                (tunnels[it] ?: ".").toString()
//            }
//            println(pr)
            val set = HashSet<Point>()
//            var i = 0
//            val bounds = points.toBounds()
            val bounds = distancesBounds.toBounds()
            // todo: change
            (bounds.min.x..bounds.max.x).forEach { x ->
                val position = Point(x, Y)

//                println(position)
                distances.forEach { (p, distance) ->
                    val (sensor, beacon) = p

                    if (isWithinManhattanBounds(
                            sensor.x, sensor.y,
                            beacon.x, beacon.y,
                            position.x, position.y
                        )
                    ) {
                        if (!tunnels.containsKey(position)) {
                            set.add(position)
                        }
                    }
                }
            }

            set.size
        }

    fun isWithinManhattanBounds(x1: Int, y1: Int, x2: Int, y2: Int, x3: Int, y3: Int): Boolean {
        return abs(x1 - x3) + abs(y1 - y3) <= abs(x1 - x2) + abs(y1 - y2)
    }

    fun moveIndex(sensor: Point, possible: Point, distance: Int): Int {
        val d2 = utils.Matrix.manhattanDistance(sensor, possible)
        val actual = abs(distance - d2)
        return actual
    }

    fun cleanDistance(sensor: Point, possible: Point, distance: Int): List<Pair<Point, Point>> {
//        val distance = utils.Matrix.manhattanDistance(sensor, beacon)

//        var str = ""
        val points = ArrayList<Pair<Point, Point>>()

        fun str1(p: Point): Char {
            return if (sensor == p) {
                'S'
            } else if (possible == p) {
                'P'
            } else {
                '#'
            }
        }

        for (i in 0 until distance) {
//            str += "\n"
//            for (j in 0 until distance - i) {
////                str += '.'
//            }
//            for (j in 0 until 2 * i + 1) {
//                val up = Point(sensor.x - distance - (distance - i) + j + 1, sensor.y - (distance - i))
////                str += str1(up)
//            }

            val x = sensor.x - i
            val y = sensor.y - (distance - i)
            val upFrom = Point(x, y)

            val j = 2 * i// + 1
            val upTo = Point(x + j, y)

            points.add(upFrom to upTo)
//            str += " from ($upFrom) to ($upTo)"
        }

//        for (i in 0 until 2*distance+1) {
//            val mid = Point(sensor.x-distance + i, sensor.y)
//            points.add(mid)
//        }
//        str += "\n"
//        for (i in 0 until 2 * distance + 1) {
////            str += '#'
////            val p = Point(x = sensor.x - 1, y = sensor.y)
//            val mid = Point(sensor.x - distance + i, sensor.y)
////            str += str1(mid)
//        }

        val midX = sensor.x - distance
        val midY = sensor.y

        val midFrom = Point(midX, midY)

        val xDelta = 2 * distance//+1
        val midTo = Point(midX + xDelta, midY)
//        str += " from ($midFrom) to ($midTo)"

        points.add(midFrom to midTo)

        for (i in 0 until distance) {
//            str += '\n'
//            for (j in 0 until i + 1) {
////                str += '.'
//            }
//            for (j in 0 until 2 * (distance - i) - 1) {
//                val down = Point(sensor.x - distance + i + 1 + j, sensor.y + i + 1)
////                str += str1(down)
//            }

            val downX = sensor.x - distance + i + 1
            val downY = sensor.y + i + 1

            val downFrom = Point(downX, downY)

            val xDelta = 2 * (distance - i) - 2
            val downTo = Point(downX + xDelta, downY)

            points.add(downFrom to downTo)
//            str += " from ($downFrom) to ($downTo)"
        }

        val str = ""
//        return points to str
        return points
    }

    override fun partB() = parseInput()
        .let {
            val tunnels = HashMap<Point, Char>()
            val closest = HashMap<Point, Point>()
            val sensors = ArrayList<Point>()
            val distances = HashMap<Pair<Point, Point>, Int>()

            it.map { (sensorX, sensorY, beaconX, beaconY) ->
                val sensor = Point(sensorX, sensorY)
                val beacon = Point(beaconX, beaconY)

                tunnels[sensor] = SENSOR
                tunnels[beacon] = BEACON

                sensors.add(sensor)
                closest[sensor] = beacon

                distances[sensor to beacon] = utils.Matrix.manhattanDistance(sensor, beacon)
            }

            val distancesBounds = ArrayList<Point>()

            closest.forEach { (sensor, beacon) ->
                val distance = cleanDistance(sensor, beacon, distances[sensor to beacon]!!)
                distance.forEach { (from, to) ->
                    distancesBounds.add(from)
                    distancesBounds.add(to)
                }
            }
            val bounds = distancesBounds.toBounds()

            val max = Y * 2

            val distressBeaconMinX = 0
//            val distressBeaconMaxX = max
            val distressBeaconMaxX = 4_000_000//max

            val distressBeaconMinY = 0
//            val distressBeaconMaxY = max
            val distressBeaconMaxY = 4_000_000
//            val distressBeaconMinX = bounds.min.x
//            val distressBeaconMaxX = bounds.max.x
//
//            val distressBeaconMinY = bounds.min.y
//            val distressBeaconMaxY = bounds.max.y

            println("bound x - from ${bounds.min.x}, to ${bounds.max.x}")
            println("bound y - from ${bounds.min.y}, to ${bounds.max.y}")
            println("distances = ${distances.size}")

//            val rr = prrr(distancesBounds, distances, tunnels)

            val pos = HashSet<Point>()

//            var x = 0
            var x = distressBeaconMinX
            wh1@ while (x < distressBeaconMaxX) {
                var y = distressBeaconMinY
                wh2@ while (y < distressBeaconMaxY) {
                    val possible = Point(x, y)

                    var isDetectedBySensor = false

                    for ((distancePositions, sensorToBeaconDistance) in distances) {
                        val (sensor, beacon) = distancePositions

                        val isDetectedByCurrentSensor = isWithinManhattanBounds(
                            sensor.x, sensor.y,
                            beacon.x, beacon.y,
                            possible.x, possible.y
                        )

                        if (isDetectedByCurrentSensor) {
                            val moveTo = moveIndex(sensor, possible, sensorToBeaconDistance)
                            y += (moveTo - 1).coerceAtLeast(0)
//                            val a =5

                            isDetectedBySensor = true
                            break
                        }
                    }

                    if (!isDetectedBySensor) {
                        println("possible = $possible")
                        pos.add(possible)
                    }

                    y++
                }

                x++
            }

            val p2 = pos.first()
            val tuningFrequency = (p2.x * 4_000_000L) + p2.y
            tuningFrequency
        }

    fun prrr(
        distancesBounds: List<Point>,
        distances: Map<Pair<Point, Point>, Int>,
        tunnels: Map<Point, Char>
    ): Map<Int, String> {
        val result = LinkedHashMap<Int, String>()
        val set = HashSet<Point>()
//            var i = 0
//            val bounds = points.toBounds()
        val bounds = distancesBounds.toBounds()
        // todo: change
        (bounds.min.x..bounds.max.x).forEach { x ->
            (bounds.min.y..bounds.max.y).forEach { y ->
                val position = Point(x, y)

//                println(position)
                distances.forEach { (p, distance) ->
                    val (sensor, beacon) = p

                    if (isWithinManhattanBounds(
                            sensor.x, sensor.y,
                            beacon.x, beacon.y,
                            position.x, position.y
                        )
                    ) {
                        if (!tunnels.containsKey(position)) {
                            set.add(position)
                        }
                    }
                }
            }
        }

        (bounds.min.x..bounds.max.x).forEach { x ->
            result[x] = ""
            (bounds.min.y..bounds.max.y).forEach { y ->
                val position = Point(x, y)

//                print("$position ")

                val r = (if (set.contains(position)) {
                    '#'
                } else {
                    '.'
                })

                result[x] = result[x] + r

//                    print(r)
            }
//                print(", x = $x")
//                println()
        }
//        println()
        return result
    }

    private fun parseInput() = readInput("_2022/15")
        .split("\n")
        .map { line -> line.split(',', '=', ':').ints() }
}
