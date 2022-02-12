/**
 * 
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author jayki
 *
 */
public class TestDBConnection {
	
	public static Connection getConnectionToDatabase() {
		Connection connection = null;

		try {

			// load the driver class
			Class.forName("org.h2.Driver");
			System.out.println("MySQL H2 Driver Registered!");

			// get hold of the DriverManager
			connection = DriverManager.getConnection("jdbc:h2:mem:db;DB_CLOSE_DELAY=-1", "sa", "sa");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your MySQL H2 Driver?");
			e.printStackTrace();

		}

		catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();

		}

		if (connection != null) {
			System.out.println("Connection made to DB!");
		}
		return connection;
	}
}
