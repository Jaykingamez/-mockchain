/**
 * 
 */
package mockchain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import beans.Wallet;

/**
 * @author jayki
 *
 */
class WalletTest {
	private int walletId;
	private int userId;
	private double amount;

	private Wallet testWallet;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		walletId = 1;
		userId = 1;
		amount = 0;

		testWallet = new Wallet(walletId, userId, amount);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link beans.Wallet#Wallet(int, int, double)}.
	 */
	@Test
	void testWallet() {
		assertEquals(testWallet.getWalletId(), walletId);
		assertEquals(testWallet.getUserId(), userId);
		assertEquals(testWallet.getAmount(), amount);
	}

	/**
	 * Test method for {@link beans.Wallet#getWalletId()}.
	 */
	@Test
	void testGetWalletId() {
		assertEquals(testWallet.getWalletId(), walletId);
	}

	/**
	 * Test method for {@link beans.Wallet#setWalletId(int)}.
	 */
	@Test
	void testSetWalletId() {
		testWallet.setWalletId(walletId + 1);
		assertEquals(testWallet.getWalletId(), walletId + 1);
	}

	/**
	 * Test method for {@link beans.Wallet#getUserId()}.
	 */
	@Test
	void testGetUserId() {
		assertEquals(testWallet.getUserId(), userId);
	}

	/**
	 * Test method for {@link beans.Wallet#setUserId(int)}.
	 */
	@Test
	void testSetUserId() {
		testWallet.setUserId(userId + 1);
		assertEquals(testWallet.getUserId(), userId + 1);
	}

	/**
	 * Test method for {@link beans.Wallet#getAmount()}.
	 */
	@Test
	void testGetAmount() {
		assertEquals(testWallet.getAmount(), amount);
	}

	/**
	 * Test method for {@link beans.Wallet#setAmount(double)}.
	 */
	@Test
	void testSetAmount() {
		testWallet.setAmount(amount + 1);
		assertEquals(testWallet.getAmount(), amount + 1);
	}

}
