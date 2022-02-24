/**
 * 
 */
package dao;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import beans.User;
/**
 * @author jayki
 *
 */
class UserDaoTest {

	private String testUsername = "testUsername";
	private String testPassword = "testPassword";

	private static Connection connection;
	private ApplicationDao dao;
	private User testUser;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		connection = TestDBConnection.getConnectionToDatabase();

		connection.createStatement().execute("CREATE SCHEMA mockchain ;");
		connection.createStatement()
				.execute("CREATE TABLE `user` (\r\n"
						+ "  `userId` INT UNSIGNED NOT NULL AUTO_INCREMENT,\r\n"
						+ "  `username` VARCHAR(50) NOT NULL,\r\n"
						+ "  `password` VARCHAR(50) NOT NULL,\r\n"
						+ "  PRIMARY KEY (`userId`),\r\n"
						+ "  UNIQUE INDEX `username_UNIQUE` (`username` ASC));");
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
		testUser = new User(testUsername, testPassword);
	}

	/**
	 * @throws java.lang.Exception
	 */

	@AfterEach
	void tearDown() throws Exception {
		// remove the contents of the table
		connection.createStatement().execute("truncate table user RESTART IDENTITY;");
		// reset auto_increment ids to 1
		connection.createStatement().execute("ALTER table user alter column userId RESTART WITH 1;");
	}

	/**
	 * Test method for
	 * {@link dao.ApplicationDao#registerUser(beans.User, java.sql.Connection)}.
	 * 
	 * @throws SQLException
	 */
	@Test
	void testRegisterUser() throws SQLException {
		int result = dao.registerUser(testUser, connection);
		assertEquals(result, 1);

		// validate user
		result = dao.validateUser(testUser, connection);
		assertEquals(result, 1);
	}

	/**
	 * Test method for
	 * {@link dao.ApplicationDao#validateUser(beans.User, java.sql.Connection)}.
	 */
	@Test
	void testValidateUser() {
		// add user
		int result = dao.registerUser(testUser, connection);
		assertEquals(result, 1);

		// validate user
		result = dao.validateUser(testUser, connection);
		assertEquals(result, 1);

		// test using a user that does not exist
		User fakeUser = new User(testUsername, testUsername);
		result = dao.validateUser(fakeUser, connection);
		assertEquals(result, -1);
	}

	/**
	 * Test method for
	 * {@link dao.ApplicationDao#getTotalNumberOfUsers(java.sql.Connection)}.
	 */
	@Test
	void testGetTotalNumberOfUsers() {
		int number = dao.getTotalNumberOfUsers(connection);
		assertEquals(0, number);

		int result = dao.registerUser(testUser, connection);
		assertEquals(1, result);

		number = dao.getTotalNumberOfUsers(connection);
		assertEquals(1, number);
	}

}
