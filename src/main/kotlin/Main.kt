import _2022.Task07

fun main() {
    val task = Task07

    task.partA().let {
        val result = if (it.toString() == "2104783") "SUCCESS" else "FAILURE"
        println("$result - $it")
    }

    task.partB().let {
        val result = if (it.toString() == "5883165") "SUCCESS" else "FAILURE"
        println("$result - $it")
    }
}