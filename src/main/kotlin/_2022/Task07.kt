package _2022

import Task
import readInput

object Task07 : Task {

    private const val MAX_SPACE = 100_000
    private const val NEEDED_SPACE = 30_000_000
    private const val TOTAL_SPACE = 70_000_000

    override fun partA() = buildFilesystem().root.flatten()
        .map { it.space() }
        .filter { space -> space <= MAX_SPACE }
        .sum()

    override fun partB() = buildFilesystem().let { fs ->
        fs.root.flatten()
            .map { it.space() }
            .sorted()
            .first { space ->
                space >= NEEDED_SPACE - (TOTAL_SPACE - fs.root.space())
            }
    }

    private fun buildFilesystem() =
        parseInput().fold(Filesystem()) { fs, input -> fs.parse(input) }

    data class Filesystem(val root: Directory, var current: Directory?) {
        constructor() : this(root = Directory("/"))
        constructor(root: Directory) : this(root = root, current = root)

        fun parse(input: List<String>): Filesystem =
            input.let { (arg1, arg2, arg3) ->
                when (arg1) {
                    "$" -> current = when (arg3) {
                        null -> current
                        "/" -> root
                        ".." -> current?.parent
                        else -> current?.children?.find { it.name == arg3 }
                    }

                    "dir" -> current?.children?.add(
                        Directory(name = arg2, parent = current)
                    )

                    else -> current?.files?.add(arg1.toInt())
                }

                return this
            }
    }

    data class Directory(
        val name: String,
        val parent: Directory? = null,
        val children: ArrayList<Directory> = ArrayList(),
        val files: ArrayList<Int> = ArrayList()
    ) {

        fun space(): Int =
            files.sumOf { it } + children.sumOf { it.space() }

        fun flatten(): List<Directory> =
            children + children.flatMap { it.flatten() }
    }

    private fun parseInput() = readInput("_2022/07")
        .split('\n')
        .map { it.split(" ") }

    operator fun List<String>.component3(): String? = getOrNull(2)

}