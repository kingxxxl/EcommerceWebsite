package com.example.ecommercewebsite.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Data
public class MerchantStock {
    @NotBlank
    @Size(min = 3, message = "id must be at least 3 character long")
    private String  id;
    @NotBlank
    @Size(min = 3, message = "product id must be at least 3 character long")
    private String  productid;
    @NotBlank
    @Size(min = 3, message = "merchant id must be at least 3 character long")
    private String  merchantid;
    @Positive
    @Min(value = 11, message = "stock need to be more than 10 at start")
    private Integer  stock;

}
