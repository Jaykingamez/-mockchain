package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.CannotProceedException;

import dao.DBConnection;

import beans.User;

public class ApplicationDao {

	/**
	 * Register the user by adding their info into the database
	 */
	public int registerUser(User user, Connection connection) {
		int rowsAffected = 0;

		try {
			// write the insert query
			String insertQuery = "insert into user (username, password) values (?,?)";
			
			// set parameters with PreparedStatement
			java.sql.PreparedStatement statement = connection.prepareStatement(insertQuery);
			statement.setString(1, user.getUsername());
			statement.setString(2, user.getPassword());

			// execute the statement
			rowsAffected = statement.executeUpdate();

		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		return rowsAffected;
	}
	
	/**
	 * Check whether user exists and return their Id
	 */
	public int validateUser(User user, Connection connection) {
		// -1 by default means user cannot be found
		int userId = -1;
		try {
			// write the select query
			String sql = "select * from user where username=? and password=?";

			// set parameters with PreparedStatement
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, user.getUsername());
			statement.setString(2, user.getPassword());

			// execute the statement and check whether user exists
			ResultSet set = statement.executeQuery();
			while (set.next()) {
				userId = set.getInt("userId");
			}
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		return userId;
	}
	
	public int createWallet(int userId,Connection connection) {
		int amount = -1;
		
		try {
			// write the insert query
			String insertQuery = "insert into wallet (userId, amount) values (?,?)";
			
			// set parameters with PreparedStatement
			java.sql.PreparedStatement statement = connection.prepareStatement(insertQuery);
			statement.setInt(1, userId);
			statement.setInt(2, 0);

			// execute the statement
			amount = statement.executeUpdate();

		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		return amount;
	}
	
	public double getWalletAmount(int userId, Connection connection) {
		double amount = -1;
		try {
			// write the select query
			String sql = "select * from wallet where userId=?";

			// set parameters with PreparedStatement
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, userId);

			// execute the statement and check whether user exists
			ResultSet set = statement.executeQuery();
			while (set.next()) {
				amount = set.getDouble("amount");
			}
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		return amount;
	}
}
