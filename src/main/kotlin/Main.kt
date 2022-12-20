import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

fun main() {
    val task = _2022.Task20

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
    println("took ${duration.inWholeSeconds} seconds and got ${value}.")
}