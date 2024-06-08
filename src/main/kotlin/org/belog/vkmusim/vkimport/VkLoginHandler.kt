package org.belog.vkmusim.vkimport

import org.openqa.selenium.WebDriver

class VkLoginHandler(
    webDriver: WebDriver,
    pauseMs: Long = 1000L
): VkBaseHandler(webDriver, pauseMs) {

    private val baseUrl = "https://vk.com/"

    fun login() {
        println("Opening VK base page...")
        get(baseUrl)
        pause()
        if (WebDriverInitializer.currentMode == DriverMode.SYNTHETIC) {
            println("Scan QR code to authorize, then perform any input here:")
            readln()
        }
    }
}