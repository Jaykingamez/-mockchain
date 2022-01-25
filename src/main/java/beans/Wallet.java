package beans;

public class Wallet {
	private int walletId;
	private int userId;
	private double amount;

	public Wallet(int walletId, int userId, double amount) {
		this.walletId = walletId;
		this.userId = userId;
		this.amount = amount;
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

}
