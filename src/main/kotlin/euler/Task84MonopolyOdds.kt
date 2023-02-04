package euler

import EulerTask

object Task84MonopolyOdds : EulerTask("Monopoly odds") {

    override fun solution(): Int {
        val board = arrayOf(
            "GO", "A1", "CC1", "A2", "T1", "R1", "B1", "CH1", "B2", "B3",
            "JAIL", "C1", "U1", "C2", "C3", "R2", "D1", "CC2", "D2", "D3",
            "FP", "E1", "CH2", "E2", "E3", "R3", "F1", "F2", "U2", "F3",
            "G2J", "G1", "G2", "CG3", "G3", "R4", "CH3", "H1", "T2", "H2",
        )

        val indexes = board.mapIndexed { index, tile -> tile to index }.toMap()
        val jailIndex = indexes["JAIL"] ?: error("undefined tile")
        val g2jIndex = indexes["G2J"] ?: error("undefined tile")

        val cc1Index = indexes["CC1"] ?: error("undefined tile")
        val cc2Index = indexes["CC2"] ?: error("undefined tile")

        val ch1Index = indexes["CH1"] ?: error("undefined tile")
        val ch2Index = indexes["CH2"] ?: error("undefined tile")
        val ch3Index = indexes["CH3"] ?: error("undefined tile")

        val communityChestCards = listOf("GO", "JAIL", null, null, null, null, null, null, null, null, null, null, null, null, null, null)
        val chanceCards = listOf("GO", "JAIL", "C1", "E3", "H2", "R1", "NEXT_R", "NEXT_R", "NEXT_U", "BACK_3", null, null, null, null, null, null)

        val dice = ArrayList<Pair<Int, Boolean>>()

        for (dice1 in 1..4) {
            for (dice2 in 1..4) {
                val sum = dice1 + dice2
                val isDouble = dice1 == dice2
                dice.add(sum to isDouble)
            }
        }

        val commands = HashMap<Pair<String, Int>, Int>()
        val doubleBoard = board + board

        for (index in board.indices) {
            commands["GO" to index] = indexes["GO"] ?: error("undefined index")
            commands["JAIL" to index] = indexes["JAIL"] ?: error("undefined index")
            commands["C1" to index] = indexes["C1"] ?: error("undefined index")
            commands["E3" to index] = indexes["E3"] ?: error("undefined index")
            commands["H2" to index] = indexes["H2"] ?: error("undefined index")
            commands["R1" to index] = indexes["R1"] ?: error("undefined index")
            commands["NEXT_R" to index] = doubleBoard.drop(index).indexOfFirst { it[0] == 'R' } % board.size
            commands["NEXT_U" to index] = doubleBoard.drop(index).indexOfFirst { it[0] == 'U' } % board.size
            commands["BACK_3" to index] = (board.size + index - 3) % board.size
        }

        val odds = IntArray(board.size)
        var total = 0

        for (i in 1..10_000) {
            val ccCards = ArrayDeque(communityChestCards.shuffled())
            val chCards = ArrayDeque(chanceCards.shuffled())

            var index = 0
            var doubles = 0

            for (rolls in 0..1_000) {
                odds[index]++
                total++

                val (roll, isDouble) = dice.random()

                if (isDouble) {
                    doubles++

                    if (doubles == 3) {
                        index = jailIndex
                        doubles = 0
                        continue
                    }
                } else {
                    doubles = 0
                }

                index = (index + roll) % board.size

                when (index) {
                    g2jIndex -> index = jailIndex

                    ch1Index, ch2Index, ch3Index -> {
                        val card = chCards.removeFirst()

                        indexes[card]?.let { targetIndex ->
                            index = targetIndex
                        }

                        chCards.addLast(card)
                    }

                    cc1Index, cc2Index -> {
                        val card = ccCards.removeFirst()

                        if (card != null) {
                            index = commands[card to index] ?: error("undefined command")
                        }

                        ccCards.addLast(card)
                    }
                }

                if (index == g2jIndex) {
                    index = jailIndex
                }
            }
        }

        val result = ArrayList<Pair<String, Double>>()

        for (index in board.indices) {
            val tile = board[index]
            val probability = (odds[index].toDouble() / total) * 100
            result.add(tile to probability)
        }

        return result
            .asSequence()
            .sortedByDescending { (_, probability) -> probability }
            .take(3)
            .map { (tile) -> indexes[tile] }
            .joinToString("")
            .toInt()
    }

}
