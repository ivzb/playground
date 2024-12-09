package advent_of_code._2024

import Task
import readInput
import java.util.*

object Task09 : Task {

    override fun partA() = parseInput()
        .let {
            val disk = generateDisk(it).flatten()
            val list = LinkedList(disk)
            val block = mutableListOf<Long>()

            while (list.isNotEmpty()) {
                val char = list.removeFirst()

                if (char == ".") {
                    while (true) {
                        if (list.isEmpty()) {
                            break
                        }

                        val lastChar = list.removeLast()

                        if (lastChar != ".") {
                            block.add(lastChar.toLong())
                            break
                        }
                    }
                } else {
                    block.add(char.toLong())
                }
            }

            block
                .mapIndexed { index, char ->
                    char.toString().toLong() * index
                }
                .sum()
        }

    override fun partB() = parseInput()
        .let {
            val disk = generateDisk(it)
            var block = disk.flatten().joinToString(",")

            disk.reversed().forEach {
                if (!it.contains(".")) {
                    val search = it.joinToString(",")
                    val searchIndex = block.indexOf(search)

                    val swap = it.map { "." }.joinToString(",")
                    val swapIndex = block.indexOf(swap)

                    if (searchIndex != -1 && swapIndex != -1 && swapIndex < searchIndex) {
                        block =
                            block.substring(0 until swapIndex) +
                            search +
                            block.substring(swapIndex + swap.length, searchIndex) +
                            swap +
                            block.substring(searchIndex + search.length)
                    }
                }
            }

            block
                .split(',')
                .mapIndexed { index, char ->
                    if (char == ".") {
                        0
                    } else {
                        char.toLong() * index
                    }
                }
                .sum()
        }

    private fun generateDisk(input: String): List<List<String>> {
        var id = 0

        return input.mapIndexed { index, char ->
            val isEven = index % 2 == 0

            if (isEven) {
                // file
                val times = char.digitToInt()
                List(times) { id.toString() }.also { id++ }
            } else {
                // free space
                val times = char.digitToInt()
                List(times) { "." }
            }
        }
    }

    private fun parseInput() = readInput("_2024/09")

}