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

    fun gcd(a: BigInteger, b: BigInteger): BigInteger = if (b == BigInteger.ZERO) a.abs() else gcd(b, a % b)


    /**
     * Find the least common multiple of a and b using the gcd of a and b.
     */
    fun lcm(a: Long, b: Long): Long = (a * b) / gcd(a, b)
    fun lcm(a: BigInteger, b: BigInteger): BigInteger = (a * b) / gcd(a, b)

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

    fun isPrime(n: Long): Boolean {
        var i = 2

        while (i <= sqrt(n.toDouble()).toInt()) {
            if (n % i == 0L) {
                return false
            }

            i++
        }

        return true
    }

    fun isPrime(n: Long, primes: List<Long>): Boolean {
        var i = 0

        while (i < primes.size && (primes[i] * primes[i]) <= n) {
            if (n % primes[i] == 0L) {
                return false
            }

            i++
        }

        return true
    }

    fun findPrimes() = sequence {
        val primes = ArrayList<Long>()

        var i = 2L

        while (true) {
            if (isPrime(i, primes)) {
                primes.add(i)
                yield(i)
            }

            i++
        }
    }

    fun findPrimesUntil(number: Int): List<Long> {
        return findPrimes().takeWhile { prime -> prime <= number }.toList()
    }

    fun Int.isPalindrome(base: Int = 10): Boolean {
        val n = this
        var reversed = 0
        var k = n

        while (k > 0) {
            reversed = base * reversed + (k % base)
            k /= base
        }

        return n == reversed
    }

    fun Long.isPalindrome(base: Long = 10): Boolean {
        val n = this
        var reversed = 0L
        var k = n

        while (k > 0) {
            reversed = base * reversed + (k % base)
            k /= base
        }

        return n == reversed
    }

    fun BigInteger.isPalindrome(base: BigInteger = 10.toBigInteger()): Boolean {
        val n = this
        var reversed = BigInteger.ZERO
        var k = n

        while (k > BigInteger.ZERO) {
            reversed = base * reversed + (k % base)
            k /= base
        }

        return n == reversed
    }

    fun Int.reversed(): Int {
        var remaining = this
        var reverse = 0

        while (remaining != 0) {
            reverse *= 10
            reverse += remaining % 10
            remaining /= 10
        }

        return reverse
    }

    fun Long.reversed(): Long {
        var remaining = this
        var reverse = 0L

        while (remaining != 0L) {
            reverse *= 10
            reverse += remaining % 10
            remaining /= 10
        }

        return reverse
    }

    fun BigInteger.reversed(): BigInteger {
        var remaining = this
        var reverse = BigInteger.ZERO
        val ten = 10.toBigInteger()

        while (remaining != BigInteger.ZERO) {
            reverse *= ten
            reverse += remaining.mod(ten)
            remaining /= ten
        }

        return reverse
    }

    fun List<Int>.product(): Int = this.reduce(Int::times)

    fun square(n: Int): Int = n * n

    private val factorialCache = HashMap<Int, BigInteger>()

    fun factorial(n: Int): BigInteger =
        factorialCache.getOrPut(n) {
            (1..n).fold(BigInteger.ONE) { factorial, i ->
                factorial.multiply(i.toBigInteger())
            }
        }

    fun binom(n: Long, k: Long): BigInteger {
        if (k > n) {
            return BigInteger.ZERO
        }

        val n = n.toBigInteger()
        var c = n

        for (i in 1..k) {
            val i = i.toBigInteger()
            c *= n - i
            c /= i + BigInteger.ONE
        }

        return c
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

    fun Int.digits(): List<Int> {
        val digits = ArrayList<Int>()

        var n = this

        while (n != 0) {
            digits.add(n % 10)
            n /= 10
        }

        return digits.reversed()
    }

    fun Long.digits(): List<Long> {
        val digits = ArrayList<Long>()

        var n = this

        while (n != 0L) {
            digits.add(n % 10)
            n /= 10
        }

        return digits.reversed()
    }

    fun BigInteger.digits(): List<BigInteger> {
        val digits = ArrayList<BigInteger>()

        var n = this
        val ten = 10.toBigInteger()

        while (n != BigInteger.ZERO) {
            digits.add(n.mod(ten))
            n /= ten
        }

        return digits.reversed()
    }

    private val pandigitalCache = HashMap<Long, Boolean>()

    fun Long.isPandigital(): Boolean =
        pandigitalCache.getOrPut(this) {
            val n = this.toString().toCharArray()
            !n.contains('0') && n.size == n.distinct().size
        }

    private val ConsecutivePandigitalCache = HashMap<Long, Boolean>()

    fun Long.isConsecutivePandigital(): Boolean =
        ConsecutivePandigitalCache.getOrPut(this) {
            val digits = this.toString().toCharArray().map { it.digitToInt() }
            val distinctDigits = digits.toSet()
            val consecutiveDigits = (1..digits.size).toSet()
            return digits.size == distinctDigits.size && distinctDigits.subtract(consecutiveDigits).isEmpty()
        }

    fun triangularNumber(n: Long): Long = n * (n + 1) / 2
    fun squareNumber(n: Long): Long = n * n
    fun pentagonalNumber(n: Long): Long = n * (3 * n - 1) / 2
    fun hexagonalNumber(n: Long): Long = n * (2 * n - 1)
    fun heptagonalNumber(n: Long): Long = n * (5 * n - 3) / 2
    fun octagonalNumber(n: Long): Long = n * (3 * n - 2)

}