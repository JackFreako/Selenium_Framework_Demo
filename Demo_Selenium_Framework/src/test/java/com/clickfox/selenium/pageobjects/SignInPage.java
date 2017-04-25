package com.clickfox.selenium.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.clickfox.selenium.util.WebUtil;

/**
 * @author yared
 *
 */
public class SignInPage {

    public void fillInUsername(WebDriver driver, String s) {
        WebUtil.clearAndSendKeys(driver, By.id("Email"), s);
    }

    public void fillInPassword(WebDriver driver, String s) {
        if(WebUtil.isElementDisplayed(driver, By.id("next"))){
            WebUtil.click(driver, By.id("next"));
        }
        WebUtil.waitForElementVisible(driver, By.id("Passwd"));
        WebUtil.clearAndSendKeys(driver, By.id("Passwd"), s);
    }

    public EmailHomePage clickSignIn(WebDriver driver) {
        WebUtil.click(driver, By.id("signIn"));

        WebUtil.waitForElementVisible(driver, By.partialLinkText("Inbox"));

        return PageFactory.initElements(driver, EmailHomePage.class);
    }

    public boolean isSignInButtonExist(WebDriver driver) {
        return WebUtil.isElementExist(driver, By.id("signIn"));
    }
}
