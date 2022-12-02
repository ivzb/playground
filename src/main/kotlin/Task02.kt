object Task02 {

    fun partOne() = parseInput().sumOf {
        val other = it[0] - 'A'
        val me = it[2] - 'X'

        val outcome = when {
            other == me -> DRAW
            (me + 1) % 3 == other % 3 -> LOSE
            else -> WIN
        } * 3
        val shape = me + 1

        outcome + shape
    }

    fun partTwo() = parseInput().sumOf {
        val other = it[0] - 'A' + 1
        val me = it[2] - 'X'

        val outcome = me * 3
        val shape = when (me) {
            LOSE -> loseTo[other]
            WIN -> winTo[other]
            DRAW -> other
            else -> 0
        } ?: 0

        outcome + shape
    }

    private fun parseInput() = readInput("02").split("\n")

    private const val LOSE = 0
    private const val DRAW = 1
    private const val WIN = 2

    private const val ROCK = 1
    private const val PAPER = 2
    private const val SCISSORS = 3

    private val winTo = mapOf(
        ROCK to PAPER,
        SCISSORS to ROCK,
        PAPER to SCISSORS,
    )

    private val loseTo = mapOf(
        ROCK to SCISSORS,
        SCISSORS to PAPER,
        PAPER to ROCK,
    )
}