package _2022

import Task
import readInput

object Task18 : Task {

    override fun partA() = parseInput()
        .let { grid ->
            grid.cubes
                // count the number of sides of each cube that are not immediately connected to another cube
                .map { cube -> notTouching(cube.neighbours, grid.cubes) }
                .sumOf { it.size }
        }

    override fun partB() = parseInput()
        .let { grid ->
            grid.cubes.map { cube ->
                // count only cube sides that could be reached from outside, ignore trapped air inside
                listOf(grid.cubes, grid.trappedCubes).fold(cube.neighbours) { acc, cubes -> notTouching(acc, cubes) }
            }
            .sumOf { it.size }
        }

    private fun parseInput() = readInput("_2022/18")
        .split("\n")
        .map {
            it.split(',').map { it.toInt() }.let(::Cube)
        }
        .toSet()
        .let(::Grid)

    private fun notTouching(left: Set<Cube>, right: Set<Cube>): Set<Cube> =
        left.subtract(right)


    data class Grid(val cubes: Set<Cube>) {

        val trappedCubes: Set<Cube> by lazy {
            (minX..maxX).map { x ->
                (minY..maxY).map { y ->
                    (minZ..maxZ).map { z -> Cube(x, y, z) }.filter { cube -> isTrapped(cube) }
                }.flatten()
            }.flatten().toSet()
        }

        private val minX: Int = cubes.minOf { it.x }
        private val minY: Int = cubes.minOf { it.y }
        private val minZ: Int = cubes.minOf { it.z }
        private val min: Int = listOf(minX, minY, minZ).min()

        private val maxX: Int = cubes.maxOf { it.x }
        private val maxY: Int = cubes.maxOf { it.y }
        private val maxZ: Int = cubes.maxOf { it.z }
        private val max: Int = listOf(maxX, maxY, maxZ).max()

        private fun isTrapped(start: Cube): Boolean {
            val q = ArrayDeque(start.neighbours)
            val visited = HashSet<Cube>()

            while (q.isNotEmpty()) {
                val cube = q.removeLast()
                val isAir = !cubes.contains(cube)
                val isNotVisited = !visited.contains(cube)

                if (isAir && isNotVisited) {
                    val isSurface = cube.min < min || cube.max > max

                    if (isSurface) {
                        // start is exposed to the grid surface
                        return false
                    }

                    visited.add(cube)
                    q.addAll(cube.neighbours)
                }
            }

            // start is trapped within his neighbour cubes
            return true
        }
    }

    data class Cube(val x: Int, val y: Int, val z: Int) {

        constructor(coords: List<Int>): this(coords[0], coords[1], coords[2])

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

        override fun toString(): String = "($x, $y, $z)"
    }
}