package com.bankwithfargo.service;

import com.bankwithfargo.dto.AccountRequestDTO;
import com.bankwithfargo.dto.CheckBalanceDTO;
import com.bankwithfargo.model.Account;
import com.bankwithfargo.model.Transaction;
import com.bankwithfargo.model.User;
import com.bankwithfargo.repository.AccountRepository;
import com.bankwithfargo.repository.TransactionRepository;
import com.bankwithfargo.repository.UserLoginRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UserLoginRepository userLoginRepository;

    @Autowired
    TransactionRepository transactionRepository;

    public Account openAccount(AccountRequestDTO account, User user) {
        try {
            Account newAccount = new Account();
            BeanUtils.copyProperties(account, newAccount);
            newAccount.setAccountStatus(true);
            newAccount.setDateOfOpening(LocalDate.now());
            newAccount.setIfsc("IFSC0099123");
            newAccount.setBalance(0.0);
            newAccount.setUser(user);
            newAccount.setEmail(user.getEmail());

            long accountNumber = new Random().nextInt(0, Integer.MAX_VALUE);
            newAccount.setAccountNumber(accountNumber);

            return accountRepository.save(newAccount);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Transaction> getTransactions(Long accNo) {
        List<Transaction> transactions=transactionRepository
                .findAllBySenderAccNoOrReceiverAccNo(accNo,accNo);

        return transactions;
    }

    public Object checkBalance(CheckBalanceDTO checkBalanceDTO){
       Optional<Account> account=accountRepository.findByAccountNumber(checkBalanceDTO.getAccNo());
       if(account.isEmpty())
       {
           return "Account does not exist.";
       }
       Account userAccount=account.get();
       if(userAccount.getMpin() != checkBalanceDTO.getMpin())
       {
           return "Incorrect MPIN";
       }
       return userAccount.getBalance();

    }
    public List<Account> getAllAccounts(User user){
        try{
            List<Account> accounts=accountRepository.findAccountNumbersByEmail(user.getEmail());
            return accounts;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

}
