package utils

import kotlin.math.absoluteValue

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
        HashMap<Int, Int>().also {
            it[0] = 0
            it[1] = 1
        }
    }

    fun fibonacci(n: Int): Int =
        fibonacciCache.getOrPut(n) {
            fibonacci(n - 1) + fibonacci(n - 2)
        }

    fun fibonacci() =
        generateSequence(0) { n -> n + 1 }.map { n -> fibonacci(n) }

    fun findPrimes() = sequence {
        val primes = ArrayList<Int>()

        fun isPrime(n: Int): Boolean {
            var i = 0

            while (i < primes.size && (primes[i] * primes[i]) <= n) {
                if (n % primes[i] == 0) {
                    return false
                }

                i++
            }

            return true
        }

        var i = 2

        while (true) {
            if (isPrime(i)) {
                primes.add(i)
                yield(i)
            }

            i++
        }
    }

    fun findPrimesUntil(number: Int): List<Int> {
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

}