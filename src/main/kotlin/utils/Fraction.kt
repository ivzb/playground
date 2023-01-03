package utils

data class Fraction(val numerator: Long, val denominator: Long) {

    val decimal: Double by lazy { numerator.toDouble() / denominator.toDouble() }

    val simplified: Fraction by lazy {
        val gcd = Math.gcd(numerator, denominator)
        Fraction(numerator/gcd, denominator/gcd)
    }

    operator fun times(other: Fraction): Fraction =
        Fraction(this.numerator * other.numerator, this.denominator * other.denominator)

    override fun toString(): String = "$numerator/$denominator"
}