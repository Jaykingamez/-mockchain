<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<%@include file="/jsp/alert.jsp"%>
</head>
<body>
	<header>
		<%@include file="/html/navbar.html"%>
	</header>
	<main>
		<div class="container col-md-6 mb-5">
			<div class="card">
				<div class="card-body text-center">
					<h1 class="card-title">${sessionScope.username}'swallet</h1>
					<h3 class="card-subtitle mb-2 text-muted">Amount:
						$${String.format("%.2f", sessionScope.amount)}</h3>
				</div>
			</div>
			<div class="text-center mb-5">
				<a class="btn btn-success" href="personal">Personal</a> <a
					class="btn btn-danger" href="transfer">Transfer</a>
			</div>
			<c:if test="${requestScope.wallet == 'personal'}">
				<div class="container col-md-6 mb-5">
					<div class="card">
						<div class="card-body">
							<form action="personal" method="post">
								<h2>
									<fmt:formatDate value="${now}" pattern="dd-MM-yyyy" />
								</h2>
								<fieldset class="form-group">
									<label>Operator</label> <select name="operator">
										<option>+</option>
										<option>-</option>
									</select>
								</fieldset>
								<fieldset class="form-group">
									<label>Amount</label> <input type="number" step="0.01"
										class="form-control" name="amount" required="required">
								</fieldset>
								<button type="submit" class="btn btn-success">Submit</button>
							</form>
						</div>
					</div>
				</div>
			</c:if>
			<c:if test="${requestScope.wallet == 'transfer'}">
				<div class="container col-md-6 mb-5">
					<div class="card">
						<div class="card-body">
							<form action="transfer" method="post">
								<h2>
									<fmt:formatDate value="${now}" pattern="dd-MM-yyyy" />
								</h2>
								<fieldset class="form-group">
									<label>Receiver</label> <input type="text" class="form-control"
										name="receiver" required="required">
								</fieldset>
								<fieldset class="form-group">
									<label>Amount</label> <input type="number" step="0.01"
										class="form-control" name="amount" required="required">
								</fieldset>
								<button type="submit" class="btn btn-success">Submit</button>
							</form>
						</div>
					</div>
				</div>
			</c:if>
		</div>
	</main>
</body>
</html>