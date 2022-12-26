package advent_of_code._2022

import Task
import readInput
import utils.Bounds
import utils.Bounds.Companion.rebound
import utils.Matrix
import utils.Parse.ints
import utils.Point
import java.lang.Math.abs

object Task15 : Task {

    private const val SENSOR = 'S'
    private const val BEACON = 'B'

    data class SensorData(
        val distance: Int,
        val fullBounds: List<Bounds>,
        val bounds: Bounds,
    )

    val Y = 2000000

    override fun partA() = parseInput().let {
        val map = HashMap<Point, Char>()
        val closest = HashMap<Point, Point>()
        val distances = HashMap<Pair<Point, Point>, Int>()

        it.map { (sensorX, sensorY, beaconX, beaconY) ->
            val sensor = Point(sensorX, sensorY)
            val beacon = Point(beaconX, beaconY)

            map[sensor] = SENSOR
            map[beacon] = BEACON
            closest[sensor] = beacon
            distances[sensor to beacon] = Matrix.manhattanDistance(sensor, beacon)
        }

        val distanceBounds = closest
            .map { (sensor, beacon) ->
                distanceBounds(
                    sensor,
                    distances[sensor to beacon] ?: error("distance of $sensor -> $beacon doesn't exist")
                )
                .rebound()
            }
            .rebound()

        val noBeaconAt = HashSet<Point>()

        (distanceBounds.min.x..distanceBounds.max.x).forEach { x ->
            val position = Point(x, Y)

            distances.forEach { (positions) ->
                val (sensor, beacon) = positions

                if (Matrix.isWithinManhattanDistance(sensor, beacon, position)) {
                    noBeaconAt.add(position)
                }
            }
        }

        noBeaconAt.count { !map.containsKey(it) }
    }

    override fun partB() = parseInput().let {
        val distances = HashMap<Pair<Point, Point>, SensorData>()

        it.map { (sensorX, sensorY, beaconX, beaconY) ->
            val sensor = Point(sensorX, sensorY)
            val beacon = Point(beaconX, beaconY)

            val distance =  Matrix.manhattanDistance(sensor, beacon)
            val fullBounds = distanceBounds(sensor, distance)
            val bounds = fullBounds.rebound()
            val data = SensorData(distance, fullBounds, bounds)
            distances[sensor to beacon] = data
        }

        val max = Y * 2
        var distressSignalPosition: Point? = null

        (0..max).forEach { x ->
            var y = 0
            val row = Point(x, y)
            val nearByDistances = distances.filter { (_, data) ->
                row.isWithinX(data.bounds)
            }

            while (y < max) {
                val possible = Point(x, y)
                var isDetectedByAnySensor = false

                for ((positions, distance) in nearByDistances) {
                    val (sensor, beacon) = positions
                    val isDetectedByCurrentSensor = Matrix.isWithinManhattanDistance(sensor, beacon, possible)

                    if (isDetectedByCurrentSensor) {
                        val sensorToPossibleDistance = Matrix.manhattanDistance(sensor, possible)
                        val (sensorToBeaconDistance) = distance
                        val sensorDistance = abs(sensorToBeaconDistance - sensorToPossibleDistance)
                        y += sensorDistance
                        isDetectedByAnySensor = true
                        break
                    }
                }

                if (!isDetectedByAnySensor) {
                    distressSignalPosition = possible
                    return@forEach
                }

                y++
            }
        }

        val position = distressSignalPosition ?: error("distress signal not found")
        val tuningFrequency = (position.x * 4_000_000L) + position.y
        tuningFrequency
    }

    private fun parseInput() = readInput("_2022/15")
        .split("\n")
        .map { line -> line.split(',', '=', ':').ints() }

    private fun distanceBounds(sensor: Point, distance: Int): List<Bounds> = ArrayList<Bounds>().apply {
        for (i in 0 until distance) {
            val up = Point(sensor.x - i, sensor.y - (distance - i))
            add(Bounds(min = Point(up.x, up.y), max = Point(up.x + 2 * i, up.y)))
        }

        val mid = Point(sensor.x - distance, sensor.y)
        add(Bounds(min = Point(mid.x, mid.y), max = Point(mid.x + 2 * distance, mid.y)))

        for (i in 0 until distance) {
            val down = Point(sensor.x - distance + i + 1, sensor.y + i + 1)
            add(Bounds(min = Point(down.x, down.y), max = Point(down.x + 2 * (distance - i) - 2, down.y)))
        }
    }
}
