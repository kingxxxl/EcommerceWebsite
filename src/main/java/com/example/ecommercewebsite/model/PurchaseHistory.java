package com.example.ecommercewebsite.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Data
public class PurchaseHistory {
    @NotBlank
    @Size(min = 3, message = "id must be at least 3 character long")
    private String  id;
    @NotBlank
    @Size(min = 3, message = "user id must be at least 3 character long")
    private String  userid;
    @NotBlank
    @Size(min = 3, message = "product id must be at least 3 character long")
    private String  productid;
    @NotNull
    @Positive(message = "need to be positive number")
    private Integer  price;
}
