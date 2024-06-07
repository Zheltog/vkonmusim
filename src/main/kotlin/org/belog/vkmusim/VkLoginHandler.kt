package org.belog.vkmusim

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

class VkLoginHandler(
    webDriver: WebDriver,
    pauseMs: Long
): VkHandler(webDriver, pauseMs) {

    private val baseUrl = "https://vk.com/"
    private val loginBoxId = "index_email"
    private val submitButtonClass = "FlatButton FlatButton--primary FlatButton--size-l FlatButton--wide VkIdForm__button VkIdForm__signInButton"
    private val submitButtonXpath = "//button[@type='submit']"
    private val anotherWayButtonXpath = "//button[@data-test-id='other-verification-methods']"
    private val bySmsButtonXpath = "//button[@data-test-id='verificationMethod_sms']"

    private var phoneNumber: String? = null
    private var password: String? = null

    fun login() {
        webDriver.get(baseUrl)
        val loginBox = element(By.id(loginBoxId))
        println("[VkLoginHandler] Input your phone number")
        phoneNumber = readln()
        loginBox.sendKeys(phoneNumber)
        pause()
        val submitButton = element(By.xpath(submitButtonXpath))
        submitButton.click()
        pause()
        val anotherWayButton = element(By.xpath(anotherWayButtonXpath))
        anotherWayButton.click()
        pause()
        val bySmsButton = element(By.xpath(bySmsButtonXpath))
        bySmsButton.click()
        pause()
    }
}