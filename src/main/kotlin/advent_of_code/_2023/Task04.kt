package advent_of_code._2023

import Task
import readInput

object Task04 : Task {

    override fun partA(): Int {
        return parseInput()
            .map { (winningNumbers, myNumbers) ->
                winningNumbers.toSet().intersect(myNumbers.toSet())
            }
            .map { myWinningCards ->
                myWinningCards.count() - 1
            }
            .sumOf { wins ->
                (1 shl wins).coerceAtLeast(0)
            }
    }

    override fun partB(): Int {
        val cardCopies = HashMap<Int, Int>()

        return parseInput()
            .map { (winningNumbers, myNumbers) ->
                winningNumbers.toSet().intersect(myNumbers.toSet())
            }
            .mapIndexed { index, myWinningCards ->
                val cardId = index + 1
                val copies = cardCopies[cardId] ?: 0
                val wins = copies + 1

                repeat(wins) { saveCopies(myWinningCards, cardId, cardCopies) }

                wins
            }
            .sum()
    }

    private fun parseInput() = readInput("_2023/04")
        .split("\n")
        .map {
            val (_, numbers) = it.split(": ")
            val (winningNumbers, myNumbers) = numbers.split(" | ").map { it.split(" ").filter { it != "" } }
            winningNumbers to myNumbers
        }

    private fun saveCopies(winningCards: Set<String>, cardId: Int, cardCopies: HashMap<Int, Int>) {
        winningCards
            .mapIndexed { index, _ ->
                cardId + index + 1
            }
            .forEach { win ->
                cardCopies[win] = cardCopies.getOrDefault(win, 0) + 1
            }
    }

}