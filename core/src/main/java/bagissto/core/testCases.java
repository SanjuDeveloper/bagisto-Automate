package bagissto.core;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;


public class testCases extends Functions {
	
	public static void openBrowser(WebDriver driver, String URL) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.manage().window().maximize(); 
		driver.get(URL);
	}
	
	public static void addToCart(WebDriver driver, String[] cartProducts,String flashMessage) {		
		int j=0; 
		scrollDown(driver);
		List<WebElement> products = driver.findElements(By.cssSelector("div.product-name")); // products array	
		for (int i = 0; i<products.size(); i++) {				
			String formattedName =products.get(i).getText();	// get productName from productCard	
			List<String> itemForAddToCart = Arrays.asList(cartProducts); //Convert stringArray to arrayList	
			if (itemForAddToCart.contains(formattedName)) {
				j++; System.out.println(formattedName);
				//click on add to cart button				
				driver.findElements(By.xpath("//div[@class='add-to-cart-btn pl0']/form/button")).get(i).click();
				driver.findElement(By.xpath(flashMessage)).click();	//close flash message
				if(j == cartProducts.length) {
					break;
				}
			}
		}		
	}
	
	public static void customerLogin(WebDriver driver,String Locator,String[] loginCredentials) {
		scrollUp(driver);
		driver.findElement(By.cssSelector(Locator)).click();
		driver.findElement(By.xpath("//a[@class='theme-btn fs14 fw6'][1]")).click();
		WebDriverWait w =new WebDriverWait(driver,Duration.ofSeconds(10)); // Object of WebDriverWaite for explicit waite.
		w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='email']")));		
		driver.findElement(By.xpath("//input[@name='email']")).sendKeys(loginCredentials[0]);		
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys(loginCredentials[1]);
		driver.findElement(By.cssSelector("input[type='submit']")).click();		
	}
	
	public static void veryfyMiniCartPrice(WebDriver driver,String[] locator) {		
		driver.findElement(By.xpath(locator[0])).click();
		List<WebElement> miniCartmount = driver.findElements(By.xpath(locator[1]));		
		int actualAmount=0;
		for(int i=0; i<miniCartmount.size(); i++) {	
			
			String finalAmount =	removeComma(miniCartmount.get(i).getText().substring(1));// remove , from price
			//convert string to double
			double itemAmount = Double.parseDouble(finalAmount);
			// convert into int
			int intsTotalAmount = (int)itemAmount;				 
			System.out.println(actualAmount+ "+" + intsTotalAmount);			 
			actualAmount =actualAmount + intsTotalAmount;
			
		}	
		
		String finalGrandAmount  = removeComma(driver.findElement(By.cssSelector(locator[2])).getText().substring(1));		
		System.out.println(finalGrandAmount);		 
		double total = Double.parseDouble(finalGrandAmount);
		int grandTotalAmount = (int)total;	 // convert into int		 
		Assert.assertEquals(grandTotalAmount,actualAmount); // check if(givenAmout==actualAmount);
		System.out.println("Test case is pass");	        
	}
	
	public static void VerifyShoppingCart(WebDriver driver,String[] productsForUpdate) throws InterruptedException {
		
		driver.findElement(By.xpath("//div[@id='cart-modal-content']/div/a")).click();
		updateCart(driver,3,productsForUpdate);
		System.out.println("Cart updated successfully");
	}
	
	public static void verifyOrdersFilter(WebDriver driver,String FlashMessage) throws InterruptedException {
		//driver.findElement(By.xpath(FlashMessage)).click();	//close flash message		
		driver.findElement(By.cssSelector("div[class*='welcome-content']")).click();
		driver.findElement(By.xpath("//div[@class='dropdown-container']/ul/li[2]")).click();
		WebElement searchBox = driver.findElement(By.cssSelector("#search-field"));
		searchBox.click();
		searchBox.sendKeys("300");
		searchBox.sendKeys(Keys.ENTER); //Press Enter keys*/		
		
		// Get all the records after search 300  as A
		Thread.sleep(2000);
		List<WebElement> orders = driver.findElements(By.xpath("//tr/td[3]"));
		
		System.out.println("Actual orders after filter="+orders.size());
		// Collect all data which contain 300 AS B
		// JAVA Stream with Anonymous Classes
		List<Object> filteredOrder = orders.stream().filter(new Predicate<WebElement>() {
					public boolean test(WebElement order) {
						return order.getText().contains("300");
					}
				}).collect(Collectors.toList());
		
		System.out.println("Orders size  Found after filter="+filteredOrder.size());
		// Check A== B 
		Assert.assertEquals(orders.size(), filteredOrder.size()); 
		
	}
}
