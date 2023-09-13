package com.bankwithfargo.repository;

import com.bankwithfargo.dto.AccountRequestDTO;
import com.bankwithfargo.model.Account;
import com.bankwithfargo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
