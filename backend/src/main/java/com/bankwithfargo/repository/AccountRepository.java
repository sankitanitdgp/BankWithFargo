package com.bankwithfargo.repository;

import com.bankwithfargo.dto.AccountRequestDTO;
import com.bankwithfargo.model.Account;
import com.bankwithfargo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByAccountNumber(Long accNo);
    List<Account> findAccountNumbersByEmail(String email);
}
