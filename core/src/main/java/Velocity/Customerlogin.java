package Velocity;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
public class Customerlogin extends bagissto.core.testCases{
	
	protected static WebDriver driver= new ChromeDriver();
	 
	@BeforeClass
	public static void configureBrowser() {
		configBrowserExe("/chromedriver"); 		
	}
	
	@Parameters({ "URL" })
	@Test(priority = 0)
	public void openBrowser(String URL) {
		openBrowser(driver,URL);	// URL from testNg.xml file
	}
	
	 @Test(dependsOnMethods={"openBrowser", "waite"})  // First run the wait() method then this
	public static void Login() throws InterruptedException {
			
		String[] loginCredentials= {"tom@example.com","tom123"};		
		String[] loginLocators = {"div[class*='welcome-content']","//input[@name='email']","//input[@name='password']"};	
		customerLogin(driver,loginLocators,loginCredentials); // customer Login	
		
	}
	
	@AfterMethod
	public static void testMethod(){
		System.out.println("I will run after every method");
	}
	
	@Test
	public static void waite() throws InterruptedException{
		Thread.sleep(2000);
	}

}
