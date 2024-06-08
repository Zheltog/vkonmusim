package org.belog.vkmusim

import org.openqa.selenium.WebDriver

class VkLoginHandler(
    webDriver: WebDriver,
    pauseMs: Long = 1000L
): VkHandler(webDriver, pauseMs) {

    private val baseUrl = "https://vk.com/"

    fun login() {
        println("Opening VK base page...")
        get(baseUrl)
        pause()
        if (WebDriverInitHandler.currentMode == DriverMode.SYNTHETIC) {
            println("Scan QR code to authorize, then perform any input here")
            readln()
        }
    }
}