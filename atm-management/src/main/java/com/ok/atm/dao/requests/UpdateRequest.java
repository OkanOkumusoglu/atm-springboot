package com.ok.atm.dao.requests;

public class UpdateRequest {

	private long identityNumber;
	private String newName;
	private String newSurname;
	private String newMail;
	private long newPhoneNumber;

	public long getIdentityNumber() {
		return identityNumber;
	}

	public void setIdentityNumber(long identityNumber) {
		this.identityNumber = identityNumber;
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	public String getNewSurname() {
		return newSurname;
	}

	public void setNewSurname(String newSurname) {
		this.newSurname = newSurname;
	}

	public String getNewMail() {
		return newMail;
	}

	public void setNewMail(String newMail) {
		this.newMail = newMail;
	}

	public long getNewPhoneNumber() {
		return newPhoneNumber;
	}

	public void setNewPhoneNumber(long newPhoneNumber) {
		this.newPhoneNumber = newPhoneNumber;
	}
}
