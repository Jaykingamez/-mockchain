package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Transaction;
import beans.Wallet;
import dao.ApplicationDao;

/**
 * Servlet implementation class TransferServlet
 */
@WebServlet("/transfer")
public class TransferServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("wallet", "transfer");
		request.getRequestDispatcher("/jsp/wallet.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String infoMessage = "Transaction performed successfully!";

		label: try {
			int receiverId = Integer.parseInt(request.getParameter("receiver"));
			double amount = Double.parseDouble(request.getParameter("amount"));
			int walletId = (int) request.getSession().getAttribute("walletId");

			ApplicationDao dao = new ApplicationDao();
			Connection connection = (Connection) getServletContext().getAttribute("dbconnection");

			// Get last transaction Id so that it can be chained to the new transaction
			// return 0 by default if no transactions exist in the table
			Integer lastTransactionId = dao.getLastTransactionId(walletId, connection);

			int transactionId = lastTransactionId + 1;
			if (lastTransactionId == -1) {
				lastTransactionId = null;
			}

			Wallet wallet = dao.getWalletWalletId(walletId, connection);
			if (wallet.getAmount() < amount) {
				infoMessage = "You don't have that much funds in your wallet!";
				break label;
			} else {
				// stake the money so it cant be used for other transactions
				dao.updateWalletAmount(walletId, wallet.getAmount() - amount, connection);
			}

			amount = -amount;

			Transaction transaction = new Transaction(transactionId, lastTransactionId,
					new Timestamp(System.currentTimeMillis()), walletId, receiverId, amount, "transfer", null);
			int transactionAffected = dao.addTransaction(transaction, connection);

			if (transactionAffected == -1 || transaction == null) {
				infoMessage = "Sorry, we failed to perform that transaction!";
				break label;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		request.getSession().setAttribute("infoMessage", infoMessage);
		response.sendRedirect("wallet");
	}

}
