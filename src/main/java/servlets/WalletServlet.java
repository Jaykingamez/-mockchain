package servlets;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Wallet;
import dao.ApplicationDao;

/**
 * Servlet implementation class WalletServlet
 */
@WebServlet("/wallet")
public class WalletServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int walletId = (int) request.getSession().getAttribute("walletId");
		
		ApplicationDao dao = new ApplicationDao();
		Connection connection = (Connection) getServletContext().getAttribute("dbconnection");
		
		Wallet wallet = dao.getWalletWalletId(walletId, connection);
		Double amount = wallet.getAmount();
		
		request.getSession().setAttribute("amount", amount);
		request.getRequestDispatcher("/jsp/wallet.jsp").forward(request, response);
	}

}
