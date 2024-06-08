package org.belog.vkmusim

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.edge.EdgeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.safari.SafariDriver


class WebDriverInitHandler {

    companion object {
        var currentMode: DriverMode = DriverMode.SYNTHETIC
    }

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
            "2" -> doCreateChromeDriver()
            "3" -> EdgeDriver()
            "4" -> SafariDriver()
            "0" -> null
            else -> null
        }
        driver?.manage()?.window()?.maximize()
        return driver
    }

    private fun doCreateChromeDriver(): WebDriver? {
        val options = ChromeOptions()

        println("""Use actual browser instead of creating synthetic one?
            |1. Yes
            |2. No
            |3. Want to hack me???
        """.trimMargin())
        val command = readln()
        when(command) {
            "1" -> {
                println("Input your Windows user name:")
                val userName = readln()
                options.addArguments("user-data-dir=C:\\Users\\$userName\\AppData\\Local\\Google\\Chrome\\User Data")
                options.addArguments("disable-infobars")
                options.addArguments("--start-maximized")
                currentMode = DriverMode.ACTUAL
                return ChromeDriver(options)
            }
            "2" -> return ChromeDriver()
            "3" -> {
                println("Yes, so what?")
                return null
            }
            else -> return null
        }
    }
}