/**
 * 
 */
package mockchain;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import java.sql.Connection;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import beans.User;
import dao.ApplicationDao;
import dao.TestDBConnection;
import net.bytebuddy.asm.Advice.This;
import servlets.RegisterServlet;

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
		connection.createStatement().execute("CREATE TABLE user ( \r\n"
				+ "   userId INT UNSIGNED NOT NULL AUTO_INCREMENT, \r\n"
				+ "   username VARCHAR(50) NOT NULL, \r\n"
				+ "   password VARCHAR(50) NOT NULL, \r\n"
				+ "	  PRIMARY KEY (`userId`),"
				+ "   UNIQUE INDEX `username_UNIQUE` (`username` ASC)"
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
		dao = new ApplicationDao();
		testUser = new User(testUsername, testPassword);
	}
	

	/**
	 * Test method for
	 * {@link dao.ApplicationDao#registerUser(beans.User, java.sql.Connection)}.
	 */
	@Test
	void testRegisterUser() {
		int result = dao.registerUser(testUser, connection);
		assertEquals(result, 1);
		
	}

	/**
	 * Test method for
	 * {@link dao.ApplicationDao#validateUser(beans.User, java.sql.Connection)}.
	 */
	@Test
	void testValidateUser() {
		int result = dao.validateUser(testUser, connection);
		assertEquals(result, 1);
		
		User fakeUser = new User(testUsername, testUsername);
		result = dao.validateUser(fakeUser, connection);
		assertEquals(result, -1);
	}

}
