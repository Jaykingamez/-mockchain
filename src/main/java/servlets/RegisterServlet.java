package servlets;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.User;
import dao.ApplicationDao;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("entry", "register");
		request.getRequestDispatcher("/jsp/index.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		User user = new User(username, password);
		
		ApplicationDao dao = new ApplicationDao();
		Connection connection = (Connection)getServletContext().getAttribute("dbconnection");
		int rows = dao.registerUser(user, connection);
		

		// prepare an information message for user about the success or failure of the operation
		String infoMessage = null;
		if(rows==-1){
			infoMessage="Sorry, an error occurred!";
			request.setAttribute("infoMessage", infoMessage);
			request.getRequestDispatcher("/jsp/index.jsp").forward(request, response);
		}
		else{
			infoMessage = "User registered successfully!";
			request.setAttribute("infoMessage", infoMessage);
			response.sendRedirect("transaction");
		}
	}

}
