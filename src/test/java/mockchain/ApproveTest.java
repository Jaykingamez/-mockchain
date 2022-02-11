/**
 * 
 */
package mockchain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import beans.Approve;

/**
 * @author jayki
 *
 */
class ApproveTest {
	
	private int approveId;
	private int userId;
	private int transactionId;
	private boolean approve;
	
	private Approve testApprove;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		approveId = 1;
		userId = 1;
		transactionId = 1;
		approve = true;
		
		testApprove = new Approve(approveId, userId, transactionId, approve);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link beans.Approve#Approve(int, int, int, boolean)}.
	 */
	@Test
	void testApprove() {
		assertEquals(testApprove.getUserId(), userId);
		assertEquals(testApprove.getApproveId(), approveId);
		assertEquals(testApprove.getTransactionId(), transactionId);
		assertEquals(testApprove.isApprove(), approve);
	}

	/**
	 * Test method for {@link beans.Approve#getUserId()}.
	 */
	@Test
	void testGetUserId() {
		assertEquals(testApprove.getUserId(), userId);
	}

	/**
	 * Test method for {@link beans.Approve#setUserId(int)}.
	 */
	@Test
	void testSetUserId() {
		testApprove.setUserId(userId + 1);
		assertEquals(testApprove.getUserId(), userId + 1);
	}

	/**
	 * Test method for {@link beans.Approve#getApproveId()}.
	 */
	@Test
	void testGetApproveId() {
		assertEquals(testApprove.getApproveId(), approveId);
	}

	/**
	 * Test method for {@link beans.Approve#setApproveId(int)}.
	 */
	@Test
	void testSetApproveId() {
		testApprove.setApproveId(approveId + 1);
		assertEquals(testApprove.getApproveId(), approveId + 1);
	}

	/**
	 * Test method for {@link beans.Approve#getTransactionId()}.
	 */
	@Test
	void testGetTransactionId() {
		assertEquals(testApprove.getTransactionId(), transactionId);
	}

	/**
	 * Test method for {@link beans.Approve#setTransactionId(int)}.
	 */
	@Test
	void testSetTransactionId() {
		testApprove.setTransactionId(transactionId + 1);
		assertEquals(testApprove.getTransactionId(), transactionId + 1);
	}

	/**
	 * Test method for {@link beans.Approve#isApprove()}.
	 */
	@Test
	void testIsApprove() {
		assertEquals(testApprove.isApprove(), approve);
	}

	/**
	 * Test method for {@link beans.Approve#setApprove(boolean)}.
	 */
	@Test
	void testSetApprove() {
		testApprove.setApprove(false);
		assertEquals(testApprove.isApprove(), false);
	}

}
