package com.bankwithfargo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    private Long transactionId;

    @Column(name="sender_acc_no", nullable = false)
    private Long senderAccNo;

    @Column(name="receiver_acc_no", nullable = false)
    private Long receiverAccNo;

    @Column(name="amount", nullable = false)
    private Float amount;

    @Column(name = "timestamp", nullable = false)
    @NotNull
    private Timestamp timeStamp;


}