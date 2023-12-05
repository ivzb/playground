package advent_of_code._2023

import Task
import readInput

object Task05 : Task {

    override fun partA(): Any {
        val input = parseInput("\n\n")
        val seeds = input.first().substringAfter("seeds: ").split(' ').map { it.toLong() }
        val maps = input.drop(1).map {
            it.split("\n").drop(1).map {
                it.split(" ").map { it.toLong() }
            }
        }

        return seeds.map { seed ->
            var result = seed

            maps
                .map {
                    for ((destinationRangeStart, sourceRangeStart, rangeLength) in it) {
                        val sourceRange = sourceRangeStart until sourceRangeStart + rangeLength

                        if (sourceRange.contains(result)) {
                            val offset = destinationRangeStart - sourceRangeStart
                            result += offset
                            break
                        }
                    }
                }

            result
        }
            .min()
    }

    override fun partB(): Any {
        val input = parseInput("\n\n")
        val seeds = input
            .first()
            .substringAfter("seeds: ")
            .split(' ')
            .map { it.toLong() }
            .windowed(2, 2).map {
                val (start, length) = it
                start until start + length
            }
        
        val maps = input.drop(1).map {
            it.split("\n").drop(1).map {
                it.split(" ").map { it.toLong() }
            }
        }

        val mapRanges = maps.map {
            it.map { (destinationRangeStart, sourceRangeStart, rangeLength) ->
                val sourceRange = sourceRangeStart until sourceRangeStart + rangeLength
                val offset = destinationRangeStart - sourceRangeStart

                sourceRange to offset
            }
        }

        var globalMin = Long.MAX_VALUE

        seeds.parallelStream().forEach { seedRange ->
            var min = Long.MAX_VALUE

            for (seed in seedRange) {
                var result = seed

                mapRanges
                    .map {
                        for ((sourceRange, offset) in it) {
                            if (sourceRange.contains(result)) {
                                result += offset
                                break
                            }
                        }
                    }

                if (result < min) {
                    min = result
                }
            }

            if (min < globalMin) {
                globalMin = min
            }
        }

        return globalMin
    }

    private fun parseInput(delimiter: String = "\n") = readInput("_2023/05")
        .split(delimiter)

}