package org.belog.vkmusim

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import java.util.concurrent.TimeUnit

fun main() {
    val driver: WebDriver = FirefoxDriver()
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
    val loginHandler = VkLoginHandler(driver, 3000L)
    loginHandler.login()
}