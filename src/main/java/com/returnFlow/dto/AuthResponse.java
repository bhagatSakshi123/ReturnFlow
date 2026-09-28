package com.returnFlow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResponse {

    private Long userId;
    private String token;
    private String role;
    private String message;
}