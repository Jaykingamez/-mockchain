package servlets;

import java.awt.Checkbox;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Approve;
import beans.Transaction;
import dao.ApplicationDao;

/**
 * Servlet implementation class PersonalServlet
 */
@WebServlet("/personal")
public class PersonalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("wallet", "personal");
		request.getRequestDispatcher("/jsp/wallet.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String operator = request.getParameter("operator");
			double amount = Double.parseDouble(request.getParameter("amount"));
			int walletId = (int) request.getSession().getAttribute("walletId");
			
			// deducting the amount makes it negative, else leave it as positive
			if (operator== "-") {
				amount = -amount;
			}
			
			ApplicationDao dao = new ApplicationDao();
			Connection connection = (Connection) getServletContext().getAttribute("dbconnection");
			
			// Get last transaction Id so that it can be chained to the new transaction
			// return 0 by default if no transactions exist in the table
			Integer lastTransactionId = dao.getLastTransactionId(walletId, connection);
			
			int transactionId = lastTransactionId + 1;
			if (lastTransactionId != -1) {
				lastTransactionId = null;
			} 
			
			Transaction transaction = new Transaction(transactionId, lastTransactionId, new Timestamp(System.currentTimeMillis()),
					walletId, null, amount, "personal", null);
			int transactionAffected = dao.addTransaction(transaction, connection);

			transaction = dao.getTransaction(walletId, connection);
			transactionId = transaction.getTransactionId();
			int approveAffected = dao.addApprove(transactionId, connection);

			String infoMessage = null;
			if (transactionAffected == -1) {
				infoMessage = "Sorry, we failed to perform that transaction!";
				request.setAttribute("infoMessage", infoMessage);
				request.getRequestDispatcher("/jsp/wallet.jsp").forward(request, response);
			} else if (approveAffected == -1) {
				infoMessage = "Sorry, we failed to add the transaction for approval!";
				request.setAttribute("infoMessage", infoMessage);
				request.getRequestDispatcher("/jsp/wallet.jsp").forward(request, response);
			}
		} catch (NumberFormatException exception) {
			exception.printStackTrace();
		}

	}

}
