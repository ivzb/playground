package advent_of_code._2025

import Task
import readInput
import java.lang.Long.max
import java.lang.Long.min

object Task05 : Task {

    override fun partA(): Int = parseInput()
        .let { (ranges, ids) ->
            ids.count { id -> ranges.any { range -> id in range } }
        }

    override fun partB(): Long = parseInput()
        .let { (ranges, _) ->
            merge(ranges).sumOf { range -> range.last - range.first + 1 }
        }

    private fun merge(ranges: List<LongRange>): List<LongRange> =
        ranges
            .sortedBy { it.first }
            .fold(listOf()) { merged, currentRange ->
                when {
                    merged.isEmpty() -> {
                        listOf(currentRange)
                    }

                    intersects(currentRange, merged.last()) -> {
                        val range = mergeRanges(currentRange, merged.last())
                        merged.dropLast(1) + listOf(range)
                    }

                    else -> {
                        merged + listOf(currentRange)
                    }
                }
            }

    private fun intersects(a: LongRange, b: LongRange): Boolean =
        (a.first in b || a.last in b) || (b.first in a || b.last in a)

    private fun mergeRanges(a: LongRange, b: LongRange): LongRange =
        min(a.first, b.first) .. max(a.last, b.last)

    private fun parseInput() = readInput("_2025/05")
        .split("\n\n")
        .let { (fresh, ingredients) ->
            val ranges = fresh
                .lines()
                .map { it.split("-") }
                .map { (start, end) -> start.toLong() .. end.toLong() }

            val ids = ingredients
                .lines()
                .map { it.toLong() }

            ranges to ids
        }
}