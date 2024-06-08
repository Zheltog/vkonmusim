package org.belog.vkmusim

open class WorkerWithExtraLogging {

    private var logExtra: Boolean = false

    protected fun initLogExtraFlag() {
        println("""Do you want extra log to be printed?
            |1. Yes
            |2. No
        """.trimMargin())
        val command = readln()
        logExtra = when(command) {
            "1" -> true
            "2" -> false
            else -> false
        }
    }

    protected fun tryLogExtra(message: String) {
        if (logExtra) {
            println(message)
        }
    }
}