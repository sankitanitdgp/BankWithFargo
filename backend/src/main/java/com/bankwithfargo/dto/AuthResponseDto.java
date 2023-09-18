package com.bankwithfargo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthResponseDto {

    private String accessToken;
    private String tokenType="Bearer";
}
