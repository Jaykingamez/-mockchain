package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.naming.CannotProceedException;
import javax.swing.plaf.multi.MultiInternalFrameUI;

import org.apache.tomcat.util.modeler.modules.MbeansDescriptorsIntrospectionSource;

import dao.DBConnection;
import servlets.WalletServlet;
import beans.Approve;
import beans.Transaction;
import beans.User;
import beans.Wallet;

public class ApplicationDao {

	private int errorCode = -1;

	/**
	 * Register the user by adding their info into the database
	 */
	public int registerUser(User user, Connection connection) {
		int rowsAffected = errorCode;

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
		int userId = errorCode;
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

	public int getTotalNumberOfUsers(Connection connection) {
		int number = -1;
		try {
			// write the select query
			String sql = "select count(*) from user";
			PreparedStatement statement = connection.prepareStatement(sql);

			ResultSet set = statement.executeQuery();
			while (set.next()) {
				number = set.getInt("count(*)");
			}
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		return number;
	}

	/**
	 * Create new user's wallet
	 */
	public int createWallet(int userId, Connection connection) {
		int rowsAffected = errorCode;

		try {
			// write the insert query
			String insertQuery = "insert into wallet (userId, amount) values (?,?)";

			// set parameters with PreparedStatement
			java.sql.PreparedStatement statement = connection.prepareStatement(insertQuery);
			statement.setInt(1, userId);
			statement.setInt(2, 0);

			// execute the statement
			rowsAffected = statement.executeUpdate();

		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		return rowsAffected;
	}

	/**
	 * Get user's wallet using userId
	 */
	public Wallet getWalletUserId(int userId, Connection connection) {
		Wallet wallet = null;
		try {
			// write the select query
			String sql = "select * from wallet where userId=?";

			// set parameters with PreparedStatement
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, userId);

			// execute the statement and check whether user exists
			ResultSet set = statement.executeQuery();

			while (set.next()) {
				int walletId = set.getInt("walletId");
				double amount = set.getDouble("amount");
				wallet = new Wallet(walletId, userId, amount);
			}

		} catch (SQLException exception) {
			exception.printStackTrace();
		}

		return wallet;
	}

	/**
	 * Get the user's wallet using walletId
	 */
	public Wallet getWalletWalletId(int walletId, Connection connection) {
		Wallet wallet = null;
		try {
			// write the select query
			String sql = "select * from wallet where walletId=?";

			// set parameters with PreparedStatement
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, walletId);

			// execute the statement and check whether user exists
			ResultSet set = statement.executeQuery();

			while (set.next()) {
				int userId = set.getInt("userId");
				double amount = set.getDouble("amount");
				wallet = new Wallet(walletId, userId, amount);
			}

		} catch (SQLException exception) {
			exception.printStackTrace();
		}

		return wallet;
	}

	/**
	 * update the amount in a user using walletId
	 */
	public int updateWalletAmount(int walletId, double amount, Connection connection) {
		int rowsAffected = errorCode;
		try {
			// write the select query
			String sql = "update wallet set amount=? where walletId=?";

			// set parameters with PreparedStatement
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setDouble(1, amount);
			statement.setInt(2, walletId);

			// execute the statement and check whether user exists
			rowsAffected = statement.executeUpdate();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		return rowsAffected;
	}

	/**
	 * Get the last transactionId
	 */
	public int getLastTransactionId(int walletId, Connection connection) {
		int lastTransactionId = -1;
		try {
			String getQuery = "select * from transaction order by timestamp desc limit 1";
			// set parameters with PreparedStatement
			java.sql.PreparedStatement statement = connection.prepareStatement(getQuery);

			ResultSet set = statement.executeQuery();

			while (set.next()) {
				lastTransactionId = set.getInt("transactionId");
			}

		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		return lastTransactionId;
	}

	public List<Transaction> getAllTransactions(String approveString, int userId, Connection connection) {
		List<Transaction> transactions = new ArrayList<>();

		try {
			String getQuery = "";
			PreparedStatement preparedStatement;
			if (approveString == "approve") {
				getQuery = "select * from transaction where approve is not null";
				preparedStatement = connection.prepareStatement(getQuery);
			} else {
				getQuery = "select * from transaction where approve is null and"
						+ " transactionId not in (select transactionId from approve where userId=?)";
				preparedStatement = connection.prepareStatement(getQuery);
				preparedStatement.setInt(1, userId);
			}
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				int transactionId = resultSet.getInt("transactionId");
				// it might be null due to the first block in the chain having nowhere to point
				// back to
				Integer previousTransactionId = resultSet.getObject("previousTransactionId", Integer.class);
				Timestamp timestamp = resultSet.getTimestamp("timestamp");
				int walletId = resultSet.getInt("walletId");
				// it might be null due to some transactions involving giving oneself money
				Integer receiverId = resultSet.getObject("receiverId", Integer.class);
				double amount = resultSet.getDouble("amount");
				String type = resultSet.getString("type");
				Boolean approve = resultSet.getObject("approve", Boolean.class);

				Transaction transaction = new Transaction(transactionId, previousTransactionId, timestamp, walletId,
						receiverId, amount, type, approve);
				transactions.add(transaction);
			}
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		return transactions;

	}

	/**
	 * Get transaction based on walletId
	 */
	public Transaction getTransaction(int walletId, Connection connection) {
		Transaction transaction = null;
		try {
			String getQuery = "select * from transaction where walletId=? order by timestamp desc limit 1";
			// set parameters with PreparedStatement
			java.sql.PreparedStatement statement = connection.prepareStatement(getQuery);
			statement.setInt(1, walletId);

			ResultSet set = statement.executeQuery();

			while (set.next()) {
				int transactionId = set.getInt("transactionId");
				int previousTransactionId = set.getInt("previousTransactionId");
				Timestamp timestamp = set.getTimestamp("timestamp");
				int receiverId = set.getInt("receiverId");
				double amount = set.getDouble("amount");
				String type = set.getString("type");
				Boolean approve = set.getObject("approve", Boolean.class);

				transaction = new Transaction(transactionId, previousTransactionId, timestamp, walletId, receiverId,
						amount, type, approve);
			}

		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		return transaction;
	}

	/**
	 * Add transaction in transaction list
	 */
	public int addTransaction(Transaction transaction, Connection connection) {
		int rowsAffected = errorCode;

		try {

			String insertQuery = "insert into transaction (transactionId, previousTransactionId, timestamp, walletId, "
					+ "receiverId, amount, type) values (?, ?, ?, ?, ?, ?, ?)";
			// set parameters with PreparedStatement
			java.sql.PreparedStatement statement = connection.prepareStatement(insertQuery);
			statement.setInt(1, transaction.getTransactionId());
			statement.setObject(2, transaction.getPreviousTransactionId(), Types.INTEGER);
			statement.setTimestamp(3, transaction.getTimestamp());
			statement.setInt(4, transaction.getWalletId());
			statement.setObject(5, transaction.getReceiverId(), Types.INTEGER);
			statement.setDouble(6, transaction.getAmount());
			statement.setString(7, transaction.getType());

			// execute the statement
			rowsAffected = statement.executeUpdate();

		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		return rowsAffected;
	}
	
	public int addApprovedTransaction(Transaction transaction, Connection connection) {
		int rowsAffected = errorCode;

		try {

			String insertQuery = "insert into transaction (transactionId, previousTransactionId, timestamp, walletId, "
					+ "receiverId, amount, type, approve) values (?, ?, ?, ?, ?, ?, ?, ?)";
			// set parameters with PreparedStatement
			java.sql.PreparedStatement statement = connection.prepareStatement(insertQuery);
			statement.setInt(1, transaction.getTransactionId());
			statement.setObject(2, transaction.getPreviousTransactionId(), Types.INTEGER);
			statement.setTimestamp(3, transaction.getTimestamp());
			statement.setInt(4, transaction.getWalletId());
			statement.setObject(5, transaction.getReceiverId(), Types.INTEGER);
			statement.setDouble(6, transaction.getAmount());
			statement.setString(7, transaction.getType());
			statement.setBoolean(8, transaction.getApprove());

			// execute the statement
			rowsAffected = statement.executeUpdate();

		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		return rowsAffected;
	}

	public int updateTransactionApproval(int transactionId, int bool, Connection connection) {
		int rowsAffected = errorCode;
		try {
			String updateQuery = "update transaction set approve=? where transactionId=?";
			// set parameters with PreparedStatement
			java.sql.PreparedStatement statement = connection.prepareStatement(updateQuery);
			statement.setInt(1, bool);
			statement.setInt(2, transactionId);
			
			rowsAffected = statement.executeUpdate();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		return rowsAffected;
	}

	/**
	 * If bool is 1, true If bool is 0, false Get the number of people who approve
	 */
	public List<Approve> getApproveList(int transactionId, int bool, Connection connection) {
		List<Approve> approveList = new ArrayList<Approve>();
		try {
			String getQuery = "select * from approve where transactionId = ? and approve = ?";
			// set parameters with PreparedStatement
			java.sql.PreparedStatement statement = connection.prepareStatement(getQuery);
			statement.setInt(1, transactionId);
			statement.setInt(2, bool);

			ResultSet set = statement.executeQuery();
			while (set.next()) {
				int approveId = set.getInt("approveId");
				int userId = set.getInt("userId");
				boolean approveBool = set.getBoolean("approve");
				
				Approve approve = new Approve(approveId, userId, transactionId, approveBool);
				approveList.add(approve);
			}

		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		return approveList;
	}

	/**
	 * Add approve in approve list
	 */
	public int addApprove(int transactionId, int userId, int bool, Connection connection) {
		int rowsAffected = errorCode;
		try {
			// write the insert query
			String insertQuery = "insert into approve (transactionId, userId, approve) values (?, ?, ?)";

			// set parameters with PreparedStatement
			java.sql.PreparedStatement statement = connection.prepareStatement(insertQuery);
			statement.setInt(1, transactionId);
			statement.setInt(2, userId);
			statement.setInt(3, bool);

			// execute the statement
			rowsAffected = statement.executeUpdate();

		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		return rowsAffected;
	}

}
