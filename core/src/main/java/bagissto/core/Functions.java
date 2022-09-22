package bagissto.core;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class Functions {

	public static String removeComma(String str) {		
		return str.replaceAll("[^a-zA-Z0-9.]", ""); // remove , from price				 
	}
	
	public static void scrollDown(WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		   js.executeScript("window.scrollBy(0,440)");
	}
	
	public static void scrollUp(WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		   js.executeScript("window.scrollBy(0,-900)");
	}
	
	public static void configBrowserExe(String fileName) {
		System.getProperty("webdriver.chrome.driver", "user.dir"+ fileName);
		
	}
	
	public static void updateCart(WebDriver driver, int productQuantity, String[] cartLocator) {
		for(int i=0; i<productQuantity; i++) {
			driver.findElement(By.cssSelector(cartLocator[0])).click(); // Click on + icon
		}
		
		driver.findElement(By.xpath(cartLocator[1])).click(); // Click on Update Cart button
	}
}
