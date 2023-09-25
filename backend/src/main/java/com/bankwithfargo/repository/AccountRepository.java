package com.bankwithfargo.repository;

import com.bankwithfargo.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByAccountNumber(Long accNo);
    List<Account> findAccountNumbersByEmail(String email);
}
