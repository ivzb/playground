object Task03 {

    fun partOne() = parseInput()
        .map { it.chunked(it.length / 2) }
        .sumOf { (compartment1, compartment2) ->
            compartment1.toSet()
                .intersect(compartment2.toSet())
                .sumOf { priority(it) }
        }

    fun partTwo() = parseInput()
        .chunked(3)
        .sumOf { (rucksack1, rucksack2, rucksack3) ->
            rucksack1.toSet()
                .intersect(rucksack2.toSet())
                .intersect(rucksack3.toSet())
                .sumOf { priority(it) }
        }

    private fun priority(it: Char): Int {
        return if (it.isUpperCase()) {
            it - 'A' + 27
        } else {
            it - 'a' + 1
        }
    }

    private fun parseInput() = readInput("03").split("\n")

}