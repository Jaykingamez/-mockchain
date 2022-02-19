package selenium;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import dao.DBConnection;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

@TestMethodOrder(OrderAnnotation.class)
public class LandingTest {
	// declare Selenium WebDriver
	private static WebDriver webDriver;
	private String testUsername = "testUsername";
	private String testPassword = "testPassword";

	@Test
	@Order(1)
	public void registerAccount() {
		// Load website as a new page
		webDriver.navigate().to("http://localhost:8090/mockchain/");

		// check that it is on homepage
		assertEquals(webDriver.getTitle(), "Mockchain");

		System.out.println("title: " + webDriver.getTitle());

		// Open the register form
		webDriver.findElement(By.linkText("Register")).click();

		// check if form is open
		assertTrue(webDriver.findElement(By.tagName("form")).isDisplayed());

		// fill up the form with the necessary details
		webDriver.findElement(By.name("username")).sendKeys(testUsername);
		webDriver.findElement(By.name("password")).sendKeys(testPassword);

		// submit form
		// webDriver.findElement(By.cssSelector("input[type='submit']")).click();
		webDriver.findElement(By.name("password")).submit();

		assertEquals(webDriver.findElement(By.className("card-title")).getText(), testUsername + "'s wallet");
	}

	@Test
	@Order(2)
	public void loginAccount() {
		// Load website as a new page
		webDriver.navigate().to("http://localhost:8090/mockchain/");

		// check that it is on homepage
		assertEquals(webDriver.getTitle(), "Mockchain");

		System.out.println("title: " + webDriver.getTitle());

		// Open the register form
		webDriver.findElement(By.linkText("Login")).click();

		// check if form is open
		assertTrue(webDriver.findElement(By.tagName("form")).isDisplayed());

		// fill up the form with the necessary details
		webDriver.findElement(By.name("username")).sendKeys(testUsername);
		webDriver.findElement(By.name("password")).sendKeys(testPassword);

		// submit form
		// webDriver.findElement(By.cssSelector("input[type='submit']")).click();
		webDriver.findElement(By.name("password")).submit();

		assertEquals(webDriver.findElement(By.className("card-title")).getText(), testUsername + "'s wallet");
	}
	
	@Test
	@Order(3)
	public void conductPersonalTransaction(){
		// open personal transaction form
		webDriver.findElement(By.linkText("Personal")).click();
		
		// check if form is open
		assertTrue(webDriver.findElement(By.tagName("form")).isDisplayed());
		
		// select a positive transaction to oneself
		WebElement selectElement = webDriver.findElement(By.name("operator"));
		Select selectObject = new Select(selectElement);
		selectObject.selectByVisibleText("+");
		
		String selectedOption = selectObject.getFirstSelectedOption().getText();
		
		assertTrue(selectedOption.equals("+"));
		
		//Give oneself $50
		webDriver.findElement(By.name("amount")).sendKeys("50");
		
		assertEquals("50", webDriver.findElement(By.name("amount")).getAttribute("value"));
		
		// Submit form
		webDriver.findElement(By.name("amount")).submit();
		
		// check if alert text is accurate
		assertEquals("Transaction performed successfully!", webDriver.switchTo().alert().getText()); 
		
		//dismiss alert that pops out
		webDriver.switchTo().alert().accept();  
	}
	
	@Test
	@Order(4)
	public void approveTransaction() {
		// navigate to Approve
		webDriver.findElement(By.linkText("Approve")).click();
		
		//click approve button
		webDriver.findElement(By.id("approve0")).click();
		
		// wait for ajax call to finish
		WebDriverWait wait = new WebDriverWait(webDriver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("modalWalletId")));
		
		//check if modal's values are accurate
		assertEquals("1", webDriver.findElement(By.id("modalWalletId")).getAttribute("innerText"));
		assertEquals("0.00", webDriver.findElement(By.id("walletIdAmount")).getAttribute("innerText"));
		assertEquals("50.00", webDriver.findElement(By.id("transactedAmount")).getAttribute("innerText"));
		assertEquals("50.00", webDriver.findElement(By.id("newWalletAmount")).getAttribute("innerText"));
		
		//approve transaction
		webDriver.findElement(By.cssSelector("button[value='approve']")).click();
		
		// navigate to Wallet
		webDriver.findElement(By.linkText("Wallet")).click();
		
		// confirm they have 50 (transaction) + 1 (mining fee)
		assertEquals("Amount: $51.00", webDriver.findElement(By.xpath("//h3[@class='card-subtitle mb-2 text-muted']")).getText());
	}

	@BeforeAll
	public static void beforeTest() throws Exception {
		// Setting system properties of ChromeDriver
		// to amend directory path base on your local file path
		String chromeDriverDir = "C:\\Program Files\\Google\\Chrome\\chromedriver.exe";

		System.setProperty("webdriver.chrome.driver", chromeDriverDir);

		// initialize FirefoxDriver at the start of test
		webDriver = new ChromeDriver();
		
		Connection connection = DBConnection.getConnectionToDatabase();
		DBConnection.initializeDatabase(connection); // before initializing
		DBConnection.destroyDatabase(connection); // clear its contents if there are any left over
		
	}

	@AfterAll
	public static void afterTest() throws Exception {
		// Quit the ChromeDriver and close all associated window at the end of test
		webDriver.quit();
		
		Connection connection = DBConnection.getConnectionToDatabase();
		DBConnection.destroyDatabase(connection);
	}

}
