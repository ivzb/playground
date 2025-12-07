import advent_of_code._2025.Task07
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

fun main() {
    val task = Task07

    measure {
        task.partA()
    }

    measure {
        task.partB()
    }
}

@OptIn(ExperimentalTime::class)
private fun measure(task: () -> Any) {
    val (value, duration) = measureTimedValue {
        task()
    }

    println("took ${duration.inWholeMilliseconds}ms and got ${value}.")
}