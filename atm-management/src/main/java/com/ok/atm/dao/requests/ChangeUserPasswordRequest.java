package com.ok.atm.dao.requests;

public class ChangeUserPasswordRequest {

    private Long identityNumber;
    private String oldPass;
    private String newPass;
    private String newPassAgain;


    public Long getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(Long identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getOldPass() {
        return oldPass;
    }

    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public String getNewPassAgain() {
        return newPassAgain;
    }

    public void setNewPassAgain(String newPassAgain) {
        this.newPassAgain = newPassAgain;
    }
}
