package org.belog.vkmusim

import org.belog.vkmusim.scan.AudioFilesScanner
import org.belog.vkmusim.vkimport.VkImporter

class VkonMusIm {

    fun processScan() {
        val scanner = AudioFilesScanner()
        scanner.scan()
    }

    fun processImport() {
        val importer = VkImporter()
        importer.import()
    }
}