/**
 * 
 */
package dao;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import beans.Transaction;
import dao.ApplicationDao;
import dao.TestDBConnection;

/**
 * @author jayki
 *
 */
class TransactionDaoTest {
	
	private static Connection connection;
	private Transaction testTransaction;
	private ApplicationDao dao;
	
	private int transactionId;
	private Integer previousTransactionId;
	private Timestamp timestamp;
	private int walletId;
	private Integer receiverId;
	private double amount;
	private String type;
	private Boolean approve;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		connection = TestDBConnection.getConnectionToDatabase();
		connection.createStatement().execute("CREATE SCHEMA mockchain ;");
		connection.createStatement()
				.execute("CREATE TABLE `transaction` (\r\n"
						+ "  `transactionId` INT UNSIGNED NOT NULL AUTO_INCREMENT,\r\n"
						+ "  `previousTransactionId` INT NULL,\r\n"
						+ "  `timestamp` TIMESTAMP NOT NULL,\r\n"
						+ "  `walletId` INT UNSIGNED NOT NULL,\r\n"
						+ "  `receiverId` INT UNSIGNED NULL,\r\n"
						+ "  `amount` DECIMAL(65,2) NOT NULL,\r\n"
						+ "  `type` VARCHAR(45) NOT NULL,\r\n"
						+ "  `approve` TINYINT NULL,\r\n"
						+ "  PRIMARY KEY (`transactionId`),\r\n"
						+ "  UNIQUE INDEX `transactionId_UNIQUE` (`transactionId` ASC),\r\n"
						+ "  UNIQUE INDEX `previousTransactionId_UNIQUE` (`previousTransactionId` ASC)"
						+ ");");
		
		connection.createStatement().execute("CREATE TABLE `approve` (\r\n"
				+ "  `approveId` INT UNSIGNED NOT NULL AUTO_INCREMENT,\r\n"
				+ "  `transactionId` INT UNSIGNED NOT NULL,\r\n"
				+ "  `userId` INT UNSIGNED NOT NULL,\r\n"
				+ "  `approve` TINYINT(1) NULL,\r\n"
				+ "  PRIMARY KEY (`approveId`),\r\n"
				+ "  UNIQUE INDEX `approveId_UNIQUE` (`approveId` ASC)"
				+ ");");
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
		transactionId = 1;
		previousTransactionId = 0;
		timestamp = new Timestamp(System.currentTimeMillis());
		walletId = 1;
		receiverId = 2;
		amount = 0;
		type = "personal"; // either "personal" or "transfer"
		approve  = null; // either true, false or null
		
		testTransaction = new Transaction(transactionId, previousTransactionId, timestamp, walletId, 
				receiverId, amount, type, approve);
		
		dao = new ApplicationDao();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		connection.createStatement().execute("DELETE FROM transaction");
	}

	/**
	 * Test method for
	 * {@link dao.ApplicationDao#getLastTransactionId(int, java.sql.Connection)}.
	 */
	@Test
	void testGetLastTransactionId() {
		// no transactions
		int id = dao.getLastTransactionId(walletId, connection);
		assertEquals(-1, id);
		
		
		int rowsAffected = dao.addTransaction(testTransaction, connection);
		assertEquals(1, rowsAffected);
		
		// one transaction in the list
		id = dao.getLastTransactionId(walletId, connection);
		assertEquals(1, id);
	}

	/**
	 * Test method for
	 * {@link dao.ApplicationDao#getAllTransactions(java.lang.String, int, java.sql.Connection)}.
	 */
	@Test
	void testGetAllTransactions() {
		List<Transaction> transactionList = dao.getAllTransactions("", 1, connection);
		assertEquals(0, transactionList.size());
		
		List<Transaction> approveTransactionList = dao.getAllTransactions("approve", 1, connection);
		assertEquals(0, approveTransactionList.size());
		
		// add transaction which was not approved
		int rowsUpdated = dao.addTransaction(testTransaction, connection);
		assertEquals(1, rowsUpdated);
		
		transactionList = dao.getAllTransactions("", 1, connection);
		assertEquals(1, transactionList.size());
		
		approveTransactionList = dao.getAllTransactions("approve", 1, connection);
		assertEquals(0, approveTransactionList.size());
		
		// add transaction that has been approved
		Transaction approvedTransaction = new Transaction(transactionId + 1, previousTransactionId + 1, timestamp,
				walletId, receiverId, amount, type, true);
		rowsUpdated = dao.addApprovedTransaction(approvedTransaction, connection);
		assertEquals(1, rowsUpdated);
		
		transactionList = dao.getAllTransactions("", 1, connection);
		assertEquals(1, transactionList.size());
		
		approveTransactionList = dao.getAllTransactions("approve", 1, connection);
		assertEquals(1, approveTransactionList.size());
	}

	/**
	 * Test method for
	 * {@link dao.ApplicationDao#getTransaction(int, java.sql.Connection)}.
	 */
	@Test
	void testGetTransaction() {
		// check whether transaction exists
		Transaction transaction = dao.getTransaction(walletId, connection);
		assertEquals(null, transaction);
		
		assertEquals(testTransaction.getTransactionId(), transactionId);
		assertEquals(testTransaction.getPreviousTransactionId(), previousTransactionId);
		assertEquals(testTransaction.getTimestamp(), timestamp);
		assertEquals(testTransaction.getWalletId(), walletId);
		assertEquals(testTransaction.getReceiverId(), receiverId); 
		assertEquals(testTransaction.getAmount(), amount);
		assertEquals(testTransaction.getType(), type);
		assertEquals(testTransaction.getApprove(), approve);
		
		// add existing transaction
		int rowsUpdated = dao.addTransaction(testTransaction, connection);
		assertEquals(1, rowsUpdated);
		
		// check for transaction
		transaction = dao.getTransaction(walletId, connection);
		assertEquals(transaction.getTransactionId(), transactionId);
		assertEquals(transaction.getPreviousTransactionId(), previousTransactionId);
		assertEquals(transaction.getTimestamp(), timestamp);
		assertEquals(transaction.getWalletId(), walletId);
		assertEquals(transaction.getReceiverId(), receiverId); 
		assertEquals(transaction.getAmount(), amount);
		assertEquals(transaction.getType(), type);
		assertEquals(transaction.getApprove(), approve); 
		
	}

	/**
	 * Test method for
	 * {@link dao.ApplicationDao#addTransaction(beans.Transaction, java.sql.Connection)}.
	 */
	@Test
	void testAddTransaction() {
		int rowsAffected = dao.addTransaction(testTransaction, connection);
		assertEquals(1, rowsAffected);
		
		// no duplicate due to index
		rowsAffected = dao.addTransaction(testTransaction, connection);
		assertEquals(-1, rowsAffected);
	}
	

	/**
	 * Test method for
	 * {@link dao.ApplicationDao#addApprovedTransaction(beans.Transaction, java.sql.Connection)}.
	 */
	@Test
	void testAddApprovedTransaction() {
		// if transaction is unapproved
		int rowsAffected = dao.addTransaction(testTransaction, connection);
		assertEquals(1, rowsAffected);
		
		// no duplicate due to index
		rowsAffected = dao.addTransaction(testTransaction, connection);
		assertEquals(-1, rowsAffected);
		
		// if transaction has approval
		Transaction approvedTransaction = new Transaction(transactionId + 1, previousTransactionId + 1, timestamp,
				walletId, receiverId, amount, type, true);
		
		rowsAffected = dao.addTransaction(approvedTransaction, connection);
		assertEquals(1, rowsAffected);
		
		// no duplicate due to index
		rowsAffected = dao.addTransaction(approvedTransaction, connection);
		assertEquals(-1, rowsAffected);
	}

	/**
	 * Test method for
	 * {@link dao.ApplicationDao#updateTransactionApproval(int, int, java.sql.Connection)}.
	 */
	@Test
	void testUpdateTransactionApproval() {
		// add unapproved transaction
		int rowsAffected = dao.addTransaction(testTransaction, connection);
		assertEquals(1, rowsAffected);
		
		// 1 is true for booleans
		rowsAffected = dao.updateTransactionApproval(transactionId, 1, connection);
		assertEquals(1, rowsAffected);
		
		Transaction transaction = dao.getTransaction(walletId, connection);
		assertEquals(transaction.getApprove(), true);
	}

}
