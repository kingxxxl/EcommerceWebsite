package com.example.ecommercewebsite.controllers;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Data
public class Api {
    private String message;
    private HttpStatus status;
}