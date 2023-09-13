package com.bankwithfargo.service;

import com.bankwithfargo.dto.AccountRequestDTO;
import com.bankwithfargo.model.Account;
import com.bankwithfargo.model.User;
import com.bankwithfargo.repository.AccountRepository;
import com.bankwithfargo.repository.UserLoginRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Random;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UserLoginRepository userLoginRepository;

    @Transactional
    public Account openAccount(AccountRequestDTO account){
        try{
            Account newAccount= new Account();
            BeanUtils.copyProperties(account, newAccount);
            newAccount.setAccountStatus(true);
            newAccount.setDateOfOpening(LocalDate.now());
            newAccount.setIfsc("IFSC0099123");
            newAccount.setBalance(0.0);

            long accountNumber= new Random().nextInt(0,Integer.MAX_VALUE);
            newAccount.setAccountNumber(accountNumber);

            User user = userLoginRepository.findOneByEmail(account.getEmail());
            newAccount.setUser(user);
            return accountRepository.save(newAccount);

        } catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
