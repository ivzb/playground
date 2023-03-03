package euler

import EulerTask

object Task92SquareDigitChains : EulerTask("Square digit chains") {

    override fun solution(): Int {
        var count = 0

        val solutions = HashSet<Int>()
        solutions.add(89)

        val ignore = HashSet<Int>()
        ignore.add(1)

        main@for (i in 1..10_000_000) {
            var n = i
            val sequence = ArrayList<Int>()

            while (true) {
                var sum = 0

                while (n != 0) {
                    val lastDigit = n % 10
                    sum += lastDigit * lastDigit
                    n /= 10
                }

                n = sum

                if (solutions.contains(n)) {
                    count++
                    solutions.addAll(sequence)
                    break
                }

                if (ignore.contains(n)) {
                    ignore.addAll(sequence)
                    break
                }

                sequence.add(n)
            }
        }

        return count
    }

}
