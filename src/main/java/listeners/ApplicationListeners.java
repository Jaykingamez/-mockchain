package listeners;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import dao.DBConnection;
import dao.TestDBConnection;

@WebListener
public class ApplicationListeners implements ServletContextListener {
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("in contextDestroyed method");
		Connection connection = (Connection)arg0.getServletContext().getAttribute("dbconnection");
		TestDBConnection.destroyDatabase(connection);
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("in contextinitialized method");
		Connection connection = TestDBConnection.getConnectionToDatabase();
		TestDBConnection.initializeDatabase(connection);
		arg0.getServletContext().setAttribute("dbconnection", connection);
	}


}
