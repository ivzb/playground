package utils

import java.math.BigInteger
import kotlin.math.*

object Math {

    /**
     * Euclid's algorithm for finding the greatest common divisor of a and b.
     */
    fun gcd(a: Long, b: Long): Long = if (b == 0L) a.absoluteValue else gcd(b, a % b)
    fun gcd(f: Long, vararg n: Long): Long = n.fold(f, ::gcd)
    fun Iterable<Long>.gcd(): Long = reduce(::gcd)


    /**
     * Find the least common multiple of a and b using the gcd of a and b.
     */
    fun lcm(a: Long, b: Long): Long = (a * b) / gcd(a, b)

    fun List<Int>.lcm(): Int = this.map { it.toLong() }.lcm().toInt()
    fun IntRange.lcm(): Int = this.toList().lcm()

    fun List<Long>.lcm(): Long = this.reduce { acc, n -> lcm(acc, n) }
    fun LongRange.lcm(): Long = this.toList().lcm()

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

    private val fibonacciCache by lazy {
        HashMap<Int, BigInteger>().also {
            it[0] = 0.toBigInteger()
            it[1] = 1.toBigInteger()
        }
    }

    fun fibonacci(n: Int): BigInteger =
        fibonacciCache.getOrPut(n) {
            fibonacci(n - 1) + fibonacci(n - 2)
        }

    fun fibonacci() =
        generateSequence(0) { n -> n + 1 }.map { n -> fibonacci(n) }

    fun fibonacciIndexOf(digits: Int): Int {
        val phi = (1 + sqrt(5.0)) / 2
        val limit = ((digits - 1) + log10(5.0) / 2) / log10(phi);
        return ceil(limit).toInt()
    }

    fun findPrimes() = sequence {
        val primes = ArrayList<Long>()

        fun isPrime(n: Long): Boolean {
            var i = 0

            while (i < primes.size && (primes[i] * primes[i]) <= n) {
                if (n % primes[i] == 0L) {
                    return false
                }

                i++
            }

            return true
        }

        var i = 2L

        while (true) {
            if (isPrime(i)) {
                primes.add(i)
                yield(i)
            }

            i++
        }
    }

    fun findPrimesUntil(number: Int): List<Long> {
        return findPrimes().takeWhile { prime -> prime <= number }.toList()
    }

    fun isPalindrome(n: Int): Boolean {
        return n == reverse(n)
    }

    fun reverse(n: Int): Int {
        var remaining = n
        var reverse = 0

        while (remaining != 0) {
            reverse *= 10
            reverse += remaining % 10
            remaining /= 10
        }

        return reverse
    }

    fun List<Int>.product(): Int = this.reduce(Int::times)

    fun square(n: Int): Int = n * n

    fun factorial(n: Int): BigInteger =
        (1..n).fold(BigInteger.ONE) { factorial, i ->
            factorial.multiply(i.toBigInteger())
        }

    fun binom(n: Int, k: Int): Long {
        val b = LongArray(n + 1)

        for (i in 0..n) {
            b[i] = 1

            for (j in i - 1 downTo 1) {
                b[j] += b[j - 1]
            }
        }

        return b[k]
    }

    // https://mathschallenge.net/index.php?section=faq&ref=number/sum_of_divisors
    fun sumOfProperDivisors(number: Int): Int {
        var n = number
        var sum = 1
        var p = 2

        while (p * p <= n && n > 1) {
            if (n % p == 0) {
                var j = p * p
                n /= p

                while (n % p == 0) {
                    j *= p
                    n /= p
                }

                sum *= j - 1
                sum /= p - 1
            }

            if (p == 2) {
                p = 3
            } else {
                p += 2
            }
        }

        if (n > 1) {
            sum *= n + 1
        }

        return sum - number
    }

    // https://www.geeksforgeeks.org/find-recurring-sequence-fraction/
    // https://mathworld.wolfram.com/DecimalExpansion.html
    fun patternInFraction(numerator: Int, denominator: Int): List<Int> {
        val digits = ArrayList<Int>()
        val remainders = HashMap<Int, Int>()

        var remainder = numerator % denominator

        while (remainder != 0 && !remainders.contains(remainder)) {
            remainders[remainder] = digits.size
            remainder *= 10
            digits.add(remainder / denominator)
            remainder %= denominator
        }

        return if (remainder == 0) {
            listOf()
        } else {
            val startIndex = remainders[remainder] ?: error("undefined remainder")
            val endIndex = digits.size - 1
            digits.slice(startIndex..endIndex)
        }
    }

}