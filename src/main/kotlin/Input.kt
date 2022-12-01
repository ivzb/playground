import java.io.File

fun readInput(task: String): String {
    val inputStream = File("src/main/input/$task").inputStream()
    return inputStream.bufferedReader().use { it.readText() }.trimIndent()
}