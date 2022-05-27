package com.example.ecommercewebsite.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Data
public class Comment {
    @NotBlank
    @Size(min = 3, message = "id must be at least 3 character long")
    private String  id;
    @NotBlank
    @Size(min = 5, max = 5, message = "user id have to be 5 length long ")
    private String  userid;
    @NotBlank
    @Size(min = 6, max = 6, message = "message have to be 6 length long ")
    private String  message;
    @NotNull
    @Pattern(regexp = "[1-3]", message = "must be a number between 1 - 5")
    private Integer  rate;
}
