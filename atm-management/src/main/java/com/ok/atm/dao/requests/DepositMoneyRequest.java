package com.ok.atm.dao.requests;

public class DepositMoneyRequest {

	private long identityNumber;
	private long amount;

	public long getIdentityNumber() {
		return identityNumber;
	}

	public void setIdentityNumber(long identityNumber) {
		this.identityNumber = identityNumber;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}
}
