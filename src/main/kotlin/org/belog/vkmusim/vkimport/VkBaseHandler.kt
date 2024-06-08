package org.belog.vkmusim.vkimport

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions

open class VkBaseHandler(
    private val webDriver: WebDriver,
    private val pauseMs: Long
) {

    protected fun pause(ms: Long = pauseMs) {
        try {
            Thread.sleep(ms)
        } catch (e: InterruptedException) {
            // ignored
        }
    }

    protected fun get(url: String) {
        webDriver.get(url)
    }

    protected fun element(by: By): WebElement {
        return webDriver.findElement(by)
    }

    protected fun mouseOn(element: WebElement) {
        Actions(webDriver)
            .moveToElement(element)
            .perform()
    }
}