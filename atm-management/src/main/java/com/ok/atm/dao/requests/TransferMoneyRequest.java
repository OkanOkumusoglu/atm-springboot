package com.ok.atm.dao.requests;

public class TransferMoneyRequest {

	private long senderIdentityNumber;
	private long receiverIdentityNumber;
	private long amount;

	public long getSenderIdentityNumber() {
		return senderIdentityNumber;
	}

	public void setSenderIdentityNumber(long senderIdentityNumber) {
		this.senderIdentityNumber = senderIdentityNumber;
	}

	public long getReceiverIdentityNumber() {
		return receiverIdentityNumber;
	}

	public void setReceiverIdentityNumber(long receiverIdentityNumber) {
		this.receiverIdentityNumber = receiverIdentityNumber;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

}
