import Task02.Shape.*

object Task02 {

    enum class Shape(val score: Int) {
        Rock(1),
        Paper(2),
        Scissors(3)
    }

    enum class Outcome(val score: Int) {
        Lose(0),
        Draw(3),
        Win(6)
    }

    val shapes = mapOf(
        "A" to Rock,
        "B" to Paper,
        "C" to Scissors,
        "X" to Rock,
        "Y" to Paper,
        "Z" to Scissors,
    )

    val need = mapOf(
        "X" to Outcome.Lose,
        "Y" to Outcome.Draw,
        "Z" to Outcome.Win,
    )

    val outcomes = mapOf(
        Pair(Rock, Scissors) to Outcome.Lose,
        Pair(Scissors, Rock) to Outcome.Win,

        Pair(Scissors, Paper) to Outcome.Lose,
        Pair(Paper, Scissors) to Outcome.Win,

        Pair(Paper, Rock) to Outcome.Lose,
        Pair(Rock, Paper) to Outcome.Win,

        Pair(Rock, Rock) to Outcome.Draw,
        Pair(Paper, Paper) to Outcome.Draw,
        Pair(Scissors, Scissors) to Outcome.Draw,
    )

    // 13446
    fun partOne() = parseInput()
        .map { (opponent, me) ->
            val it = shapes[opponent] to shapes[me]
            val outcome = outcomes[it]
            (outcome?.score ?: 0) + (it.second?.score ?: 0)
        }
        .sum()

    // 13509
    fun partTwo() =
        parseInput()
            .map { (opponent, me) ->
                val n = need[me]
                val entry = outcomes.entries.find { it.value == n && it.key.first == shapes[opponent] }
                val outcome = entry?.value
                val shape = entry?.key?.second
                (outcome?.score ?: 0) + (shape?.score ?: 0)
            }
            .sum()

    private fun parseInput() = readInput("02")
        .split("\n")
        .map { it.split(' ') }
}