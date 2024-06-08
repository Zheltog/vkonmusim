package org.belog.vkmusim

import org.openqa.selenium.WebDriver
import java.nio.file.Files
import java.nio.file.Paths

class VkImporter: WorkerWithExtraLogging() {

    private lateinit var parsedFilePath: String
    private lateinit var musicHandler: VkMusicHandler
    private var pauseMs: Long = 1000L

    fun import() {
        try {
            println("Input path for result file that will be generated:")
            parsedFilePath = readln()

            initLogExtraFlag()

            val driverInitHandler = WebDriverInitHandler()
            val driver: WebDriver = driverInitHandler.createWebDriver() ?: return

            println("Input pause value (ms) between browser operations (1000, for example):")
            pauseMs = readln().toLong()

            val loginHandler = VkLoginHandler(driver, pauseMs)
            loginHandler.login()

            musicHandler = VkMusicHandler(driver, pauseMs)
            musicHandler.openMusicSection()

            println("Starting to add tracks...")
            Files.lines(Paths.get(parsedFilePath)).use { stream ->
                stream.forEach { line: String ->
                    tryAddTrack(line)
                }
            }
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }

    private fun tryAddTrack(request: String) {
        tryLogExtra("Trying to add track by search request: $request")
        musicHandler.searchForTrack(request)
    }
}