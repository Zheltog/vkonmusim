package org.belog.vkmusim

fun main() {
    val vmi = VkonMusIm()
    println("""Hello
        |Select what you want to do or input 0 to exit:
        |1. Scan directory for mp3 audio files
        |2. Import parsed tracks to VK
        |0. Exit
    """.trimMargin())
    val command = readln()
    when(command) {
        "1" -> vmi.processScan()
        "2" -> vmi.processImport()
        "0" -> return
        else -> return
    }
}