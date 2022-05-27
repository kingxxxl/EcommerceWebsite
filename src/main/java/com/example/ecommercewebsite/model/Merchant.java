package com.example.ecommercewebsite.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Data
public class Merchant {
    @NotBlank
    @Size(min = 3, message = "id must be at least 3 character long")
    private String  id;
    @NotBlank
    @Size(min = 3, message = "name must be at least 3 character long")
    private String  name;
}
