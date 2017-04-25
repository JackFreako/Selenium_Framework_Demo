package com.clickfox.selenium.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.clickfox.selenium.pageobjects.EmailHomePage;
import com.clickfox.selenium.pageobjects.EmailViewPage;
import com.clickfox.selenium.pageobjects.SignInPage;
import com.clickfox.selenium.priority.Critical;
import com.clickfox.selenium.priority.Major;
import com.clickfox.selenium.util.WebUtil;


/**
 * @author yared
 *
 */
public class GmailSignInTest {

    WebDriver driver;

    @Before
    public void setDriver() {
    	String browserName = "Chrome"; //System.getenv("browser");
        System.setProperty("webdriver.chrome.driver", "/home/yared/Desktop/clickfox/selenium jars/chromedriver");
      	//System.setProperty("webdriver.gecko.driver", "/home/yared/Desktop/clickfox/TestAutomation/geckodriver");
        if (browserName != null && browserName.equalsIgnoreCase("Chrome")) {
            driver = new ChromeDriver();
        } else {
            driver = new FirefoxDriver();
        }
        
        driver.manage().window().maximize();
    }

    //@Ignore //Already working fine...
    @Category({Critical.class})
    @Test
    public void gmailLoginShouldBeSuccessful() {
        //1. Go to Gmail website
        SignInPage signInPage = WebUtil.goToSignInPage(driver);

        //2. Fill in username
        signInPage.fillInUsername(driver, "clickfox.demo@gmail.com");

        //3. Fill in password
        signInPage.fillInPassword(driver, "clickfox12$");

        //4. click sign in
        EmailHomePage emailHomePage = signInPage.clickSignIn(driver);

        //5. verify user did sign in
        Assert.assertTrue("Inbox should exist", emailHomePage.isInboxExist(driver));
        //6. sign out
        signInPage = emailHomePage.signOut(driver);

        //7. verified user did sign out
        Assert.assertTrue("signIn button should exist", signInPage.isSignInButtonExist(driver));
    }

    //@Ignore
    @Category({Major.class})
    @Test
    public void gmailSendAndReceiveEmailTest() {
        // 1. Click sign in
        //1. Go to Gmail website
        SignInPage signInPage = WebUtil.goToSignInPage(driver);
        //2. Fill in username
        signInPage.fillInUsername(driver, "clickfox.demo@gmail.com");

        //3. Fill in password
        signInPage.fillInPassword(driver, "clickfox12$");

        //4. click sign in
        EmailHomePage emailHomePage = signInPage.clickSignIn(driver);
        // 2. Click Compose
        emailHomePage.clickComposeButton(driver);

        // 3. Fill in recipient
        emailHomePage.fillInRecipient(driver, "clickfox.demo@gmail.com");

        // 4. Fill in subject
        final String subject = "Gmail Send Email Test";
        emailHomePage.fillInSubject(driver, subject);

        // 5. Fill in email body
        final String body = "Hello Testers Good Morning";
        emailHomePage.fillInEmailBody(driver, body);

        // 6. Click Send
        emailHomePage.clickSendEmail(driver);

        // 7. Click Inbox again
        emailHomePage.clickFolderByName(driver, "Inbox");

        // 8. Click email
        EmailViewPage emailViewPage = emailHomePage.clickNewEmailWithSubject(driver, subject);

        // 9. Verify the email subject and email body is correct
        String actualSubject = emailViewPage.getEmailSubjectText(driver);

        Assert.assertEquals("Email Subject Text shoudld be the same", subject, actualSubject);

        String actualBody = emailViewPage.getEmailBodyText(driver);

        Assert.assertEquals("Email Body Text shoudld be the same", body, actualBody);
        // 10. Sign out
        emailHomePage.signOut(driver);
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
