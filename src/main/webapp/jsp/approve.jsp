<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js"
	integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh"
	crossorigin="anonymous"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js"
	integrity="sha384-alpBpkh1PFOepccYVYDB4do5UnbKysX5WZXm3XxPqe5iKTfUKjNkCk9SaVuEZflJ"
	crossorigin="anonymous"></script>
<script type="text/javascript" src="<c:url value="/js/index.js"/>"></script>
</head>
<body>
	<%@include file="/html/navbar.html"%>\
	<%@include file="/jsp/approveModal.jsp"%>
	<div class="row">
		<div class="container">
			<h3 class="text-center">List of Transactions needing approval</h3>
			<hr>
			<br>
			<!-- Create a table to list out all current users information -->
			<table class="table">
				<thead>
					<tr>
						<th>TransactionId</th>
						<th>PreviousTransactionId</th>
						<th>Timestamp</th>
						<th>WalletId</th>
						<th>ReceiverId</th>
						<th>Amount</th>
						<th>Type</th>
						<th>Approve</th>
					</tr>
				</thead>
				<!-- Pass in the list of users receive via the Servlet’s response to a loop-->

				<tbody>
					<c:forEach var="transaction" items="${transactions}">
						<!-- For each user in the database, display their information accordingly -->
						<tr>
							<td><c:out value="${transaction.transactionId}" /></td>
							<td><c:out value="${transaction.previousTransactionId}" /></td>
							<td><c:out value="${transaction.timestamp}" /></td>
							<td><c:out value="${transaction.walletId}" /></td>
							<td><c:out value="${transaction.receiverId}" /></td>
							<td><c:out value="${transaction.amount}" /></td>
							<td><c:out value="${transaction.type}" /></td>
							<td><form id="approve${transactions.indexOf(transaction)}"
									class="prevent" action="approveModal" method="post">
									<input type="hidden" type="number" name="transactionId"
										value=<c:out value="${transaction.transactionId}" />>
									<input type="hidden" type="number" name="walletId"
										value=<c:out value="${transaction.walletId}" />> <input
										type="hidden" type="number" step="0.01" name="amount"
										value=<c:out value="${transaction.amount}" />> <input
										type="hidden" type="number" name="receiverId"
										value=<c:out value="${transaction.receiverId}" />> <input
										type="hidden" type="text" name="type"
										value=<c:out value="${transaction.type}" />>
									<button class="btn btn-success" type="submit"
										data-toggle="modal" data-target="#approveModal">Approve</button>
								</form></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>