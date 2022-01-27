package servlets;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ApplicationDao;

@WebServlet("/approve")
public class ApproveServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ApplicationDao dao = new ApplicationDao();
		Connection connection = (Connection) getServletContext().getAttribute("dbconnection");
		request.setAttribute("transactions", dao.getAllTransactions("", 0, connection));
		request.getRequestDispatcher("/jsp/approve.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//temp fix, I dont know why ajax is making calls here, but this will work for now
		boolean ajax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
		if(ajax) {
			return;
		}
		
		String approve = request.getParameter("approve");
		String reject = request.getParameter("reject");
		int transactionId = Integer.parseInt(request.getParameter("transactionId"));
		int userId = (int) request.getSession().getAttribute("userId");
		
		ApplicationDao dao = new ApplicationDao();
		Connection connection = (Connection) getServletContext().getAttribute("dbconnection");
		
		int numberOfUsers = dao.getTotalNumberOfUsers(connection);
		// more than 50% of users, transaction is approved
		// get the number of users at 50%
		int halfNumberOfUsers = Math.floorDiv(numberOfUsers, 2);
		
		if(approve != null) {
			int approveBool = 1;
			dao.addApprove(transactionId, userId, approveBool, connection);
			int numberOfApprovals = dao.getApprove(transactionId,approveBool, connection);
			if(numberOfApprovals >= halfNumberOfUsers) {
				//approve transaction
				dao.updateTransactionApproval(transactionId, approveBool, connection);
			}
		} else {
			int rejectBool = 0;
			dao.addApprove(transactionId, userId, rejectBool, connection);
			int numberOfRejections = dao.getApprove(transactionId, rejectBool, connection);
			if(numberOfRejections >= halfNumberOfUsers) {
				//reject transaction
				dao.updateTransactionApproval(transactionId, rejectBool, connection);
			}
		}
		
		doGet(request, response);
	}
}
