package mockchain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import beans.User;

class UserTest {
	
	private User userTest;
	private String username;
	private String password;

	@BeforeEach
	void setUp() throws Exception {
		username = "username";
		password = "password";
		userTest = new User(username, password);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testUser() {
		assertEquals(userTest.getUsername(), username);
		assertEquals(userTest.getPassword(), password);
	}

	@Test
	void testGetPassword() {
		assertEquals(userTest.getPassword(), password);
		
	}

	@Test
	void testSetPassword() {
		userTest.setPassword(username);
		assertEquals(userTest.getPassword(), username);
	}

	@Test
	void testGetUsername() {
		assertEquals(userTest.getUsername(), username);
	}

	@Test
	void testSetUsername() {
		userTest.setUsername(password);
		assertEquals(userTest.getUsername(), password);
	}

}
