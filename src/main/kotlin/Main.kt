import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

fun main() {
    val task = euler.Task34DigitFactorials

    measure {
        println(task.name)
        task.solution()
    }
}

@OptIn(ExperimentalTime::class)
private fun measure(task: () -> Any) {
    val (value, duration) = measureTimedValue {
        task()
    }
    println("took ${duration.inWholeMilliseconds}ms and got ${value}.")
}