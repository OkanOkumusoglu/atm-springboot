package com.ok.atm.dao.responses;

public class CreateUserResponse {

	private long identityNumber;
	private String name;
	private String surname;
	private long balance;
	private boolean success;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public long getIdentityNumber() {
		return identityNumber;
	}

	public void setIdentityNumber(long identityNumber) {
		this.identityNumber = identityNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public long getBalance() {
		return balance;
	}

	public void setBalance(long balance) {
		this.balance = balance;
	}

}
