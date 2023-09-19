package com.bankwithfargo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferMoneyDTO {
    private Long senderAccNumber;
    private Long receiverAccNumber;
    private Double amount;
    private int mpin;
}
