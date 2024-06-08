package org.belog.vkmusim

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

open class VkHandler(
    protected val webDriver: WebDriver,
    private val pauseMs: Long
) {

    fun pause() {
        try {
            Thread.sleep(pauseMs)
        } catch (e: InterruptedException) {
            // ignored
        }
    }

    fun get(url: String) {
        webDriver.get(url)
    }

    protected fun element(by: By): WebElement {
        return webDriver.findElement(by)
    }
}