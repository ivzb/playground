package advent_of_code._2024

import Task
import readInput
import utils.Point

object Task12 : Task {

    override fun partA() = parseInput()
        .let { map ->
            solve(map) { positions ->
                val area = positions.size
                val perimeter = countPerimeter(map, positions)
                area * perimeter
            }
        }

    override fun partB() = parseInput()
        .let { map ->
            solve(map) { positions ->
                val area = positions.size
                val corners = countCorners(positions.toSet())
                area * corners
            }
        }

    private fun solve(map: Map<Point, Char>, fn: (Set<Point>) -> Int): Int {
        val regions = reverseMap(groupRegions(map))

        return regions
            .map { (_, positions) ->
                fn(positions.toSet())
            }
            .sum()
    }

    private val NEIGHBOURS = listOf(
        Point(0, -1), // up
        Point(1, 0), // right
        Point(0, 1), // bottom
        Point(-1, 0), // left
    )

    private val CORNERS = listOf(
        listOf(Point(0, -1), Point(-1, -1), Point(-1, 0)), // top left
        listOf(Point(0, -1), Point(1, -1), Point(1, 0)), // bottom left

        listOf(Point(0, 1), Point(-1, 1), Point(-1, 0)), // top right
        listOf(Point(0, 1), Point(1, 1), Point(1, 0)), // bottom right
    )

    private fun floodFill(
        map: MutableMap<Point, Char>,
        start: Point,
        target: Char,
        replacement: Int,
        resultMap: MutableMap<Point, Int>
    ) {
        // Check if the starting position is valid
        if (!map.containsKey(start) || resultMap.containsKey(start) || map[start] != target) {
            return
        }

        // Assign the current position to the region
        resultMap[start] = replacement

        // Recursive calls for 4-connected neighbors (up, down, left, right)
        val neighbors = listOf(
            Point(start.x - 1, start.y), // Up
            Point(start.x + 1, start.y), // Down
            Point(start.x, start.y - 1), // Left
            Point(start.x, start.y + 1) // Right
        )

        for (neighbor in neighbors) {
            floodFill(map, neighbor, target, replacement, resultMap)
        }
    }

    private fun groupRegions(map: Map<Point, Char>): Map<Point, Int> {
        val resultMap = mutableMapOf<Point, Int>()
        var regionId = 1

        for ((point, value) in map) {
            if (!resultMap.containsKey(point)) {
                // Start a new region
                floodFill(map.toMutableMap(), point, value, regionId, resultMap)
                regionId++
            }
        }

        return resultMap
    }

    private fun <K, V> reverseMap(map: Map<K, V>): Map<V, List<K>> {
        return map
            .toList()
            .groupBy { pair -> pair.second }
            .mapValues { entry ->
                entry.value.map { it.first }
            }
    }

    private fun countPerimeter(map: Map<Point, Char>, points: Set<Point>): Int {
        return points.sumOf { point ->
            val n = NEIGHBOURS
                .map { neighbour -> neighbour + point }
                .filter {
                    val item = map[it]
                    item != null && item == map[point]
                }
                .size

            4 - n
        }
    }

    private fun countCorners(points: Set<Point>): Int {
        var corners = 0
        val set = points.toSet()

        points.forEach { point ->
            CORNERS.forEach { deltas ->
                val inside = deltas.map { it + point }.filter { set.contains(it) }.size
                val cornerInside = set.contains(point + deltas[1])

                val isExternalCorner = inside == 0
                val isExternalCornerDiagonally = inside == 1 && cornerInside
                val isInternalCorner = inside == 2 && !cornerInside

                if (isExternalCorner || isExternalCornerDiagonally || isInternalCorner) {
                    corners++
                }
            }
        }

        return corners
    }

    private fun parseInput() = readInput("_2024/12")
        .lines()
        .mapIndexed { row, line -> line.mapIndexed { col, symbol -> Point(row, col) to symbol } }
        .flatten()
        .toMap()
}