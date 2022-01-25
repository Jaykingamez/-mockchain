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
</head>
<jsp:include page="alert.jsp" />
<body>
	<%@include file="/html/navbar.html"%>
	<div class="row">
		<div class="container">
			<h3 class="text-center">List of Approved Transactions</h3>
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
							<td><c:out value="${transaction.approve}" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>