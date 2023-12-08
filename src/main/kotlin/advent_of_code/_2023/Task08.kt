package advent_of_code._2023

import Task
import readInput
import utils.Math.lcm
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

object Task08 : Task {

    override fun partA(): Long {
        val input = parseInput()
        val instructions = input.first()
        val map = input
            .drop(2)
            .associate {
                val (step, left, right) = it.split(',')
                step to (left to right)
            }

        var index = 0
        var steps = 0L
        var node = "AAA"

        while (true) {
            val instruction = instructions[index]
            index++
            steps++

            if (index >= instructions.length) {
                index = 0
            }

            val (left, right) = map[node]!!

            if (instruction == 'L') {
                node = left

                if (node == "ZZZ") {
                    return steps
                }

                continue
            }

            if (instruction == 'R') {
                node = right

                if (node == "ZZZ") {
                    return steps
                }

                continue
            }

            error("unknown instruction $instruction")
        }
    }

    override fun partB(): Long {
        val input = parseInput()
        val instructions = input.first()
        val map = input
            .drop(2)
            .associate {
                val (step, left, right) = it.split(',')
                step to (left to right)
            }

        val startingNodes = map.keys.filter { it.endsWith("A") }.toTypedArray()
        val loops = ArrayList<Long>()

        for (startingNode in startingNodes) {
            var index = 0
            var steps = 0
            var node = startingNode
            val loop = ArrayList<String>()

            while (true) {
                val instruction = instructions[index]
                index++
                steps++

                if (index >= instructions.length) {
                    index = 0
                }

                loop.add(node)

                val (left, right) = map[node]!!

                val loopEnd = node.endsWith('Z')

                if (loopEnd) {
                    break
                }

                if (instruction == 'L') {
                    node = left
                    continue
                }

                if (instruction == 'R') {
                    node = right
                    continue
                }

                error("unknown instruction $instruction")
            }

            loops.add(loop.size.toLong() - 1)
        }

        return loops.reduce { a, b -> lcm(a, b) }
    }

    private fun parseInput() = readInput("_2023/08")
        .split("\n")
        .map {
            it
                .replace("(", "")
                .replace(")", "")
                .replace(" ", "")
                .replace("=", ",")
        }

}