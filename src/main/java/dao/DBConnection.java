package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	public static Connection getConnectionToDatabase() {
		Connection connection = null;

		try {

			// load the driver class
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("MySQL JDBC Driver Registered!");

			// get hold of the DriverManager
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mockchain", "root", "root");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your MySQL JDBC Driver?");
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
	
	public static void initializeDatabase(Connection connection) {
		try {
			//connection.createStatement().execute("CREATE SCHEMA mockchain ;");
			connection.createStatement()
					.execute("CREATE TABLE `mockchain`.`user` (\r\n" + "  `userId` INT UNSIGNED NOT NULL AUTO_INCREMENT,\r\n"
							+ "  `username` VARCHAR(50) NOT NULL,\r\n" + "  `password` VARCHAR(50) NOT NULL,\r\n"
							+ "  PRIMARY KEY (`userId`),\r\n" + "  UNIQUE INDEX `username_UNIQUE` (`username` ASC));");
			connection.createStatement()
					.execute("CREATE TABLE `mockchain`.`wallet` (\r\n" + "  `walletId` INT NOT NULL AUTO_INCREMENT,\r\n"
							+ "  `userId` INT UNSIGNED NOT NULL,\r\n" + "  `amount` DECIMAL(65,2) NOT NULL,\r\n"
							+ "  UNIQUE INDEX `userId_UNIQUE` (`userId` ASC),\r\n" + "  PRIMARY KEY (`walletId`),\r\n"
							+ "  UNIQUE INDEX `walletId_UNIQUE` (`walletId` ASC));\r\n");
			connection.createStatement()
					.execute("CREATE TABLE `mockchain`.`transaction` (\r\n"
							+ "  `transactionId` INT UNSIGNED NOT NULL AUTO_INCREMENT,\r\n"
							+ "  `previousTransactionId` INT NULL,\r\n" + "  `timestamp` TIMESTAMP NOT NULL,\r\n"
							+ "  `walletId` INT UNSIGNED NOT NULL,\r\n" + "  `receiverId` INT UNSIGNED NULL,\r\n"
							+ "  `amount` DECIMAL(65,2) NOT NULL,\r\n" + "  `type` VARCHAR(45) NOT NULL,\r\n"
							+ "  `approve` TINYINT NULL,\r\n" + "  PRIMARY KEY (`transactionId`),\r\n"
							+ "  UNIQUE INDEX `transactionId_UNIQUE` (`transactionId` ASC),\r\n"
							+ "  UNIQUE INDEX `previousTransactionId_UNIQUE` (`previousTransactionId` ASC)" + ");");
			connection.createStatement()
					.execute("CREATE TABLE `mockchain`.`approve` (\r\n" + "  `approveId` INT UNSIGNED NOT NULL AUTO_INCREMENT,\r\n"
							+ "  `transactionId` INT UNSIGNED NOT NULL,\r\n" + "  `userId` INT UNSIGNED NOT NULL,\r\n"
							+ "  `approve` TINYINT(1) NULL,\r\n" + "  PRIMARY KEY (`approveId`),\r\n"
							+ "  UNIQUE INDEX `approveId_UNIQUE` (`approveId` ASC));");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void destroyDatabase(Connection connection) {
		try {
			String[] tableList = {"user", "wallet", "transaction", "approve"};
			for (String table: tableList) {
				connection.createStatement().execute("USE mockchain");
				connection.createStatement().execute("truncate table " + table);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
