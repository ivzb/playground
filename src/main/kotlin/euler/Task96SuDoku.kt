package euler

import EulerTask
import readInput
import java.lang.Exception
import java.lang.StringBuilder

object Task96SuDoku : EulerTask("Su Doku") {

    override fun solution(): Int {
        val input = parseInput()

        val suDokus = input
            .first().solve()
//            .map {
//                it.solve()
//            }

        return -1
    }

    private fun parseInput(): List<SuDoku> =
        readInput("euler/96")
            .split("Grid \\d+\n".toRegex())
            .drop(1)
            .map {
                it
                    .split("\n")
                    .dropLast(1)
                    .map { it.map { it.digitToInt() }.toIntArray() }
                    .toTypedArray()
            }
            .map { SuDoku(it) }

    class SuDoku(private val grid: Array<IntArray>) {

        private val isSolved: Boolean
            get() = grid.all { it.all { it != 0 } }

        fun solve() {
            var didChange = false

            while (!isSolved) {
//                println(toString())

                for (i in 0 .. 8) {
                    for (j in 0..8) {
                        print(grid[i][j])
                    }
                    println()
                }

                break
            }

            if (!isSolved) {
                throw Exception("couldn't solve")
            }
        }

        override fun toString(): String {
            return StringBuilder().apply {
                append(grid.joinToString("\n") { it.joinToString("") })
                append("\n")
            }.toString()
        }
    }

}
