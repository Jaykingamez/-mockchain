package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Request;

import com.google.gson.Gson;

import beans.User;
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
		
		boolean ajax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
		if(!ajax) {
			return;
		}
		
		HashMap<String, Object> hashMap = new HashMap<String, Object>();

		int transactionId = Integer.parseInt(request.getParameter("transactionId"));
		int walletId = Integer.parseInt(request.getParameter("walletId"));
		double amount = Double.parseDouble(request.getParameter("amount"));
		String type = request.getParameter("type");

		ApplicationDao dao = new ApplicationDao();
		Connection connection = (Connection) getServletContext().getAttribute("dbconnection");
		Wallet walletIdWallet = dao.getWalletWalletId(walletId, connection);
		Double walletIdAmount = walletIdWallet.getAmount();
		Double newWalletAmount = walletIdAmount + amount;

		/*
		 * request.setAttribute("walletId", walletId);
		 * request.setAttribute("walletIdAmount", walletIdAmount);
		 * request.setAttribute("transactedAmount", amount);
		 * request.setAttribute("newWalletAmount", newWalletAmount);
		 */
		hashMap.put("transactionId", transactionId);

		hashMap.put("walletId", walletId);
		hashMap.put("walletIdAmount", walletIdAmount);
		hashMap.put("transactedAmount", amount);
		hashMap.put("newWalletAmount", newWalletAmount);

		if (type == "transfer") {
			Integer receiverId = Integer.parseInt(request.getParameter("receiverId"));
			Wallet receiverIdWallet = dao.getWalletWalletId(receiverId, connection);
			Double receiverIdAmount = receiverIdWallet.getAmount();
			// for transfer transaction, amount will be negative,
			// doing this will sum the amount, adding into the receiver's account
			Double newReceiverAmount = receiverIdAmount - amount;

			/*
			 * request.setAttribute("type", type); request.setAttribute("receiverId",
			 * receiverId); request.setAttribute("receiverIdAmount", receiverIdAmount);
			 * request.setAttribute("newReceiverAmount", newReceiverAmount);
			 */

			hashMap.put("type", type);
			hashMap.put("receiverId", receiverId);
			hashMap.put("receiverIdAmount", receiverIdAmount);
			hashMap.put("newReceiverAmount", newReceiverAmount);
		}
		// use gson to turn hashmap into json
		String json = new Gson().toJson(hashMap);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}

}
