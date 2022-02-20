package selenium;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import beans.Transaction;

@TestMethodOrder(OrderAnnotation.class)
public class SeleniumFunctions {
	/**
	 * Register new account for a new user
	 */
	public static void register(WebDriver webDriver, String username, String password) {
		// check that it is on homepage
		assertEquals(webDriver.getTitle(), "Mockchain");

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

		assertEquals(Integer.toString(amount), webDriver.findElement(By.name("amount")).getAttribute("value"));

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
		
		// Submit form
		webDriver.findElement(By.name("amount")).submit();
	}

	/**
	 * Approve or Reject supposed transaction
	 */
	public static void transactionApproval(WebDriver webDriver, int walletId, String initialAmount,
			String transactedAmount, String newAmount, String approve, List<Transaction> transactionList) {
		// navigate to Approve
		webDriver.findElement(By.linkText("Approve")).click();
		
		checkTransactions(webDriver, transactionList);

		// click approve button
		webDriver.findElement(By.id("approve0")).click();

		// wait for ajax call to finish
		WebDriverWait wait = new WebDriverWait(webDriver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("modalWalletId")));

		// check if modal's values are accurate
		assertEquals(Integer.toString(walletId),
				webDriver.findElement(By.id("modalWalletId")).getAttribute("innerText"));
		assertEquals(initialAmount, webDriver.findElement(By.id("walletIdAmount")).getAttribute("innerText"));
		assertEquals(transactedAmount, webDriver.findElement(By.id("transactedAmount")).getAttribute("innerText"));
		assertEquals(newAmount, webDriver.findElement(By.id("newWalletAmount")).getAttribute("innerText"));

		// approve transaction
		webDriver.findElement(By.cssSelector("button[value='" + approve + "']")).click();
	}

	/**
	 * Check all Transactions
	 */
	public static void checkTransactions(WebDriver webDriver, List<Transaction> transactionList) {
		// get table body to loop through it
		WebElement tableBodyElement = webDriver.findElement(By.tagName("tbody"));

		// get the table rows
		List<WebElement> trCollection = tableBodyElement.findElements(By.tagName("tr"));
		for (int i = 0; i < transactionList.size(); i++) {
			List<WebElement> tdCollection = trCollection.get(i).findElements(By.tagName("td"));
			
			String transactionId = Integer.toString(transactionList.get(i).getTransactionId());
			// return empty string if null or convert int to string if not null
			String previousTransactionId = transactionList.get(i).getPreviousTransactionId() == 
					null ? "" : Integer.toString(transactionList.get(i).getPreviousTransactionId());
			String timeStamp = transactionList.get(i).getTimestamp().toString();
			String walletId = Integer.toString(transactionList.get(i).getWalletId());
			// return empty string if null or convert int to string if not null
			String receiverId = transactionList.get(i).getReceiverId() == 
					null ? "" : Integer.toString(transactionList.get(i).getReceiverId());
			String amount = transactionList.get(i).getAmount() + "0";
			String type = transactionList.get(i).getType();
			// if null return empty string
			// else return "true" if true and "false" is false
			String approve = transactionList.get(i).getApprove() == null ? "" : 
				transactionList.get(i).getApprove() ? "true" : "false";
				
				
			assertEquals(transactionId, tdCollection.get(0).getText());
			assertEquals(previousTransactionId, tdCollection.get(1).getText());
			assertEquals(timeStamp, tdCollection.get(2).getText());
			assertEquals(walletId, tdCollection.get(3).getText());
			assertEquals(receiverId, tdCollection.get(4).getText());
			assertEquals(amount, tdCollection.get(5).getText());
			assertEquals(type, tdCollection.get(6).getText());
			// if on Approval page, check if text equals "Approve"
			// else on transaction page, check the values
			if(tdCollection.get(7).getText().equals("Approve")) {
				assertEquals("Approve", tdCollection.get(7).getText());
			} else {
				assertEquals(approve, tdCollection.get(7).getText());
			}
			
		}
	}
}
