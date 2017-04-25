package com.clickfox.selenium.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.clickfox.selenium.util.WebUtil;

/**
 * @author yared
 *
 */
public class EmailViewPage {
    public String getEmailSubjectText(WebDriver driver) {
        return WebUtil.getElementText(driver, By.cssSelector("h2[class='hP']"));
    }

    public String getEmailBodyText(WebDriver driver) {
        return WebUtil.getElementText(driver, By.cssSelector("div[class='nH aHU'] div[dir='ltr']"));
    }
}
