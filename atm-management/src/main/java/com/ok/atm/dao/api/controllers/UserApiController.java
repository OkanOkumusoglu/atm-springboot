package com.ok.atm.dao.api.controllers;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import com.ok.atm.ValidationChecks;
import com.ok.atm.dao.api.controllers.methods.UserControllerMethods;
import com.ok.atm.dao.requests.*;
import com.ok.atm.dao.responses.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import com.ok.atm.dao.UserRepository;
import com.ok.atm.entities.User;

@RestController
@RequestMapping("/app-api/users")
public class UserApiController {

    @Autowired
    UserRepository userRepo;

    @GetMapping
    public List<User> getUsers() {
        return userRepo.findAll();
    }

    @RequestMapping(value = "/image/{identityNumber}", method = RequestMethod.GET,
            produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<InputStreamResource> getImage(@PathVariable("identityNumber") String identityNumber) throws IOException {

        ClassPathResource imgFile = new ClassPathResource("images/" + identityNumber + ".jpg");

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new InputStreamResource(imgFile.getInputStream()));
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateUserResponse create(@RequestBody @Valid CreateUserRequest request) {
        CreateUserResponse response = new CreateUserResponse();
        User user = new User();
        if (!userRepo.existsById(request.getIdentityNumber())) {
            if (ValidationChecks.isValidEmailAddress(request.getMail())) {
                if (ValidationChecks.isValidIdentityNumber(request.getIdentityNumber())) {
                    if (request.getPassword().equals(request.getCheckPassword())) {
                        UserControllerMethods.userCreateSave(user, userRepo, request, response);
                    } else {
                        response.setSuccess(false);
                        response.setMessage("Passwords doesn't match!");
                    }
                } else {
                    response.setSuccess(false);
                    response.setMessage("Identity number must be 11 digits!");
                }

            } else {
                response.setSuccess(false);
                response.setMessage("Wrong type of email address!");
            }

        } else {
            response.setSuccess(false);
            response.setMessage("Identity number already exist!");
        }


        return response;

    }

    @PostMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public UpdateResponse update(@RequestBody UpdateRequest request) {
        UpdateResponse response = new UpdateResponse();
        if (userRepo.existsById(request.getIdentityNumber())) {
            User user = userRepo.findById(request.getIdentityNumber()).get();

            UserControllerMethods.updateUser(user, userRepo, request, response);

        } else {
            response.setSuccess(false);
            response.setMessage("Identity number doesn't exist!");
        }
        return response;
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public DeleteResponse delete(@RequestBody DeleteRequest request) {
        DeleteResponse response = new DeleteResponse();
        if (!userRepo.existsById(request.getIdentityNumber())) {
            response.setSuccess(false);
            response.setMessage("Id doesn't exist!");
        } else {
            response.setSuccess(true);
            userRepo.deleteById(request.getIdentityNumber());
            response.setMessage("Completed!");
        }
        return response;
    }

    @PostMapping("/change-password")
    public ChangeUserPasswordResponse changeUSerPassword(@RequestBody ChangeUserPasswordRequest request) {
        ChangeUserPasswordResponse response = new ChangeUserPasswordResponse();
        if (userRepo.existsById(request.getIdentityNumber())) {
            User user = userRepo.findById(request.getIdentityNumber()).get();
            if (user.getPassword().equals(request.getOldPass())) {
                if (request.getNewPass().equals(request.getNewPassAgain())) {
                    user.setPassword(request.getNewPass());
                    userRepo.save(user);
                    response.setSuccess(true);
                    response.setMessage("Successful!");
                } else {
                    response.setSuccess(false);
                    response.setMessage("New passwords doesn't match!");
                }
            } else {
                response.setSuccess(false);
                response.setMessage("Old user password doesn't match!");
            }

        } else {
            response.setSuccess(false);
            response.setMessage("User doesn't exist!");
        }

        return response;
    }


    @PostMapping("/deposit")
    @ResponseStatus(HttpStatus.OK)
    public DepositMoneyResponse deposit(@RequestBody DepositMoneyRequest request) {
        DepositMoneyResponse response = new DepositMoneyResponse();
        if (userRepo.existsById(request.getIdentityNumber())) {
            User user = userRepo.findById(request.getIdentityNumber()).get();

            UserControllerMethods.depositMoney(user, userRepo, request, response);

        } else {
            response.setSuccess(false);
            response.setMessage("Identity number doesn't exist");
        }
        return response;

    }

    @PostMapping("/withdraw")
    @ResponseStatus(HttpStatus.OK)
    public WithdrawMoneyResponse withdraw(@RequestBody WithdrawMoneyRequest request) {
        WithdrawMoneyResponse response = new WithdrawMoneyResponse();
        if (userRepo.existsById(request.getIdentityNumber())) {
            User user = userRepo.findById(request.getIdentityNumber()).get();
            long userBalance = user.getBalance();
            if (userBalance < request.getAmount()) {
                response.setSuccess(false);
                response.setNewBalance(user.getBalance());
                response.setMessage("Insufficient balance");
            } else {
                UserControllerMethods.withdrawMoney(user, userRepo, request, response);
            }
        } else {
            response.setSuccess(false);
            response.setMessage("User doesn't exist!");
        }
        return response;
    }

    @PostMapping("/transfer")
    @ResponseStatus(HttpStatus.OK)
    public TransferMoneyResponse transfer(@RequestBody TransferMoneyRequest request) {
        TransferMoneyResponse response = new TransferMoneyResponse();
        User senderUser = userRepo.findById(request.getSenderIdentityNumber()).get();
        if (!userRepo.existsById(request.getReceiverIdentityNumber())) {
            response.setSuccess(false);
            response.setMessage("Receiver identity number doesn't exist");
        } else if (!userRepo.existsById(request.getSenderIdentityNumber())) {
            response.setSuccess(false);
            response.setMessage("Sender identity number doesn't exist!");
        } else {
            User receiverUser = userRepo.findById(request.getReceiverIdentityNumber()).get();
            if (senderUser.getBalance() < request.getAmount()) {
                response.setSuccess(false);
                response.setSenderNewBalance(senderUser.getBalance());
                response.setMessage("Insufficient balance");
            } else {
                UserControllerMethods.transferMoney(senderUser, receiverUser, userRepo, request, response);
            }
        }
        return response;
    }

    @PostMapping("/balance")
    public BalanceInfoResponse balanceInfo(@RequestBody BalanceInfoRequest request) {
        BalanceInfoResponse response = new BalanceInfoResponse();
        if (userRepo.existsById(request.getIdentityNumber())) {
            User user = userRepo.findById(request.getIdentityNumber()).get();
            response.setBalance(user.getBalance());
            response.setSuccess(true);
            response.setMessage("Successful!");
        } else {
            response.setSuccess(false);
            response.setMessage("User doesn't exist!");
        }

        return response;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        LoginResponse response = new LoginResponse();
        if (userRepo.existsById(request.getIdentityNumber())) {
            User user = userRepo.findById(request.getIdentityNumber()).get();
            if (BCrypt.checkpw(request.getPassword(), user.getPassword())) {
                response.setSuccess(true);
                response.setMessage("Successful!");
            } else {
                response.setSuccess(false);
                response.setMessage("Wrong password try again!");
            }
        } else {
            response.setSuccess(false);
            response.setMessage("User doesn't exist!");
        }

        return response;
    }


}
