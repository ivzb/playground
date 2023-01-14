package utils

import utils.Math.gcd
import utils.Math.lcm
import java.math.BigDecimal
import java.math.BigInteger

data class Fraction(val numerator: BigInteger, val denominator: BigInteger) {

    constructor(numerator: BigInteger, denominator: Fraction): this(numerator * denominator.denominator, denominator.numerator)
    constructor(numerator: Long, denominator: Long): this(numerator.toBigInteger(), denominator.toBigInteger())
    constructor(numerator: Long, denominator: Fraction): this(numerator.toBigInteger(), denominator)

    val decimal: Double by lazy { numerator.toDouble() / denominator.toDouble() }

    val simplified: Fraction by lazy {
        val gcd = gcd(numerator, denominator)
        Fraction(numerator / gcd, denominator / gcd)
    }

    operator fun times(other: Fraction): Fraction =
        Fraction(this.numerator * other.numerator, this.denominator * other.denominator)

    operator fun plus(other: Fraction): Fraction {
        val lcm = lcm(this.denominator, other.denominator)
        val thisNumerator = this.numerator * (lcm / this.denominator)
        val otherNumerator = other.numerator * (lcm / other.denominator)

        return Fraction(thisNumerator + otherNumerator, lcm)
    }

    override fun toString(): String = "$numerator/$denominator"
}