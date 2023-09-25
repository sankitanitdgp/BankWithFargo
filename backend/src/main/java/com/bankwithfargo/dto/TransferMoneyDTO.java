package com.bankwithfargo.dto;

import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "account number cannot be null")
    private Long receiverAccNumber;
    private Double amount;
    private int mpin;
}
