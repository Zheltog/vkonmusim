package org.belog.vkmusim

import org.openqa.selenium.WebDriver

fun main() {
    try {
        val driverInitHandler = WebDriverInitHandler()
        val driver: WebDriver = driverInitHandler.createWebDriver() ?: return

        val loginHandler = VkLoginHandler(driver)
        loginHandler.login()

        val musicHandler = VkMusicHandler(driver)
        musicHandler.openMusicSection()
    } catch (e: Exception) {
        println("Error: ${e.message}")
    }
}