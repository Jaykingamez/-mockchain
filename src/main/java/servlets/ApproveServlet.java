package servlets;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.JoinRowSet;

import org.eclipse.jdt.internal.compiler.IDebugRequestor;

import beans.Transaction;
import beans.Wallet;
import dao.ApplicationDao;

@WebServlet("/approve")
public class ApproveServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int userId = (int) request.getSession().getAttribute("userId");

		ApplicationDao dao = new ApplicationDao();
		Connection connection = (Connection) getServletContext().getAttribute("dbconnection");
		request.setAttribute("transactions", dao.getAllTransactions("", userId, connection));
		request.getRequestDispatcher("/jsp/approve.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// temp fix, I dont know why ajax is making calls here, but this will work for
		// now
		boolean ajax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
		if (ajax) {
			return;
		}

		String bool = request.getParameter("bool");
		int walletId = Integer.parseInt(request.getParameter("hiddenWalletId"));
		int transactionId = Integer.parseInt(request.getParameter("transactionId"));
		int userId = (int) request.getSession().getAttribute("userId");

		ApplicationDao dao = new ApplicationDao();
		Connection connection = (Connection) getServletContext().getAttribute("dbconnection");

		int numberOfUsers = dao.getTotalNumberOfUsers(connection);
		// more than 50% of users, transaction is approved
		// get the number of users at 50%
		int halfNumberOfUsers = Math.floorDiv(numberOfUsers, 2);

		// should also work on a transfer use case
		if (bool.equals("approve")) {
			int approveBool = 1;
			dao.addApprove(transactionId, userId, approveBool, connection);
			int numberOfApprovals = dao.getApprove(transactionId, approveBool, connection);
			if (numberOfApprovals >= halfNumberOfUsers) {
				// approve transaction
				dao.updateTransactionApproval(transactionId, approveBool, connection);

				Wallet wallet = dao.getWalletWalletId(walletId, connection);
				Transaction transaction = dao.getTransaction(walletId, connection);

				double walletAmount = wallet.getAmount();
				double transactionAmount = transaction.getAmount();
				double newAmount = walletAmount + transactionAmount;

				// if transaction is negative, amount was already deducted
				if (transactionAmount > 0) {
					dao.updateWalletAmount(walletId, newAmount, connection);
				} else if(transaction.getReceiverId() != null) {
					int receiverId = transaction.getReceiverId();
					
					Wallet receiverWallet = dao.getWalletWalletId(receiverId, connection);
					double receiverAmount = receiverWallet.getAmount();
					
					//transactionAmount will be negative, since it's a transfer transaction
					// so the money will be added to the receiver's account
					dao.updateWalletAmount(receiverId, receiverAmount - transactionAmount, connection);
				}

			}
		} else {
			int rejectBool = 0;
			dao.addApprove(transactionId, userId, rejectBool, connection);
			int numberOfRejections = dao.getApprove(transactionId, rejectBool, connection);
			if (numberOfRejections >= halfNumberOfUsers) {
				// reject transaction
				dao.updateTransactionApproval(transactionId, rejectBool, connection);

				Wallet wallet = dao.getWalletWalletId(walletId, connection);
				Transaction transaction = dao.getTransaction(walletId, connection);

				double walletAmount = wallet.getAmount();
				double transactionAmount = transaction.getAmount();

				// if transaction is negative, return deducted amount
				if (transactionAmount < 0) {
					double newAmount = walletAmount - transactionAmount;
					dao.updateWalletAmount(walletId, newAmount, connection);
				}
			}
		}
		response.sendRedirect("approve");
	}

}
