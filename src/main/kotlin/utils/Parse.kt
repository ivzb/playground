package utils

object Parse {

    fun String.firstInt(delimiter: Char = ' '): Int =
        this.split(delimiter).firstNotNullOf { it.toIntOrNull() }
}