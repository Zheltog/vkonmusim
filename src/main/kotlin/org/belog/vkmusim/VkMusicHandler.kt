package org.belog.vkmusim

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.interactions.Actions

class VkMusicHandler(
    webDriver: WebDriver,
    pauseMs: Long = 1000L,
    private val importMode: ImportMode
): VkHandler(webDriver, pauseMs) {

    private var addTrackButtonOrder = 2

    fun openMusicSection() {
        println("Opening music section, make sure you got it showed...")
        val musicButton = element(By.id("l_aud"))
        musicButton.click()
        pause()
    }

    fun searchForTrack(request: String) {
        val searchTextBox = element(By.id("audio_search"))
        searchTextBox.sendKeys(request)
        pause()
        val searchButton = element(By.xpath("//button[@aria-label='Поиск']"))
        searchButton.click()
        pause()

        val globalDiv = element(By.xpath("//div[@data-audio-context='search_global_audios']"))
        val top3Block = globalDiv
            .findElement(By.xpath("//div[@class='ui_gallery__inner_cont']"))
            .findElement(By.xpath("//div[@class='ui_gallery__inner ']"))
            .findElement(By.xpath("//div[@class='ui_gallery_item']"))
            .findElements(By.xpath("//ul[@class='audio_recoms_audios_block_col']"))[0]
        val top3List = top3Block.findElements(By.tagName("li"))

        val top1Song = top3List[0]
        Actions(webDriver)
            .moveToElement(top1Song)
            .perform()
        pause()

        if (importMode == ImportMode.ADVANCED) {
            println("Input the order of the add button (default is 2 of 3):")
            addTrackButtonOrder = readln().toInt()
        }

        val hiddenActions = top1Song.findElement(By.xpath("//div[@class='_audio_row__actions audio_row__actions']"))
        println(hiddenActions)
        val hiddenButtons = hiddenActions.findElements(By.tagName("button"))
        hiddenButtons[addTrackButtonOrder - 1].click()
        readln()
    }
}