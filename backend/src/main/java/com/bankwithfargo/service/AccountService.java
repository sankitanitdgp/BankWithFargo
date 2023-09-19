package com.bankwithfargo.service;

import com.bankwithfargo.dto.AccountRequestDTO;
import com.bankwithfargo.dto.CheckBalanceDTO;
import com.bankwithfargo.dto.DepositMoneyDTO;
import com.bankwithfargo.dto.TransferMoneyDTO;
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
import java.time.LocalDateTime;
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
       Account account=accountRepository.findByAccountNumber(checkBalanceDTO.getAccNo());
       if(account==null)
       {
           return "Account does not exist.";
       }

       if(account.getMpin() != checkBalanceDTO.getMpin())
       {
           return "Incorrect MPIN";
       }
       return account.getBalance();

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

    public String depositMoney(DepositMoneyDTO depositMoneyDTO){
        try{
            Account account=accountRepository.findByAccountNumber(depositMoneyDTO.getAccNo());
            if(depositMoneyDTO.getMpin() != account.getMpin()){
                return "Incorrect MPIN";
            }
            account.setBalance(account.getBalance() + depositMoneyDTO.getAmount());
            accountRepository.save(account);
            return "Deposit successful";
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return "An error occurred";
    }

    public String withdrawMoney(DepositMoneyDTO depositMoneyDTO){
        try{
            Account account=accountRepository.findByAccountNumber(depositMoneyDTO.getAccNo());
            if(depositMoneyDTO.getMpin() != account.getMpin()){
                return "Incorrect MPIN";
            } else if(depositMoneyDTO.getAmount()<0){
                return "amount cannot be negative";
            }else if(depositMoneyDTO.getAmount()>account.getBalance()){
                return "Insufficient balance";
            } else {
                account.setBalance(account.getBalance() - depositMoneyDTO.getAmount());
                accountRepository.save(account);
                return "Money withdrawn successfully";
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return "An error occurred";
    }

    public String transferMoney(TransferMoneyDTO transferMoneyDTO){
        try{
            Account senderAccount=accountRepository.findByAccountNumber(transferMoneyDTO.getSenderAccNumber());
            Account receiverAccount=accountRepository.findByAccountNumber(transferMoneyDTO.getReceiverAccNumber());
            if(transferMoneyDTO.getMpin() != senderAccount.getMpin()){
                return "Incorrect MPIN";
            } else if(transferMoneyDTO.getAmount()<0){
                return "amount cannot be negative";
            }else if(transferMoneyDTO.getAmount()>senderAccount.getBalance()) {
                return "Insufficient balance";
            } else {
                Transaction transaction = new Transaction();
                transaction.setSenderAccNo(transferMoneyDTO.getSenderAccNumber());
                transaction.setReceiverAccNo(transferMoneyDTO.getReceiverAccNumber());
                transaction.setTimeStamp(LocalDateTime.now());
                transaction.setAmount(transferMoneyDTO.getAmount());

//                long transactionId = new Random().nextInt(0, Integer.MAX_VALUE);
//                transaction.setTransactionId(transactionId);

                transactionRepository.save(transaction);
                senderAccount.setBalance(senderAccount.getBalance() - transferMoneyDTO.getAmount());
                accountRepository.save(senderAccount);
                receiverAccount.setBalance(receiverAccount.getBalance() + transferMoneyDTO.getAmount());
                accountRepository.save(receiverAccount);

                return "Transfer Successful";
            }

        } catch(Exception e){
            System.out.println(e.getMessage());
        }
        return "An error occurred";
    }

}
