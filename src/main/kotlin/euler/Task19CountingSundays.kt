package euler

import EulerTask

object Task19CountingSundays : EulerTask {

    override val name: String = "Counting Sundays"

    override fun solution(): Int {
        var day = 2 // first day of 1901 is Tuesday
        var solutions = 0

        for (year in 1901..2000) {
            for (month in 1..12) {
                day += daysPerMonth(month, year)

                if (day % 7 == 0) {
                    solutions++
                }
            }
        }

        return solutions
    }

    private fun daysPerMonth(month: Int, year: Int): Int =
        arrayOf(31, if (year % 4 == 0) 29 else 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)[month-1]
}
