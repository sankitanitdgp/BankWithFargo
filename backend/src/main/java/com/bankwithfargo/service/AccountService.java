package com.bankwithfargo.service;

import com.bankwithfargo.dto.*;
import com.bankwithfargo.entity.Account;
import com.bankwithfargo.entity.Transaction;
import com.bankwithfargo.entity.User;
import com.bankwithfargo.repository.AccountRepository;
import com.bankwithfargo.repository.TransactionRepository;
import com.bankwithfargo.repository.UserLoginRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UserLoginRepository userLoginRepository;

    @Autowired
    TransactionRepository transactionRepository;

    public AccountService(AccountRepository accountRepository, TransactionRepository transactionRepository){
        this.accountRepository=accountRepository;
        this.transactionRepository=transactionRepository;
    }

    public Account openAccount(AccountRequestDTO account, User user) {

            Account newAccount = new Account();
            BeanUtils.copyProperties(account, newAccount);
            newAccount.setAccountStatus(true);
            newAccount.setDateOfOpening(new Date());
            newAccount.setIfsc("IFSC0099123");
            newAccount.setBalance(0.0);

            if(user.getEmail().equals("admin6@gmail.com")){
                newAccount.setUser(userLoginRepository.findOneByEmail(account.getEmail()));
                newAccount.setEmail(account.getEmail());
            } else {
                newAccount.setUser(user);
                newAccount.setEmail(user.getEmail());
            }

            long accountNumber = new Random().nextInt(0, Integer.MAX_VALUE);
            newAccount.setAccountNumber(accountNumber);

            return accountRepository.save(newAccount);

    }

    public ResponseEntity<Object> getTransactions(TransactionRequestDTO transactionRequestDTO, User user) {
        Long accNo=transactionRequestDTO.getAccNo();
        Account account=accountRepository.findByAccountNumber(accNo);
        if(!user.getEmail().equals("admin6@gmail.com") && transactionRequestDTO.getMpin() != account.getMpin()){
            System.out.println(transactionRequestDTO.getMpin()+"\n"+account.getMpin());
            return new ResponseEntity<>("Incorrect MPIN", HttpStatus.UNAUTHORIZED);
        }
        List<Transaction> transactions= transactionRepository.findAllBySenderAccNoOrReceiverAccNo(accNo,accNo);
        return ResponseEntity.ok(transactions);
    }

    public Object checkBalance(CheckBalanceDTO checkBalanceDTO){
       Account account=accountRepository.findByAccountNumber(checkBalanceDTO.getAccNo());
       if(account.getMpin() != checkBalanceDTO.getMpin())
           return "Incorrect MPIN";
       else
           return account.getBalance();

    }
    public List<Account> getAllAccountsByUser(User user){
        try{
            return accountRepository.findAccountNumbersByEmail(user.getEmail());
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public String depositMoney(DepositMoneyDTO depositMoneyDTO){

            Account account=accountRepository.findByAccountNumber(depositMoneyDTO.getAccNo());
            if(depositMoneyDTO.getMpin() != account.getMpin()){
                return "Incorrect MPIN";
            } else if(depositMoneyDTO.getAmount()<0) {
                return "Amount cannot be negative";
            } else if(!account.getAccountStatus()) {
                return "Sorry! Your account has been disabled by admin";
            }
            else {
                account.setBalance(account.getBalance() + depositMoneyDTO.getAmount());
                accountRepository.save(account);
                return "Money deposited successfully!";
            }

    }

    public String withdrawMoney(DepositMoneyDTO depositMoneyDTO){
            Account account=accountRepository.findByAccountNumber(depositMoneyDTO.getAccNo());
            if(depositMoneyDTO.getMpin() != account.getMpin()){
                return "Incorrect MPIN";
            } else if(depositMoneyDTO.getAmount()<0){
                return "Amount cannot be negative";
            } else if(depositMoneyDTO.getAmount()>account.getBalance()){
                return "Insufficient balance";
            } else if(!account.getAccountStatus()) {
                return "Sorry! Your account has been disabled by admin";
            } else {
                account.setBalance(account.getBalance() - depositMoneyDTO.getAmount());
                accountRepository.save(account);
                return "Money withdrawn successfully!";
            }

    }

    public String transferMoney(TransferMoneyDTO transferMoneyDTO){
            Account senderAccount=accountRepository.findByAccountNumber(transferMoneyDTO.getSenderAccNumber());
//            Account receiverAccount=accountRepository.findByAccountNumber(transferMoneyDTO.getReceiverAccNumber());
            if(transferMoneyDTO.getMpin() != senderAccount.getMpin()){
                return "Incorrect MPIN";
            } else if(transferMoneyDTO.getAmount()<0){
                return "amount cannot be negative";
            } else if(transferMoneyDTO.getAmount()>senderAccount.getBalance()) {
                return "Insufficient balance";
            } else if(transferMoneyDTO.getSenderAccNumber().equals(transferMoneyDTO.getReceiverAccNumber())) {
                return "Cannot transfer money to same account";
            } else if(!senderAccount.getAccountStatus()) {
                return "Sorry! Your account has been disabled by admin";
            } else {
                Transaction transaction = new Transaction();
                transaction.setSenderAccNo(transferMoneyDTO.getSenderAccNumber());
                transaction.setReceiverAccNo(transferMoneyDTO.getReceiverAccNumber());
                transaction.setTimeStamp(new Date());
                transaction.setAmount(transferMoneyDTO.getAmount());

//                long transactionId = new Random().nextInt(0, Integer.MAX_VALUE);
//                transaction.setTransactionId(transactionId);

                transactionRepository.save(transaction);
                senderAccount.setBalance(senderAccount.getBalance() - transferMoneyDTO.getAmount());
                accountRepository.save(senderAccount);
//                receiverAccount.setBalance(receiverAccount.getBalance() + transferMoneyDTO.getAmount());
//                accountRepository.save(receiverAccount);

                return "Money transferred successfully!";
            }

    }

    @Transactional
    public Boolean changeAccountStatus(AccountNoDTO accountNoDTO,User user){
        if(user.getEmail().equals("admin6@gmail.com")){
            Account account=accountRepository.findByAccountNumber(accountNoDTO.getAccNo());
            account.setAccountStatus(!account.getAccountStatus());
            return account.getAccountStatus();
        } else {
            return null;
        }
    }

}
