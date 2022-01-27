package beans;

public class Approve {
	private int approveId;
	private int userId;
	private int transactionId;
	private boolean approve;

	public Approve(int approveId, int userId, int transactionId, boolean approve) {
		this.approveId = approveId;
		this.userId = userId;
		this.transactionId = transactionId;
		this.approve = approve;
	}

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @return the approveId
	 */
	public int getApproveId() {
		return approveId;
	}

	/**
	 * @param approveId the approveId to set
	 */
	public void setApproveId(int approveId) {
		this.approveId = approveId;
	}

	/**
	 * @return the transactionId
	 */
	public int getTransactionId() {
		return transactionId;
	}

	/**
	 * @param transactionId the transactionId to set
	 */
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * @return the approve
	 */
	public boolean isApprove() {
		return approve;
	}

	/**
	 * @param approve the approve to set
	 */
	public void setApprove(boolean approve) {
		this.approve = approve;
	}

}
