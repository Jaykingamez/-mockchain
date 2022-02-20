package selenium;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import beans.Transaction;
import dao.ApplicationDao;
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
	private static List<Transaction> transactionList;
	private static Connection connection;
	private static ApplicationDao dao;
	private String testUsername = "testUsername";
	private String testPassword = "testPassword";

	private String secondUsername = "secondUsername";
	private String secondPassword = "secondPassword";

	private String thirdUsername = "thirdUsername";
	private String thirdPassword = "thirdPassword";

	@Test
	@Order(1)
	/**
	 * register testUsername's account
	 */
	public void registerAccount() {
		// Load website as a new page
		webDriver.navigate().to("http://localhost:8090/mockchain/");

		SeleniumFunctions.register(webDriver, testUsername, testPassword);
	}

	@Test
	@Order(2)
	/**
	 * login testUsername's account
	 */
	public void loginAccount() {
		// Load website as a new page
		webDriver.navigate().to("http://localhost:8090/mockchain/");

		SeleniumFunctions.login(webDriver, testUsername, testPassword);
	}

	@Test
	@Order(3)
	/**
	 * Verify that money cannot be deucted from testUsername's $0 wallet
	 * Give testUsername's wallet $50
	 */
	public void conductPersonalTransaction() {
		// deduct $100 from testUser who has $0
		SeleniumFunctions.personalAmount(webDriver, "-", 100);

		// check if alert text is accurate
		assertEquals("You don't have that much funds in your wallet!", webDriver.switchTo().alert().getText());

		// dismiss alert that pops out
		webDriver.switchTo().alert().accept();
		
		// Give testUser $50
		SeleniumFunctions.personalAmount(webDriver, "+", 50);

		// check if alert text is accurate
		assertEquals("Transaction performed successfully!", webDriver.switchTo().alert().getText());

		// dismiss alert that pops out
		webDriver.switchTo().alert().accept();
	}

	@Test
	@Order(4)
	/**
	 * Approve Transaction to give testUsername $50
	 * Confirm final amount $51, original + mining reward
	 */
	public void approveTransaction() {
		SeleniumFunctions.transactionApproval(webDriver, 1, "0.00", "50.00", "50.00", "approve");

		// navigate to Wallet
		webDriver.findElement(By.linkText("Wallet")).click();

		// confirm they have 50 (transaction) + 1 (mining fee)
		assertEquals("Amount: $51.00",
				webDriver.findElement(By.xpath("//h3[@class='card-subtitle mb-2 text-muted']")).getText());
	}
	
	@Test
	@Order(5)
	/**
	 * Check if Transactions appear in approved list
	 */
	public void checkTransactions() {
		//go to transaction page
		webDriver.findElement(By.linkText("Transaction")).click();
		
		//check if all transaction that are needed to be displayed are displayed
		transactionList = dao.getAllTransactions("approve", 1, connection);
		SeleniumFunctions.checkTransactions(webDriver, transactionList);
	}

	@Test
	@Order(6)
	/**
	 * Register secondUsername's account
	 */
	public void createSecondAccount() {
		// logout
		webDriver.findElement(By.linkText("Logout")).click();

		// create new account
		SeleniumFunctions.register(webDriver, secondUsername, secondPassword);

		// logout
		webDriver.findElement(By.linkText("Logout")).click();
	}

	@Test
	@Order(7)
	/**
	 * Transfer 100 from testUsername's wallet to secondUsername's wallet
	 */
	public void transferAmount() {
		// login to testUsername's account
		SeleniumFunctions.login(webDriver, testUsername, testPassword);

		// transfer money from testUsername's wallet to secondUsername's wallet
		SeleniumFunctions.transferAmount(webDriver, 2, 100);

		// check if alert text is accurate
		assertEquals("You don't have that much funds in your wallet!", webDriver.switchTo().alert().getText());

		// dismiss alert that pops out
		webDriver.switchTo().alert().accept();

	}

	@BeforeAll
	public static void beforeTest() throws Exception {
		// Setting system properties of ChromeDriver
		// to amend directory path base on your local file path
		String chromeDriverDir = "C:\\Program Files\\Google\\Chrome\\chromedriver.exe";

		System.setProperty("webdriver.chrome.driver", chromeDriverDir);

		// initialize FirefoxDriver at the start of test
		webDriver = new ChromeDriver();

		connection = DBConnection.getConnectionToDatabase();
		DBConnection.initializeDatabase(connection); // before initializing
		DBConnection.destroyDatabase(connection); // clear its contents if there are any left over
		
		dao = new ApplicationDao();
		transactionList = new ArrayList<Transaction>();

	}

	@AfterAll
	public static void afterTest() throws Exception {
		// Quit the ChromeDriver and close all associated window at the end of test
		webDriver.quit();

		Connection connection = DBConnection.getConnectionToDatabase();
		DBConnection.destroyDatabase(connection);
	}

}
