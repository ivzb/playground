object Task01 {

    fun partOne() = parseInput().maxOf { it }.toString()

    fun partTwo() = parseInput().sortedDescending().take(3).sum().toString()

    private fun parseInput() = readInput("01")
        .split("\n\n")
        .map { it.split("\n").sumOf { it.toInt() } }

}