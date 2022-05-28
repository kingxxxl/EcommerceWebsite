package com.example.ecommercewebsite.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.annotation.Lazy;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;

@AllArgsConstructor
@Data @Lazy
public class Cart {
    @NotBlank
    @Size(min = 3, message = "id must be at least 3 character long")
    private String  id;
    @NotBlank
    @Size(min = 3, message = "user id must be at least 3 character long")
    private String  userid;
    @NotNull
    private ArrayList<Product> productsList;
}
