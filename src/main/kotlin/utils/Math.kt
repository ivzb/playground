package utils

import kotlin.math.absoluteValue

object Math {

    /**
     * Euclid's algorithm for finding the greatest common divisor of a and b.
     */
    fun gcd(a: Int, b: Int): Int = if (b == 0) a.absoluteValue else gcd(b, a % b)
    fun gcd(f: Int, vararg n: Int): Int = n.fold(f, ::gcd)
    fun Iterable<Int>.gcd(): Int = reduce(::gcd)


    /**
     * Find the least common multiple of a and b using the gcd of a and b.
     */
    fun lcm(a: Int, b: Int): Int =
        (a * b) / gcd(a, b)

    fun List<Int>.lcm(): Int =
        this.reduce { acc, n -> lcm(acc, n) }

    infix fun Long.isDivisibleBy(divider: Int): Boolean = this % divider == 0L

    infix fun Int.isDivisibleBy(divider: Int): Boolean = this % divider == 0

    fun Iterable<Long>.product(): Long = reduce(Long::times)

    /**
     * Simple algorithm to find the primes of the given Int.
     */
    fun Int.primes(): Sequence<Int> = sequence {
        var n = this@primes
        var j = 2

        while (j * j <= n) {
            while (n % j == 0) {
                yield(j)
                n /= j
            }
            j++
        }

        if (n > 1) {
            yield(n)
        }
    }

    /**
     * Simple algorithm to find the primes of the given Long.
     */
    fun Long.primes(): Sequence<Long> = sequence {
        var n = this@primes
        var j = 2L
        while (j * j <= n) {
            while (n % j == 0L) {
                yield(j)
                n /= j
            }
            j++
        }

        if (n > 1) {
            yield(n)
        }
    }
}