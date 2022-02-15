package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Approve;
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

		// block ajax calls
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
			List<Approve> approveList = dao.getApproveList(transactionId, approveBool, connection);
			int numberOfApprovals = approveList.size();
			
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
				
				// Reward the miner
				Random random = new Random();
				int minerUserId = approveList.get(random.nextInt(numberOfApprovals)).getUserId();
				
				Wallet minerWallet = dao.getWalletUserId(minerUserId, connection);
				int minerWalletId = minerWallet.getWalletId();
				double minerWalletAmount = minerWallet.getAmount();
				
				
				// Get last transaction Id so that it can be chained to the new transaction
				// return 0 by default if no transactions exist in the table
				Integer lastTransactionId = dao.getLastTransactionId(walletId, connection);
				
				int minerTransactionId = lastTransactionId + 1;
				
				Transaction minerTransaction = new Transaction(minerTransactionId, lastTransactionId,
						new Timestamp(System.currentTimeMillis()), minerWalletId, null, 1, "personal", true);
				dao.addApprovedTransaction(minerTransaction, connection);
				
				//give a single token
				dao.updateWalletAmount(minerWalletId, minerWalletAmount + 1, connection);
			}
		} else {
			int rejectBool = 0;
			dao.addApprove(transactionId, userId, rejectBool, connection);
			List<Approve> approveList = dao.getApproveList(transactionId, rejectBool, connection);
			int numberOfRejections = approveList.size();
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
				
				// Reward the miner
				Random random = new Random();
				int minerUserId = approveList.get(random.nextInt(numberOfRejections)).getUserId();
				
				Wallet minerWallet = dao.getWalletUserId(minerUserId, connection);
				int minerWalletId = minerWallet.getWalletId();
				double minerWalletAmount = minerWallet.getAmount();
				
				// Get last transaction Id so that it can be chained to the new transaction
				// return 0 by default if no transactions exist in the table
				Integer lastTransactionId = dao.getLastTransactionId(walletId, connection);
				
				int minerTransactionId = lastTransactionId + 1;
				
				Transaction minerTransaction = new Transaction(minerTransactionId, lastTransactionId,
						new Timestamp(System.currentTimeMillis()), minerWalletId, null, 1, "personal", true);
				dao.addApprovedTransaction(minerTransaction, connection);
				
				//give a single token
				dao.updateWalletAmount(minerWalletId, minerWalletAmount + 1, connection);
			}
		}
		response.sendRedirect("approve");
	}

}
