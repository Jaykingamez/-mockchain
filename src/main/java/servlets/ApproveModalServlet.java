package servlets;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Request;

import beans.Wallet;
import dao.ApplicationDao;

/**
 * Servlet implementation class ApproveModalServlet
 */
@WebServlet("/approveModal")
public class ApproveModalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		int walletId = Integer.parseInt(request.getParameter("walletId"));
		double amount = Double.parseDouble(request.getParameter("amount"));
		Integer receiverId = Integer.parseInt(request.getParameter("receiverId"));
		String type = request.getParameter("type");

		ApplicationDao dao = new ApplicationDao();
		Connection connection = (Connection) getServletContext().getAttribute("dbconnection");
		Wallet walletIdWallet = dao.getWalletWalletId(walletId, connection);
		Double walletIdAmount = walletIdWallet.getAmount();
		Double newWalletAmount = walletIdAmount + amount;
		
		request.setAttribute("walletId", walletId);
		request.setAttribute("walletIdAmount", walletIdAmount);
		request.setAttribute("transactedAmount", amount);
		request.setAttribute("newWalletAmount", newWalletAmount);

		if (type == "transfer") {
			Wallet receiverIdWallet = dao.getWalletWalletId(receiverId, connection);
			Double receiverIdAmount = receiverIdWallet.getAmount();
			// for transfer transaction, amount will be negative,
			// doing this will sum the amoutn, adding into the receiver's account
			Double newReceiverAmount = receiverIdAmount - amount;
			
			request.setAttribute("type", type);
			request.setAttribute("receiverId", receiverId);
			request.setAttribute("receiverIdAmount", receiverIdAmount);
			request.setAttribute("newReceiverAmount", newReceiverAmount);
		}
		request.getRequestDispatcher("/jsp/approve.jsp").forward(request, response);
		
	}

}
