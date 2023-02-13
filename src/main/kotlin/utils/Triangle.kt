package utils

data class Triangle(val a: Int, val b: Int, val c: Int) {

    val perimeter: Int by lazy { a + b + c }

    operator fun times(n: Int): Triangle = Triangle(a * n, b * n, c * n)

    override fun toString(): String = "$a, $b, $c = $perimeter"
}