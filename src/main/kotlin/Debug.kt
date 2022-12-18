object Debug {

    private const val WITH_DEBUG = true

    fun println(message: Any) {
        if (WITH_DEBUG) kotlin.io.println(message)
    }

    fun println() {
        if (WITH_DEBUG) kotlin.io.println()
    }

    fun print(message: Any) {
        if (WITH_DEBUG) kotlin.io.print(message)
    }
}