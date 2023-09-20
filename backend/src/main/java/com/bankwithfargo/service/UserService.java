package com.bankwithfargo.service;

import com.bankwithfargo.dto.AccountNoDTO;
import com.bankwithfargo.dto.UserLoginRequestDTO;
import com.bankwithfargo.dto.UserSignupRequestDTO;
import com.bankwithfargo.model.Account;
import com.bankwithfargo.model.User;
import com.bankwithfargo.repository.AccountRepository;
import com.bankwithfargo.repository.UserLoginRepository;
import com.bankwithfargo.security.JwtTokenProvider;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
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
    public List<Account> getAllUsers(User user){
        if(user.getEmail().equals("admin6@gmail.com"))
            return accountRepository.findAll();
        else return null;
//        ArrayList<UserLoginRequestDTO> returnValue = new ArrayList<>();
//        BeanUtils.copyProperties(users, returnValue);
//        return returnValue;
    }

    @Transactional
    public String createUser(UserSignupRequestDTO user){

        User u= userRepository.findOneByEmail(user.getEmail());
        if(u==null) {
            try {
                User newUser = new User();
                BeanUtils.copyProperties(user, newUser);
                userRepository.save(newUser);
                return "User Registered Successfully";
            } catch (Exception e) {
                return e.getMessage();
            }
        } else {
            return "User already exists";
        }
    }

    public Account getUserByAccountNo(AccountNoDTO accountNoDTO, User user) {
        System.out.println("account no - " + accountNoDTO.getAccNo());
        if (user.getEmail().equals("admin6@gmail.com")){
            System.out.println("in admin");
        return accountRepository.findByAccountNumber(accountNoDTO.getAccNo());
        } else{
            System.out.println("in user");
            return null;
        }

    }

}
