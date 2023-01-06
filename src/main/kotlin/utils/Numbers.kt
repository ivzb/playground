package utils

import kotlin.math.pow

fun Int.pow(n: Int): Int = this.toDouble().pow(n).toInt()
fun Long.pow(n: Int): Long = this.toDouble().pow(n).toLong()