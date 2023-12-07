package advent_of_code._2023

import Task
import readInput

object Task07 : Task {

    override fun partA(): Long = sumTotalWinnings(withJokerRule = false)

    override fun partB(): Long = sumTotalWinnings(withJokerRule = true)

    private fun sumTotalWinnings(withJokerRule: Boolean) =
        parseInput(withJokerRule = withJokerRule)
            .sortedBy { (hand, _) -> hand }
            .mapIndexed { index, (_, bid) ->
                val rank = index + 1
                rank * bid
            }
            .sum()

    private fun parseInput(withJokerRule: Boolean) = readInput("_2023/07")
        .split("\n")
        .map {
            val (cards, bid) = it.split(' ')
            val hand = Hand(cards.map { Card.from(it) }, withJokerRule = withJokerRule)

            hand to bid.toLong()
        }

    data class Hand(val cards: List<Card>, val withJokerRule: Boolean) : Comparable<Hand> {

        override fun compareTo(other: Hand): Int {
            val handComparison = this.rank().ordinal - other.rank().ordinal

            if (handComparison != 0) return handComparison

            for (i in 0 until this.cards.size) {
                val thisCard = this.cards[i]
                val otherCard = other.cards[i]
                val cardComparison = thisCard.rank.numeric(withJokerRule) - otherCard.rank.numeric(withJokerRule)

                if (cardComparison != 0) {
                    return cardComparison
                }
            }

            return 0
        }

        private fun rank(): HandRank {
            return when {
                isFiveOfAKind() -> HandRank.FiveOfAKind
                isFourOfAKind() -> HandRank.FourOfAKind
                isFullHouse() -> HandRank.FullHouse
                isThreeOfAKind() -> HandRank.ThreeOfAKind
                isTwoPairs() -> HandRank.TwoPairs
                isOnePair() -> HandRank.OnePair
                else -> HandRank.HighCard
            }
        }

        private fun isFiveOfAKind(): Boolean =
            pairsRanks.contains(HandRank.FiveOfAKind)

        private fun isFourOfAKind(): Boolean =
            pairsRanks.contains(HandRank.FourOfAKind)

        private fun isFullHouse(): Boolean =
            pairsRanks.contains(HandRank.ThreeOfAKind) && pairsRanks.contains(HandRank.OnePair)

        private fun isThreeOfAKind(): Boolean =
            pairsRanks.contains(HandRank.ThreeOfAKind)

        private fun isTwoPairs(): Boolean =
            pairsRanks.contains(HandRank.TwoPairs)

        private fun isOnePair(): Boolean =
            pairsRanks.contains(HandRank.OnePair)

        private val pairs by lazy {
            val ranks = cards.map { it.rank }

            ranks.toSet().map { rank ->
                val rankCount = ranks.count { it == rank }
                rankCount to rank
            }
        }

        private val pairsRanks: Set<HandRank> by lazy {
            val pairs = if (withJokerRule) pairsWithJokerRule() else pairs
            val pairsCounts = pairs.groupingBy { (count) -> count }.eachCount()

            val result = HashSet<HandRank>()

            if (pairsCounts.containsKey(5)) {
                result += HandRank.FiveOfAKind
            }

            if (pairsCounts.containsKey(4)) {
                result += HandRank.FourOfAKind
            }

            if (pairsCounts.containsKey(3)) {
                result += HandRank.ThreeOfAKind
            }

            if (pairsCounts.containsKey(2)) {
                result += when (pairsCounts[2]) {
                    1 -> HandRank.OnePair
                    2 -> HandRank.TwoPairs
                    else -> error("invalid amount of pairs")
                }
            }

            result
        }

        private fun pairsWithJokerRule(): List<Pair<Int, Rank>> {
            val jackPair = pairs.find { (_, rank) -> rank == Rank.Jack }
            val bestPair = pairs.filterNot { (_, rank) -> rank == Rank.Jack }.maxByOrNull { (count, _) -> count }

            var jackedPairs = pairs
                .filterNot { (_, rank) -> rank == Rank.Jack }
                .map { pair ->
                    if (pair == bestPair && jackPair != null) {
                        val (bestPairCount, bestPairRank) = bestPair
                        val (jackPairCount, _) = jackPair
                        bestPairCount + jackPairCount to bestPairRank
                    } else {
                        pair
                    }
                }

            if (jackedPairs.isEmpty()) {
                jackedPairs = pairs
            }

            return jackedPairs
        }

    }

    enum class HandRank {
        HighCard,
        OnePair,
        TwoPairs,
        ThreeOfAKind,
        FullHouse,
        FourOfAKind,
        FiveOfAKind,
    }

    data class Card(val rank: Rank) {

        override fun toString(): String = rank.symbol.toString()

        companion object {

            fun from(rank: Char): Card {
                return Card(Rank.from(rank))
            }
        }
    }

    enum class Rank(val symbol: Char, val numeric: Int) {
        Two('2', 2),
        Three('3', 3),
        Four('4', 4),
        Five('5', 5),
        Six('6', 6),
        Seven('7', 7),
        Eight('8', 8),
        Nine('9', 9),
        Ten('T', 10),
        Jack('J', 11),
        Queen('Q', 12),
        King('K', 13),
        Ace('A', 14);

        fun numeric(withJokerRule: Boolean): Int {
            if (withJokerRule && this == Jack) {
                return 1
            }

            return numeric
        }

        companion object {

            fun from(symbol: Char): Rank {
                return values().first { it.symbol == symbol }
            }

        }
    }

}