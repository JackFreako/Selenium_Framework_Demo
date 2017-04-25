/**
 * 
 */
package com.clickfox.selenium.test;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.clickfox.selenium.test.util.AppConstants;


/**
 * @author yared
 *
 */
@Ignore
public class GmailSendAndReceiveEmailTest {

	private WebDriver driver;
	
	@Before
	public void setUp(){
		
		String browserName = "Chrome"; //System.getenv("browser");
        
        if (browserName != null && browserName.equalsIgnoreCase("Chrome")) {
        	System.setProperty("webdriver.chrome.driver", "/home/yared/Desktop/clickfox/selenium jars/chromedriver");
            driver = new ChromeDriver();
        } else {
        	System.setProperty("webdriver.gecko.driver", "/home/yared/Desktop/clickfox/TestAutomation/geckodriver");
            driver = new FirefoxDriver();
        }
        
        driver.manage().window().maximize();
		
	}
	
	/**
	 * Send and Receive Email
	 * 
	 * 1) Sign In into gmail
	 * 2) Click Compose, fill in receipient, fill in subject and fill in email body
	 * 3) Hit Send
	 * 4) Check Inbox
	 * 5) Check the sent email (Verify that the subject and body match with the Sent email)
	 * 6) Sign out of gmail
	 */
	@Test
	public void test_sendAndReceiveEmail(){
				
		//Navigate to the Application under test
		driver.get(AppConstants.AUT_URL);

		// fill in the userName
		WebElement emailTextBox = driver.findElement(By.id("Email"));
		emailTextBox.clear();
		emailTextBox.sendKeys(AppConstants.gmailUserName);
		

		//If a "Next" button exists, click on that 
		
		boolean nextButtonExists = true;
		
		if(nextButtonExists){
			WebElement nextButton = driver.findElement(By.id("next"));
			nextButton.click();
		}
		
		//Synchronization for the "Password" textbox
		WebDriverWait wait = new WebDriverWait(driver, AppConstants.WAIT_TIME_OUT);
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Passwd")));
	    
		//Fill in the password
		WebElement passWordTextBox = driver.findElement(By.id("Passwd"));
		passWordTextBox.clear();
		passWordTextBox.sendKeys(AppConstants.gmailUserPassword);
		
		
		//Click the signIn Button
		WebElement signInButton = driver.findElement(By.id("signIn"));
		signInButton.click();
				
		
		//TODO : Send, receive and verify a sample email
		
		//click the compose button
		WebElement composeButton = driver.findElement(By.cssSelector("div[role='button'][gh='cm']"));
		composeButton.click();
		
		//Fill-out the receipent Email textarea and Synchronization
		WebDriverWait receipientEmailWait = new WebDriverWait(driver, AppConstants.WAIT_TIME_OUT);
		receipientEmailWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("textarea[name='to']")));
	    
		WebElement receipientEmail = driver.findElement(By.cssSelector("textarea[name='to']"));
		receipientEmail.clear();
		receipientEmail.sendKeys(AppConstants.gmailUserName);
		
		
		//Fill-out the subject textarea
		String emailSubject = "Clickfox : Selenimu Framework Design and Implementation Demo";
		WebElement emailSubjectTextArea = driver.findElement(By.cssSelector("input[name='subjectbox']"));
		emailSubjectTextArea.clear();
		emailSubjectTextArea.sendKeys(emailSubject);
		
		//Fill-out the subject textbox
		String emailBody= "Clickfox : Selenimu Framework Design and Implementation Demo";
		WebElement emailBodyTextArea = driver.findElement(By.cssSelector("div[aria-label='Message Body']"));
		emailBodyTextArea.clear();
		emailBodyTextArea.sendKeys(emailBody);
		
		//click the send button
		WebElement sendButton = driver.findElement(By.cssSelector("div[aria-label*='Send']"));
		sendButton.click();
		
		//Click on the "Inbox" folder
		WebDriverWait inboxWait = new WebDriverWait(driver, AppConstants.WAIT_TIME_OUT);
		inboxWait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Inbox")));
		retryingFindClick(driver, By.partialLinkText("Inbox"));
		
		
		//Click on a particular email
		clickNewEmailWithSubject(driver,emailSubject);
		
		
		//Retrieve the Email Subject and Assert that it is equal to the sent email
		WebDriverWait subjectAreaWait = new WebDriverWait(driver, AppConstants.WAIT_TIME_OUT);
		subjectAreaWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h2[class='hP']")));
		WebElement subjectArea = driver.findElement(By.cssSelector("h2[class='hP']"));
		String actualSubject = subjectArea.getText();
		Assert.assertEquals("Email Subject Text shoudld be the same", emailSubject, actualSubject);
		
		
		//Retrieve the Email Body and Assert that it is equal to the sent email's body
		WebDriverWait emailBodyWait = new WebDriverWait(driver, AppConstants.WAIT_TIME_OUT);
		emailBodyWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='nH aHU'] div[dir='ltr']")));
		WebElement emailBodyElement = driver.findElement(By.cssSelector("div[class='nH aHU'] div[dir='ltr']"));
		String actualEmailBody = emailBodyElement.getText();
		Assert.assertEquals("Email Body Text shoudld be the same", emailBody, actualEmailBody);
		
		
		//Click on the profile icon
		WebElement profileIcon = driver.findElement(By.cssSelector("span[class='gb_9a gbii']"));		
		profileIcon.click();
		
		
		//Click the Signout button
		WebElement signOutButton = driver.findElement(By.id("gb_71"));
		signOutButton.click();
		
		//Assertion : check the presence of the signIn button
		WebElement signInButtonAfterSignOut = driver.findElement(By.id("signIn"));
		Assert.assertTrue(signInButtonAfterSignOut.isDisplayed());
		
	}
	
	
	
	public void clickNewEmailWithSubject(WebDriver driver, String subject) {
        
		WebDriverWait wait = new WebDriverWait(driver, AppConstants.WAIT_TIME_OUT);
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='y6'] span[id] b")));
		

        boolean isEmailFound = false;
        // get all the new emails
        List<WebElement> emails = driver.findElements(By.cssSelector("div[class='y6'] span[id] b"));

        for (WebElement email : emails) {
            // if email found ,click it and stop loop
            if (email.getText().equals(subject)) {
            	email.click();
                //WebUtil.click(driver, email);
                isEmailFound = true;
                break;
            }
        }

        // if email cannot be found, refresh the page and search again
        if(!isEmailFound) {
            driver.navigate().refresh();
            tryDismissAlert(driver);
            clickNewEmailWithSubject(driver, subject);
        }

        
    }
	
	
	public static String tryDismissAlert(WebDriver driver) {
        String alertText = "";
        try {
            Alert alert = driver.switchTo().alert();
            alertText = alert.getText();
            alert.dismiss();

        } catch (NoAlertPresentException nape) {
            // nothing to do, because we only want to close it when pop up
        }
        return alertText;
    }
	
	 /**
     *  Helper method to handle StaleElementException
     *  Note : http://darrellgrainger.blogspot.com/2012/06/staleelementexception.html
     * @param by
     * @param driver
     * @return
     */
    
    public static boolean retryingFindClick(WebDriver driver,By by) {
        boolean result = false;
        int attempts = 0;
        while(attempts < 2) {
            try {
                driver.findElement(by).click();
                result = true;
                break;
            } catch(StaleElementReferenceException e) {
            }
            attempts++;
        }
        return result;
    }
	
	@After
	public void tearDown(){
		driver.quit();
	}
}
