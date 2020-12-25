package com.ok.atm.dao.api.controllers.methods;

import com.ok.atm.dao.UserRepository;
import com.ok.atm.dao.requests.*;
import com.ok.atm.dao.responses.*;
import com.ok.atm.entities.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserControllerMethods {

    public static void transferMoney(User senderUser, User receiverUser, UserRepository userRepo, TransferMoneyRequest request,
                                     TransferMoneyResponse response) {
        senderUser.setBalance(senderUser.getBalance() - request.getAmount());
        response.setSenderNewBalance(senderUser.getBalance());
        receiverUser.setBalance(receiverUser.getBalance() + request.getAmount());
        userRepo.save(senderUser);
        userRepo.save(receiverUser);
        response.setSuccess(true);
        response.setMessage("Transfer Completed!");
    }

    public static void withdrawMoney(User user, UserRepository userRepo, WithdrawMoneyRequest request, WithdrawMoneyResponse response) {
        user.setBalance(user.getBalance() - request.getAmount());
        userRepo.save(user);
        response.setNewBalance(user.getBalance());
        response.setSuccess(true);
        response.setMessage("Successful!");
    }

    public static void updateUser(User user, UserRepository userRepo, UpdateRequest request, UpdateResponse response) {
        user.setName(request.getNewName());
        user.setSurname(request.getNewSurname());
        user.setMail(request.getNewMail());
        user.setPhoneNumber(request.getNewPhoneNumber());
        userRepo.save(user);
        response.setSuccess(true);
        response.setMessage("Updated!");
    }

    public static void depositMoney(User user, UserRepository userRepo, DepositMoneyRequest request, DepositMoneyResponse response) {
        user.setBalance(user.getBalance() + request.getAmount());
        userRepo.save(user);
        response.setSuccess(true);
        response.setMessage("Completed!");
        response.setNewBalance(user.getBalance());
    }

    public static void userCreateSave(User user, UserRepository userRepo, CreateUserRequest request, CreateUserResponse response) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12); // Strength set as 12

        user.setIdentityNumber(request.getIdentityNumber());
        user.setName(request.getName());
        user.setSurname(request.getSurname());

        String encodedPassword = encoder.encode(request.getPassword());
        user.setPassword(encodedPassword);

        user.setMail(request.getMail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setBalance(request.getBalance());
        response.setName(request.getName());
        response.setSurname(request.getSurname());
        response.setBalance(request.getBalance());
        response.setIdentityNumber(user.getIdentityNumber());
        response.setMessage("Created");
        response.setSuccess(true);
        userRepo.save(user);
    }

}
