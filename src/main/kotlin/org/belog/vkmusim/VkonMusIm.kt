package org.belog.vkmusim

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