package euler

import EulerTask
import utils.Math.heptagonalNumber
import utils.Math.hexagonalNumber
import utils.Math.octagonalNumber
import utils.Math.pentagonalNumber
import utils.Math.squareNumber
import utils.Math.triangularNumber

object Task61CyclicalFigurateNumbers : EulerTask("Cyclical figurate numbers") {

    override fun solution(): Long {
        val allNumbers: List<FigurateNumber> = listOf(
            NumberType.Triangle,
            NumberType.Square,
            NumberType.Pentagonal,
            NumberType.Hexagonal,
            NumberType.Heptagonal,
            NumberType.Octagonal,
        ).map { type ->
            type.numbers().map { n ->
                FigurateNumber(n, type)
            }
        }.flatten()

        val q = ArrayDeque<Attempt>()
        q.addAll(allNumbers.map(::Attempt))

        while (q.isNotEmpty()) {
            val attempts = ArrayList<Attempt>()
            val current = q.removeLast()

            for (number in allNumbers) {
                if (current.types.contains(number.type)) {
                    continue
                }

                if (current.numbers.last().isCyclicalTo(number)) {
                    val attempt = current + number
                    attempts.add(attempt)

                    if (attempt.isSolution()) {
                        return attempt.numbers.sumOf { it.value }
                    }
                }
            }

            q.addAll(attempts)
        }

        return -1
    }

    private enum class NumberType {
        Triangle,
        Square,
        Pentagonal,
        Hexagonal,
        Heptagonal,
        Octagonal;

        fun numbers(): List<Long> =
            (1..1000L).map { n -> number(n) }.filter { it in 1000..9999 }

        private fun number(n: Long): Long {
            return when (this) {
                Triangle -> triangularNumber(n)
                Square -> squareNumber(n)
                Pentagonal -> pentagonalNumber(n)
                Hexagonal -> hexagonalNumber(n)
                Heptagonal -> heptagonalNumber(n)
                Octagonal -> octagonalNumber(n)
            }
        }
    }

    private data class Attempt(val numbers: List<FigurateNumber>, val types: Set<NumberType>) {

        constructor(number: FigurateNumber): this(listOf(number), setOf(number.type))

        operator fun plus(number: FigurateNumber): Attempt =
            Attempt(this.numbers + number, types + number.type)

        fun isSolution(): Boolean =
            numbers.size == 6 && numbers.last().isCyclicalTo(numbers.first())
    }

    private data class FigurateNumber(val value: Long, val type: NumberType) {

        private val firstHalf by lazy { value / 100 }
        private val secondHalf by lazy { value % 100 }

        fun isCyclicalTo(other: FigurateNumber): Boolean =
            this.secondHalf == other.firstHalf

    }

}
