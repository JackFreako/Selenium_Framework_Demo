/**
 * 
 */
package com.clickfox.selenium.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
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
public class GmailSignInSignOutTest {

	private WebDriver driver;
	
	@Before
	public void setUp(){
		
		String browserName = "firefox"; //System.getenv("browser");
        
        if (browserName != null && browserName.equalsIgnoreCase("Chrome")) {
        	System.setProperty("webdriver.chrome.driver", "/home/yared/Desktop/clickfox/selenium jars/chromedriver");
            driver = new ChromeDriver();
        } else {
        	System.setProperty("webdriver.gecko.driver", "/home/yared/Desktop/clickfox/TestAutomation/geckodriver");
            driver = new FirefoxDriver();
        }
		
	}
	
	/**
	 * 1)
	 * 2)
	 * 3)
	 * 4)
	 * 5)
	 */
	@Test
	public void test_gmailSignInOut(){
		
		
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
	
	@After
	public void tearDown(){
		driver.quit();
	}
}
