package servlets;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.User;
import beans.Wallet;
import dao.ApplicationDao;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("entry", "login");
		request.getRequestDispatcher("/jsp/index.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		User user = new User(username, password);

		ApplicationDao dao = new ApplicationDao();
		Connection connection = (Connection) getServletContext().getAttribute("dbconnection");
		int userId = dao.validateUser(user, connection);
		
		String infoMessage = null;
		
		if (userId == -1) {
			infoMessage = "Sorry, that account does not exist!";
			request.setAttribute("infoMessage", infoMessage);
			request.getRequestDispatcher("/jsp/index.jsp").forward(request, response);
		}

		Wallet wallet = dao.getWallet(userId, connection);
		int walletId = wallet.getWalletId();
		Double amount = wallet.getAmount();
		
		if (amount == -1) {
			infoMessage = "Sorry, that wallet does not exist!";
			request.setAttribute("infoMessage", infoMessage);
			request.getRequestDispatcher("/jsp/index.jsp").forward(request, response);
		}

		//success
		infoMessage = "User login successfully!";
		request.setAttribute("infoMessage", infoMessage);
		request.getSession().setAttribute("username", username);
		request.getSession().setAttribute("userId", userId);
		request.getSession().setAttribute("walletId", walletId);
		request.getSession().setAttribute("amount", amount);
		response.sendRedirect("wallet");

	}

}
