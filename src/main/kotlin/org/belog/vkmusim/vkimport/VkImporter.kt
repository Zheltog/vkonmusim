package org.belog.vkmusim.vkimport

import org.belog.vkmusim.common.WorkerWithExtraLogging
import org.openqa.selenium.WebDriver
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths

class VkImporter: WorkerWithExtraLogging() {

    private val importedTracks: MutableList<String> = mutableListOf()
    private lateinit var parsedFilePath: String
    private lateinit var importedFilePath: String
    private lateinit var musicHandler: VkMusicHandler
    private lateinit var mode: ImportMode
    private var pauseMs: Long = 1000L
    private var newImportedFilePath: String? = null

    fun import() {
        try {
            println("""Select mode (quick by default):
                |1. Quick mode
                |2. Advanced mode
            """.trimMargin())
            mode = when(readln()) {
                "1" -> ImportMode.QUICK
                "2" -> ImportMode.ADVANCED
                else -> ImportMode.QUICK
            }

            println("Input path of result file that was previously generated:")
            parsedFilePath = readln()

            println("Input path of imported tracks info file (just ENTER if there is none):")
            importedFilePath = readln()

            if (importedFilePath.isNotEmpty()) {
                loadImportedTracks()
            }

            println("Input path for new imported tracks info file (just ENTER if no need):")
            newImportedFilePath = readln()

            if (mode == ImportMode.ADVANCED) {
                initLogExtraFlag()
            }

            val driverInitHandler = WebDriverInitializer()
            val driver: WebDriver = driverInitHandler.createWebDriver() ?: return

            if (mode == ImportMode.ADVANCED) {
                println("Input pause value (ms) between browser operations (1000 is optimal):")
                pauseMs = readln().toLong()
            }

            val loginHandler = VkLoginHandler(driver, pauseMs)
            loginHandler.login()

            musicHandler = VkMusicHandler(driver, pauseMs, mode)
            musicHandler.openMusicSection()

            println("Starting to add tracks...")
            Files.lines(Paths.get(parsedFilePath)).use { stream ->
                stream.forEach { request: String ->
                    if (!importedTracks.contains(request)) {
                        tryAddTrack(request)
                        importedTracks.add(request)
                    }
                }
            }
            println("Import finished")
        } catch (e: Exception) {
            println("Error: ${e.message}")
        } finally {
            saveImportedTracks()
        }
    }

    private fun tryAddTrack(request: String) {
        tryLogExtra("Trying to add track by search request: $request")
        musicHandler.tryAddTrack(request)
    }

    private fun loadImportedTracks() {
        println("Loading data about imported tracks...")
        Files.lines(Paths.get(importedFilePath)).use { stream ->
            stream.forEach { request: String ->
                importedTracks.add(request)
            }
        }
        println("Data loaded")
    }

    private fun saveImportedTracks() {
        newImportedFilePath ?: return
        println("Saving data about imported tracks...")
        val file = Paths.get(newImportedFilePath!!)
        Files.write(file, importedTracks, StandardCharsets.UTF_8)
        println("Data saved")
    }
}