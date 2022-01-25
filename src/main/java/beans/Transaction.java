package beans;

import java.sql.Timestamp;
import java.util.Date;

public class Transaction {
	private int transactionId = 0;
	private Integer previousTransactionId;
	private Timestamp timestamp;
	private int walletId;
	private Integer receiverId;
	private double amount;
	private String type;
	private Boolean approve;
	
	
	/**
	 * Constructor for transactions
	 */
	public Transaction(int transactionId, Integer previoustransactionId, Timestamp timestamp,
			int walletId, Integer receiverId, double amount, String type, Boolean approve) {
		this.transactionId = transactionId;
		this.previousTransactionId = previoustransactionId;
		this.timestamp = timestamp;
		this.walletId = walletId;
		this.receiverId = receiverId;
		this.amount = amount;
		this.type = type;
		this.approve = approve;
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
	 * @return the previoustransactionId
	 */
	public Integer getPreviousTransactionId() {
		return previousTransactionId;
	}

	/**
	 * @param previoustransactionId the previoustransactionId to set
	 */
	public void setPrevioustransactionId(int previousTransactionId) {
		this.previousTransactionId = previousTransactionId;
	}

	/**
	 * @return the timestamp
	 */
	public Timestamp getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the walletId
	 */
	public int getWalletId() {
		return walletId;
	}

	/**
	 * @param walletId the walletId to set
	 */
	public void setWalletId(int walletId) {
		this.walletId = walletId;
	}

	/**
	 * @return the receiverId
	 */
	public Integer getReceiverId() {
		return receiverId;
	}

	/**
	 * @param receiverId the receiverId to set
	 */
	public void setReceiverId(int receiverId) {
		this.receiverId = receiverId;
	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the approve
	 */
	public Boolean getApprove() {
		return approve;
	}

	/**
	 * @param approve the approve to set
	 */
	public void setApprove(boolean approve) {
		this.approve = approve;
	}

}
