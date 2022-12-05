package _2022

import readInput
import java.util.*

object Task05 {

    // GRTSWNJHH
    fun partOne() = parseInput()
        .let { (stacks, instructions) ->
            instructions.forEach { (move, from, to) ->
                repeat(move) {
                    stacks[to]?.addFirst(stacks[from]?.removeFirst())
                }
            }

            topCrates(stacks)
        }

    // QLFQDBBHM
    fun partTwo() = parseInput()
        .let { (stacks, instructions) ->
            instructions.forEach { (move, from, to) ->
                val temp = LinkedList<String>()
                repeat(move) { temp.addFirst(stacks[from]?.removeFirst()) }
                repeat(move) { stacks[to]?.addFirst(temp.removeFirst()) }
            }

            topCrates(stacks)
        }

    private fun topCrates(stacks: Map<Int, LinkedList<String>>): String =
        stacks.entries.sortedBy { it.key }.joinToString("") { it.value.peekFirst() }

    private fun parseInput() = readInput("_2022/05")
        .split("\n\n")
        .let { (stacks, instructions) -> parseStacks(stacks) to parseInstructions(instructions) }

    // todo: Change Int? to Int
    private fun parseStacks(input: String): Map<Int, LinkedList<String>> {
        return input.split('\n').let {
            val positions = it
                .last()
                .mapIndexed { index, char -> index to char }
                .filter { (_, char) -> char.isDigit() }
                    // todo: improve
                .associate { (index, char) -> index to char.toString().toInt() }

            it
                .map { line ->
                    line
                        .mapIndexed { index, char ->
                            (positions[index] ?: -1) to char
                        }
                        .filter { (index, char) -> char.isLetter() }
                }
                .flatten()
                .groupBy { (index) -> index }
                .map { (index, list) -> index to LinkedList(list.map { (_, char) -> char.toString() }) }
                .toMap()
        }
    }

    private fun parseInstructions(input: String): List<List<Int>> = input
        .split('\n')
        .map {
            it.split(' ').filter { it.toIntOrNull() != null }.map { it.toInt() }
        }

}