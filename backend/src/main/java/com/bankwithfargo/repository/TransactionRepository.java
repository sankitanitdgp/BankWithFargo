package com.bankwithfargo.repository;

import com.bankwithfargo.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllBySenderAccNoOrReceiverAccNo(Long senderAccNo, Long receiverAccNo);
}
