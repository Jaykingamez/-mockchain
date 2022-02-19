package selenium;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import net.bytebuddy.implementation.InvokeDynamic.InvocationProvider.ArgumentProvider;

@TestMethodOrder(OrderAnnotation.class)
public class SeleniumFunctions {
	/**
	 * Register new account for a new user
	 */
	public static void register(WebDriver webDriver, String username, String password) {
		// check that it is on homepage
		assertEquals(webDriver.getTitle(), "Mockchain");

		System.out.println("title: " + webDriver.getTitle());

		// Open the register form
		webDriver.findElement(By.linkText("Register")).click();

		// check if form is open
		assertTrue(webDriver.findElement(By.tagName("form")).isDisplayed());

		// fill up the form with the necessary details
		webDriver.findElement(By.name("username")).sendKeys(username);
		webDriver.findElement(By.name("password")).sendKeys(password);

		// submit form
		// webDriver.findElement(By.cssSelector("input[type='submit']")).click();
		webDriver.findElement(By.name("password")).submit();

		assertEquals(webDriver.findElement(By.className("card-title")).getText(), username + "'s wallet");
	}

	/**
	 * Login the account for a current user
	 */
	public static void login(WebDriver webDriver, String username, String password) {
		// check that it is on homepage
		assertEquals(webDriver.getTitle(), "Mockchain");

		System.out.println("title: " + webDriver.getTitle());

		// Open the login form
		webDriver.findElement(By.linkText("Login")).click();

		// check if form is open
		assertTrue(webDriver.findElement(By.tagName("form")).isDisplayed());

		// fill up the form with the necessary details
		webDriver.findElement(By.name("username")).sendKeys(username);
		webDriver.findElement(By.name("password")).sendKeys(password);

		// submit form
		// webDriver.findElement(By.cssSelector("input[type='submit']")).click();
		webDriver.findElement(By.name("password")).submit();

		assertEquals(webDriver.findElement(By.className("card-title")).getText(), username + "'s wallet");
	}

	public static void personalAmount(WebDriver webDriver, String selectedOperator, int amount) {
		// open personal transaction form
		webDriver.findElement(By.linkText("Personal")).click();

		// check if form is open
		assertTrue(webDriver.findElement(By.tagName("form")).isDisplayed());

		// select the type of transaction
		WebElement selectElement = webDriver.findElement(By.name("operator"));
		Select selectObject = new Select(selectElement);
		selectObject.selectByVisibleText(selectedOperator);

		// get the selected option
		String selectedOption = selectObject.getFirstSelectedOption().getText();

		assertTrue(selectedOption.equals(selectedOperator));

		// Give or Remove money from oneself
		webDriver.findElement(By.name("amount")).sendKeys(Integer.toString(amount));

		assertEquals("50", webDriver.findElement(By.name("amount")).getAttribute("value"));

		// Submit form
		webDriver.findElement(By.name("amount")).submit();
	}

	/**
	 * transfer money to another user
	 */
	public static void transferAmount(WebDriver webDriver, int receiverWalletId, int amount) {
		// open transfer transaction form
		webDriver.findElement(By.linkText("Transfer")).click();

		// check if form is open
		assertTrue(webDriver.findElement(By.tagName("form")).isDisplayed());

		// input secondUsername's walletId
		webDriver.findElement(By.name("receiver")).sendKeys(Integer.toString(receiverWalletId));

		// input an amount more than eligible amount walletId
		webDriver.findElement(By.name("amount")).sendKeys(Integer.toString(amount));
	}

	/**
	 * Approve or Reject supposed transaction
	 */
	public static void transactionApproval(WebDriver webDriver, int walletId, String initialAmount, 
			String transactedAmount, String newAmount, String approve) {
		// navigate to Approve
		webDriver.findElement(By.linkText("Approve")).click();

		// click approve button
		webDriver.findElement(By.id("approve0")).click();

		// wait for ajax call to finish
		WebDriverWait wait = new WebDriverWait(webDriver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("modalWalletId")));

		// check if modal's values are accurate
		assertEquals(Integer.toString(walletId), webDriver.findElement(By.id("modalWalletId")).getAttribute("innerText"));
		assertEquals(initialAmount, webDriver.findElement(By.id("walletIdAmount")).getAttribute("innerText"));
		assertEquals(transactedAmount, webDriver.findElement(By.id("transactedAmount")).getAttribute("innerText"));
		assertEquals(newAmount, webDriver.findElement(By.id("newWalletAmount")).getAttribute("innerText"));

		// approve transaction
		webDriver.findElement(By.cssSelector("button[value='" + approve + "']")).click();
	}
}
