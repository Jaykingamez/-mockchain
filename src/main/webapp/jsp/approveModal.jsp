<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!--Approve Transaction Modal-->
<div class="modal fade" id="approveModal" tabindex="-1" role="dialog"
	aria-labelledby="approveModal" aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="approveModalLabel">Approve
					Transaction</h5>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<div class="container">
					<div class="row">
						<div class="col-sm">
							<div class="card">
								<div class="card-body">
									<h5 class="card-title">${requestScope.walletId}</h5>
									<p class="card-text">${requestScope.walletIdAmount}</p>
									<p class="card-text">Change</p>
									<p class="card-text">New amount</p>
								</div>
							</div>
						</div>
						<c:if test="${requestScope.type != null}">
							<div class="col-sm">
								<div class="card">
									<div class="card-body">
										<h5 class="card-title">Wallet Id</h5>
										<p class="card-text">Old amount</p>
										<p class="card-text">Change</p>
										<p class="card-text">New amount</p>
									</div>
								</div>
							</div>
						</c:if>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<form action="" method="post">
					<button type="submit" class="btn btn-success" value="approve"
						data-dismiss="modal">Approve</button>
					<button type="submit" class="btn btn-danger" value="reject"
						data-dismiss="modal">Reject</button>
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Close</button>
				</form>
			</div>
		</div>
	</div>
</div>
