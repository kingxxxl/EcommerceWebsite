package com.example.ecommercewebsite.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.annotation.Lazy;

import javax.validation.constraints.*;

@AllArgsConstructor
@Data
public class User {
    @NotBlank
    @Size(min = 3, message = "id must be at least 3 character long")
    private String  id;
    @NotBlank
    @Size(min = 5, max = 5, message = "user name have to be 5 length long ")
    private String  username;
    @NotNull
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z]).{6}$", message = "password need to be 6 length long , must have characters and digits")
    private String  password;
    @NotNull
    @Email(message = "not a valid email")
    private String  email;
    @NotNull
    @Pattern(regexp = "admin|customer", message = "role need to be “Admin” or”Customer")
    private String  role;
    @NotNull
    @Positive(message = "balance need to be positive")
    private Integer  balance;
}
