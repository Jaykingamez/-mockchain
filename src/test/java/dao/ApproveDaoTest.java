/**
 * 
 */
package dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.intThat;

import java.sql.Connection;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import beans.Approve;
import dao.ApplicationDao;
import dao.TestDBConnection;

/**
 * @author jayki
 *
 */
class ApproveDaoTest {

	private int approveId;
	private int userId;
	private int transactionId;
	private boolean approve;

	private static Connection connection;
	private ApplicationDao dao;
	private Approve testApprove;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		connection = TestDBConnection.getConnectionToDatabase();
		connection.createStatement().execute("CREATE SCHEMA mockchain ;");
		connection.createStatement()
				.execute("CREATE TABLE `approve` (\r\n"
						+ "  `approveId` INT UNSIGNED NOT NULL AUTO_INCREMENT,\r\n"
						+ "  `transactionId` INT UNSIGNED NOT NULL,\r\n" + "  `userId` INT UNSIGNED NOT NULL,\r\n"
						+ "  `approve` TINYINT(1) NULL,\r\n" + "  PRIMARY KEY (`approveId`),\r\n"
						+ "  UNIQUE INDEX `approveId_UNIQUE` (`approveId` ASC));");
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
		dao = new ApplicationDao();
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
		// remove the contents of the table
		connection.createStatement().execute("truncate table approve RESTART IDENTITY;");
		// reset auto_increment ids to 1
		connection.createStatement().execute("ALTER table approve alter column approveId RESTART WITH 1;");
	}

	/**
	 * Test method for
	 * {@link dao.ApplicationDao#getApproveList(int, int, java.sql.Connection)}.
	 */
	@Test
	void testGetApproveList() {
		List<Approve> approveList = dao.getApproveList(transactionId, approveId, connection);
		assertEquals(0, approveList.size());
		
		int rowsAffected = dao.addApprove(transactionId, userId, approve ? 1 : 0, connection);
		assertEquals(1, rowsAffected);
		
		approveList = dao.getApproveList(transactionId, approveId, connection);
		assertEquals(1, approveList.size());
	}

	/**
	 * Test method for
	 * {@link dao.ApplicationDao#addApprove(int, int, int, java.sql.Connection)}.
	 */
	@Test
	void testAddApprove() {
		// if approve == true, return 1, else return 0
		int rowsAffected = dao.addApprove(transactionId, userId, approve ? 1 : 0, connection);
		assertEquals(1, rowsAffected);
		
	}

}
