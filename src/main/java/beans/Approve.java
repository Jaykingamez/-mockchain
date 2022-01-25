package beans;

public class Approve {
	private int approveId;
	private int transactionId;
	private boolean approve;

	public Approve(int approveId, int transactionId, boolean approve) {
		super();
		this.approveId = approveId;
		this.transactionId = transactionId;
		this.approve = approve;
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
