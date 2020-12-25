package com.ok.atm.dao.responses;

public class WithdrawMoneyResponse {

	private boolean success;
	private long newBalance; 
	private String message;

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public long getNewBalance() {
		return newBalance;
	}

	public void setNewBalance(long newBalance) {
		this.newBalance = newBalance;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
