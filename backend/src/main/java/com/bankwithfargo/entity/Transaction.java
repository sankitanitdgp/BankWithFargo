package com.bankwithfargo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @TableGenerator(name = "Address_Gen", initialValue = 10000)
    @GeneratedValue(strategy=GenerationType.TABLE, generator = "Address_Gen")
    private Long transactionId;

    @Column(name="sender_acc_no", nullable = false)
    private Long senderAccNo;

    @Column(name="receiver_acc_no", nullable = false)
    private Long receiverAccNo;

    @Column(name="amount", nullable = false)
    private Double amount;

    @Column(name = "timestamp", nullable = false)
    @NotNull
    private Date timeStamp;


}