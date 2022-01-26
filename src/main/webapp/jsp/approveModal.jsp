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
							<div class="card text-center">
								<div class="card-body">
									<h5>WalletId:</h5>
									<h5 id="walletId" class="card-title"></h5>
									<p>Original Amount:</p>
									<p id="walletIdAmount" class="card-text">${requestScope.walletIdAmount}</p>
									<p>Transacted Amount:</p>
									<p id="transactedAmount" class="card-text">${requestScope.transactedAmount}</p>
									<p>New Amount:</p>
									<p id="newWalletAmount" class="card-text">${requestScope.newWalletAmount}</p>
								</div>
							</div>
						</div>
						<c:if test="${requestScope.type != null}">
							<div class="col-sm">
								<div class="card">
									<div class="card-body">
										<h5 id="receiverId" class="card-title">${requestScope.receiverId}</h5>
										<p id="receiverIdAmount" class="card-text">${requestScope.receiverIdAmount}</p>
										<p id="negativeTransactedAmount" class="card-text">${-requestScope.transactedAmount}</p>
										<p id="newReceiverAmount" class="card-text">${requestScope.newReceiverAmount}</p>
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
