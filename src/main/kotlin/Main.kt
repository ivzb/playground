import _2022.Task06

fun main() {
    val task = Task06

    task.partOne().let {
        val result = if (it.toString() == "1779") "SUCCESS" else "FAILURE"
        println("$result - $it")
    }

    task.partTwo().let {
        val result = if (it.toString() == "2635") "SUCCESS" else "FAILURE"
        println("$result - $it")
    }
}