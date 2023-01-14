package euler

import EulerTask
import readInput
import java.lang.Math.abs

object Task54PokerHands : EulerTask("Poker hands") {

    override fun solution(): Int {
        var wins = 0

        for ((player1Hand, player2Hand) in hands()) {
            if (player1Hand > player2Hand) {
                wins++
            }
        }

        return wins
    }

    private fun hands() =
        readInput("euler/54")
            .split('\n')
            .map { it.split(' ').map { Card.from(it) } }
            .map { Hand(it.take(5).toSet()) to Hand(it.takeLast(5).toSet()) }

    data class Hand(val cards: Set<Card>) : Comparable<Hand> {

        override fun compareTo(other: Hand): Int {
            val handComparison = this.rank().ordinal - other.rank().ordinal

            if (handComparison != 0) return handComparison

            for (i in 0 until this.pairRanks.size) {
                val pairsComparison = this.pairRanks[i].numeric - other.pairRanks[i].numeric

                if (pairsComparison != 0) return pairsComparison
            }

            return this.highestCard.rank.numeric - other.highestCard.rank.numeric
        }

        private val highestCard by lazy {
            cards
                .filterNot { pairRanks.isNotEmpty() && pairRanks.contains(it.rank) }
                .maxBy { it.rank.numeric }
        }

        private fun rank(): HandRank {
            return when {
                isRoyalFlush() -> HandRank.RoyalFlush
                isStraightFlush() -> HandRank.StraightFlush
                isFourOfAKind() -> HandRank.FourOfAKind
                isFullHouse() -> HandRank.FullHouse
                isFlush() -> HandRank.Flush
                isStraight() -> HandRank.Straight
                isThreeOfAKind() -> HandRank.ThreeOfAKind
                isTwoPairs() -> HandRank.TwoPairs
                isOnePair() -> HandRank.OnePair
                else -> HandRank.HighCard
            }
        }

        private fun isRoyalFlush(): Boolean {
            if (!isFlush()) {
                return false
            }

            val royalFlushRanks = setOf(Rank.Ten, Rank.Jack, Rank.Queen, Rank.King, Rank.Ace)
            val ranks = cards.map { it.rank }.toSet()

            return ranks.intersect(royalFlushRanks).size == royalFlushRanks.size
        }

        private fun isStraightFlush(): Boolean =
            isStraight() && isFlush()

        private fun isFourOfAKind(): Boolean =
            pairsRanks.contains(HandRank.FourOfAKind)

        private fun isFullHouse(): Boolean =
            pairsRanks.contains(HandRank.ThreeOfAKind) && pairsRanks.contains(HandRank.OnePair)

        private fun isFlush(): Boolean =
            distinctSuites() == 1

        private fun isStraight(): Boolean =
            consecutiveRanks()

        private fun isThreeOfAKind(): Boolean =
            pairsRanks.contains(HandRank.ThreeOfAKind)

        private fun isTwoPairs(): Boolean =
            pairsRanks.contains(HandRank.TwoPairs)

        private fun isOnePair(): Boolean =
            pairsRanks.contains(HandRank.OnePair)

        private val pairs by lazy {
            val ranks = cards.map { it.rank }
            ranks.toSet().map { rank -> ranks.count { it == rank } to rank }
        }

        private val pairRanks by lazy {
            pairs
                .filter { (pairs, _) -> pairs > 1 }
                .sortedByDescending { (pairs, _) -> pairs }
                .map { (_, rank) -> rank }
        }

        private val pairsRanks: Set<HandRank> by lazy {
            val pairsCounts = pairs.groupingBy { (count) -> count }.eachCount()

            val result = HashSet<HandRank>()

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

        private fun consecutiveRanks(): Boolean =
            cards
                .sortedByDescending { it.rank.numeric }
                .zipWithNext { a, b -> abs(a.rank.numeric - b.rank.numeric) == 1 }
                .all { isConsecutive -> isConsecutive }

        private fun distinctSuites(): Int =
            cards.map { it.suit }.distinct().size

    }

    enum class HandRank {
        HighCard,
        OnePair,
        TwoPairs,
        ThreeOfAKind,
        Straight,
        Flush,
        FullHouse,
        FourOfAKind,
        StraightFlush,
        RoyalFlush,
    }

    data class Card(val rank: Rank, val suit: Suit) {

        override fun toString(): String = "${rank.symbol}${suit.symbol}"

        companion object {

            fun from(card: String): Card {
                val (rank, suit) = card.toCharArray()
                return Card(Rank.from(rank), Suit.from(suit))
            }
        }
    }

    enum class Suit(val symbol: Char) {
        Clubs('C'),
        Diamonds('D'),
        Hearts('H'),
        Spades('S');

        companion object {

            fun from(symbol: Char): Suit {
                return values().first { it.symbol == symbol }
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

        companion object {

            fun from(symbol: Char): Rank {
                return values().first { it.symbol == symbol }
            }

        }
    }

}
