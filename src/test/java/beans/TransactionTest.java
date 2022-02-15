/**
 * 
 */
package beans;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import beans.Transaction;

/**
 * @author jayki
 *
 */
class TransactionTest {
	
	private int transactionId;
	private Integer previousTransactionId;
	private Timestamp timestamp;
	private int walletId;
	private Integer receiverId;
	private double amount;
	private String type;
	private Boolean approve;
	
	private Transaction testTransaction;

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
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link beans.Transaction#Transaction(int, java.lang.Integer, java.sql.Timestamp, int, java.lang.Integer, double, java.lang.String, java.lang.Boolean)}.
	 */
	@Test
	void testTransaction() {
		assertEquals(testTransaction.getTransactionId(), transactionId);
		assertEquals(testTransaction.getPreviousTransactionId(), previousTransactionId);
		assertEquals(testTransaction.getTimestamp(), timestamp);
		assertEquals(testTransaction.getWalletId(), walletId);
		assertEquals(testTransaction.getReceiverId(), receiverId); 
		assertEquals(testTransaction.getAmount(), amount);
		assertEquals(testTransaction.getType(), type);
		assertEquals(testTransaction.getApprove(), approve);
	}
	
	/**
	 * Test method for {@link beans.Transaction#getTransactionId()}.
	 */
	@Test
	void testGetTransactionId() {
		assertEquals(testTransaction.getTransactionId(), transactionId);
	}

	/**
	 * Test method for {@link beans.Transaction#setTransactionId(int)}.
	 */
	@Test
	void testSetTransactionId() {
		testTransaction.setTransactionId(transactionId + 1);
		assertEquals(testTransaction.getTransactionId(), transactionId + 1);
	}

	/**
	 * Test method for {@link beans.Transaction#getPreviousTransactionId()}.
	 */
	@Test
	void testGetPreviousTransactionId() {
		assertEquals(testTransaction.getPreviousTransactionId(), previousTransactionId);
	}

	/**
	 * Test method for {@link beans.Transaction#setPrevioustransactionId(int)}.
	 */
	@Test
	void testSetPrevioustransactionId() {
		testTransaction.setPrevioustransactionId(previousTransactionId + 1);
		assertEquals(testTransaction.getPreviousTransactionId(), previousTransactionId + 1);
	}

	/**
	 * Test method for {@link beans.Transaction#getTimestamp()}.
	 */
	@Test
	void testGetTimestamp() {
		assertEquals(testTransaction.getTimestamp(), timestamp);
	}

	/**
	 * Test method for {@link beans.Transaction#setTimestamp(java.sql.Timestamp)}.
	 */
	@Test
	void testSetTimestamp() {
		Timestamp newTimestamp = new Timestamp(System.currentTimeMillis());
		testTransaction.setTimestamp(newTimestamp);
		assertEquals(testTransaction.getTimestamp(), newTimestamp);
	}

	/**
	 * Test method for {@link beans.Transaction#getWalletId()}.
	 */
	@Test
	void testGetWalletId() {
		assertEquals(testTransaction.getWalletId(), walletId);
	}

	/**
	 * Test method for {@link beans.Transaction#setWalletId(int)}.
	 */
	@Test
	void testSetWalletId() {
		testTransaction.setWalletId(walletId + 1);
		assertEquals(testTransaction.getWalletId(), walletId + 1);
	}

	/**
	 * Test method for {@link beans.Transaction#getReceiverId()}.
	 */
	@Test
	void testGetReceiverId() {
		assertEquals(testTransaction.getReceiverId(), receiverId); 
	}

	/**
	 * Test method for {@link beans.Transaction#setReceiverId(int)}.
	 */
	@Test
	void testSetReceiverId() {
		testTransaction.setReceiverId(receiverId + 1);
		assertEquals(testTransaction.getReceiverId(), receiverId + 1);
	}

	/**
	 * Test method for {@link beans.Transaction#getAmount()}.
	 */
	@Test
	void testGetAmount() {
		assertEquals(testTransaction.getAmount(), amount);
	}

	/**
	 * Test method for {@link beans.Transaction#setAmount(double)}.
	 */
	@Test
	void testSetAmount() {
		testTransaction.setAmount(amount + 1);
		assertEquals(testTransaction.getAmount(), amount + 1);
	}

	/**
	 * Test method for {@link beans.Transaction#getType()}.
	 */
	@Test
	void testGetType() {
		assertEquals(testTransaction.getType(), type);
	}

	/**
	 * Test method for {@link beans.Transaction#setType(java.lang.String)}.
	 */
	@Test
	void testSetType() {
		testTransaction.setType("transfer");
		assertEquals(testTransaction.getType(), "transfer");
	}

	/**
	 * Test method for {@link beans.Transaction#getApprove()}.
	 */
	@Test
	void testGetApprove() {
		assertEquals(testTransaction.getApprove(), approve);
	}

	/**
	 * Test method for {@link beans.Transaction#setApprove(boolean)}.
	 */
	@Test
	void testSetApprove() {
		testTransaction.setApprove(true);
		assertEquals(testTransaction.getApprove(), true);
	}

}
