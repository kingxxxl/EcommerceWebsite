package com.example.ecommercewebsite.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.ArrayList;

@AllArgsConstructor
@Data
public class Product {
    @NotBlank
    @Size(min = 3, message = "id must be at least 3 character long")
    private String  id;
    @NotBlank
    @Size(min = 3, message = "name must be at least 3 character long")
    private String  name;
    @NotNull
    @PositiveOrZero(message = "price must be 0 or more")
    private Integer price;
    @NotBlank
    @Size(min = 3, message = "categoryID must be at least 3 character long")
    private String categoryID;
    @NotNull
    private ArrayList<Comment> commentsList;
}
