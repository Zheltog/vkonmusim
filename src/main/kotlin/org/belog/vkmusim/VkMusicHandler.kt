package org.belog.vkmusim

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

class VkMusicHandler(
    webDriver: WebDriver,
    pauseMs: Long = 1000L
): VkHandler(webDriver, pauseMs) {

    private val musicButtonId = "l_aud"
    private val searchTextBoxId = "audio_search"
    private val searchButtonXpath = "//button[aria-label='Поиск']"

    fun openMusicSection() {
        println("Opening music section, make sure you got it showed...")
        val musicButton = element(By.id(musicButtonId))
        musicButton.click()
        pause()
    }

    fun searchForTrack(request: String) {
        val searchTextBox = element(By.id(searchTextBoxId))
        searchTextBox.sendKeys(request)
        pause()
//        val searchButton = element(By.xpath(searchButtonXpath))
//        searchButton.click()
//        pause()
    }
}