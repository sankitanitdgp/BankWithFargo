package com.bankwithfargo.service;

import com.bankwithfargo.dto.AccountNoDTO;
import com.bankwithfargo.dto.UserSignupRequestDTO;
import com.bankwithfargo.entity.Account;
import com.bankwithfargo.entity.User;
import com.bankwithfargo.repository.AccountRepository;
import com.bankwithfargo.repository.UserLoginRepository;
import com.bankwithfargo.security.JwtTokenProvider;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserLoginRepository userRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    public UserService(UserLoginRepository userRepository, AccountRepository accountRepository, JwtTokenProvider jwtTokenProvider){
        this.userRepository=userRepository;
        this.accountRepository=accountRepository;
        this.jwtTokenProvider=jwtTokenProvider;
    }

    public List<Account> getAllUsers(User user){
        if(user.getEmail().equals("admin6@gmail.com")) {
            List<Account> accounts = accountRepository.findAll();
            return accounts;

        }
        else return null;
//        ArrayList<UserLoginRequestDTO> returnValue = new ArrayList<>();
//        BeanUtils.copyProperties(users, returnValue);
//        return returnValue;
    }

    @Transactional
    public ResponseEntity<Object> createUser(UserSignupRequestDTO user){

       User u= userRepository.findOneByEmail(user.getEmail());

        if(u==null) {
            try {
                User newUser = new User();
                BeanUtils.copyProperties(user, newUser);
                User createdUser = userRepository.save(newUser);
                return ResponseEntity.ok(createdUser);
            } catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("User already exists",HttpStatus.BAD_REQUEST);
        }
    }

    public Account getUserByAccountNo(AccountNoDTO accountNoDTO, User user) {
        if (user.getEmail().equals("admin6@gmail.com")){
        Account account= accountRepository.findByAccountNumber(accountNoDTO.getAccNo());
        return account;
        } else{
            return null;
        }

    }

}
