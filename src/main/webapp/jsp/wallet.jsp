<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
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
					<h1 class="card-title">${sessionScope.username}'s wallet</h1>
					<h3 class="card-subtitle mb-2 text-muted">Amount:
						$${String.format("%.2f", sessionScope.amount)}</h3>
				</div>
			</div>
		</div>
	</main>

</body>
</html>