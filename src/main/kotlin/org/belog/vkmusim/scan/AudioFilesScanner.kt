package org.belog.vkmusim.scan

import org.apache.tika.parser.ParseContext
import org.apache.tika.parser.Parser
import org.apache.tika.parser.mp3.Mp3Parser
import org.belog.vkmusim.common.WorkerWithExtraLogging
import org.xml.sax.ContentHandler
import org.xml.sax.helpers.DefaultHandler
import java.io.File
import java.io.FileInputStream
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths

class AudioFilesScanner: WorkerWithExtraLogging() {

    private val parsedTracks: MutableList<TrackInfo> = mutableListOf()
    private lateinit var resultFilePath: String

    fun scan() {
        println("Input directory path you want to scan for mp3 files:")
        val pathString = readln()
        val file = File(pathString)
        if (!file.isDirectory) {
            println("Error: no such a directory for path you provided")
            return
        }

        println("Input path for result file that will be generated:")
        resultFilePath = readln()

        initLogExtraFlag()

        println("Started scanning...")
        scanDir(file)
        println("Finished scanning - parsed ${parsedTracks.size} tracks")

        saveParsed()
        println("Finished")
    }

    private fun scanDir(dir: File) {
        println("Scanning directory: ${dir.name}")
        val elements = dir.listFiles() ?: return
        println("Directory ${dir.name} got ${elements.size} sub-elements")
        elements.forEach { element ->
            if (element.isDirectory) {
                scanDir(element)
            } else {
                processFile(element)
            }
        }
    }

    private fun processFile(file: File) {
        tryLogExtra("Processing file: ${file.name}")
        if (!file.name.endsWith(".mp3", true)) {
            tryLogExtra("${file.name} is not a mp3 file, skipping")
            return
        }
        try {
            FileInputStream(file).use {
                val handler: ContentHandler = DefaultHandler()
                val metadata = org.apache.tika.metadata.Metadata()
                val parser: Parser = Mp3Parser()
                val parseCtx = ParseContext()
                parser.parse(it, handler, metadata, parseCtx)
                val trackInfo = trackInfoBy(file.name, metadata)
                tryLogExtra("Parsed track info: $trackInfo")
                parsedTracks.add(trackInfo)
            }
        } catch (e: Exception) {
            println("Error: could not process file ${file.name}")
        }
    }

    private fun trackInfoBy(fileName: String, metadata: org.apache.tika.metadata.Metadata): TrackInfo {
        var artist: String? = null
        var title: String? = null

        metadata.names().forEach { name ->
            val value = metadata[name]
            if ((name.contains("artist", true)
                || name.contains("creator", true))
                && value != null) {
                artist = value
            } else if (name.contains("title", true) && value != null) {
                title = value
            }
        }

        return TrackInfo(artist = artist, title = title, raw = fileName)
    }

    private fun saveParsed() {
        println("Saving parsed data to file: $resultFilePath...")
        val lines: List<String> = parsedTracks.map { info ->
            info.toFormatted()
        }
        val file = Paths.get(resultFilePath)
        Files.write(file, lines, StandardCharsets.UTF_8)
    }
}