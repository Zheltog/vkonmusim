package org.belog.vkmusim

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.edge.EdgeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.safari.SafariDriver

class WebDriverInitHandler {

    fun createWebDriver(): WebDriver? {
        println("""Select your browser from list below or input 0 to exit:
            |1. Firefox
            |2. Google Chrome
            |3. Edge
            |4. Safari
            |0. Exit
        """.trimMargin())
        val command = readln()
        try {
            return doCreateWebDriver(command)
        } catch (e: Exception) {
            println("Error: seems like you don't have requested browser installed")
            return null
        }
    }

    private fun doCreateWebDriver(command: String): WebDriver? {
        val driver = when (command) {
            "1" -> FirefoxDriver()
            "2" -> ChromeDriver()
            "3" -> EdgeDriver()
            "4" -> SafariDriver()
            "0" -> null
            else -> null
        }
        driver?.manage()?.window()?.maximize()
        return driver
    }
}