/**
 * 
 */
package dao;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import beans.Wallet;
import dao.ApplicationDao;
import dao.TestDBConnection;

/**
 * @author jayki
 *
 */
class WalletDaoTest {
	private int walletId;
	private int userId;
	private double amount;

	private static Connection connection;
	private ApplicationDao dao;
	private Wallet testWallet;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		connection = TestDBConnection.getConnectionToDatabase();
		connection.createStatement().execute("CREATE SCHEMA mockchain ;");
		connection.createStatement()
				.execute("CREATE TABLE `wallet` (\r\n" + "  `walletId` INT NOT NULL AUTO_INCREMENT,\r\n"
						+ "  `userId` INT UNSIGNED NOT NULL,\r\n" + "  `amount` DECIMAL(65,2) NOT NULL,\r\n"
						+ "  UNIQUE INDEX `userId_UNIQUE` (`userId` ASC),\r\n" + "  PRIMARY KEY (`walletId`),\r\n"
						+ "  UNIQUE INDEX `walletId_UNIQUE` (`walletId` ASC));\r\n");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
		connection.createStatement().execute("DROP ALL OBJECTS DELETE FILES");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		walletId = 1;
		userId = 1;
		amount = 0;

		testWallet = new Wallet(walletId, userId, amount);

		dao = new ApplicationDao();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		// remove the contents of the table
		connection.createStatement().execute("truncate table wallet RESTART IDENTITY;");
		// reset auto_increment ids to 1
		connection.createStatement().execute("ALTER table wallet alter column walletId RESTART WITH 1;");
	}

	/**
	 * Test method for
	 * {@link dao.ApplicationDao#createWallet(int, java.sql.Connection)}.
	 */
	@Test
	void testCreateWallet() {
		int amount = dao.createWallet(userId, connection);
		assertEquals(1, amount);

		// duplicate userId (error due to unique index)
		amount = dao.createWallet(userId, connection);
		assertEquals(-1, amount);
	}

	/**
	 * Test method for
	 * {@link dao.ApplicationDao#getWalletUserId(int, java.sql.Connection)}.
	 */
	@Test
	void testGetWalletUserId() {
		// when there is no wallet
		Wallet wallet = dao.getWalletWalletId(walletId, connection);
		assertEquals(null, wallet);

		// create wallet
		int rowsAffected = dao.createWallet(userId, connection);
		assertEquals(1, rowsAffected);

		// get wallet
		wallet = dao.getWalletUserId(userId, connection);
		assertEquals(1, wallet.getUserId());
		assertEquals(1, wallet.getWalletId());
		assertEquals(0, wallet.getAmount());
	}

	/**
	 * Test method for
	 * {@link dao.ApplicationDao#getWalletWalletId(int, java.sql.Connection)}.
	 */
	@Test
	void testGetWalletWalletId() {
		// when there is no wallet
		Wallet wallet = dao.getWalletWalletId(walletId, connection);
		assertEquals(null, wallet);

		// create wallet
		int rowsAffected = dao.createWallet(userId, connection);
		assertEquals(1, rowsAffected);

		// get wallet
		wallet = dao.getWalletWalletId(walletId, connection);
		assertEquals(1, wallet.getUserId());
		assertEquals(1, wallet.getWalletId());
		assertEquals(0, wallet.getAmount());
	}

	/**
	 * Test method for
	 * {@link dao.ApplicationDao#updateWalletAmount(int, double, java.sql.Connection)}.
	 */
	@Test
	void testUpdateWalletAmount() {
		// no rows affected
		int rowsAffected = dao.updateWalletAmount(walletId, amount, connection);
		assertEquals(0, rowsAffected);
		
		// create wallet
		rowsAffected = dao.createWallet(userId, connection);
		assertEquals(1, rowsAffected);
		
		// update wallet amount
		rowsAffected = dao.updateWalletAmount(walletId, 50, connection);
		assertEquals(1, rowsAffected);
		
		//get wallet
		Wallet wallet = dao.getWalletWalletId(walletId, connection);
		assertEquals(1, wallet.getUserId());
		assertEquals(1, wallet.getWalletId());
		assertEquals(50, wallet.getAmount());
	}

}
