package com.example.ecommercewebsite.controllers;

import com.example.ecommercewebsite.model.Cart;
import com.example.ecommercewebsite.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    private CartService cartService;
    public CartController(CartService cartService){
        this.cartService = cartService;
    }
    /**
     * Get all the domain data.
     */
    @GetMapping()
    public ResponseEntity<List> getCart(){
        return  ResponseEntity.status(HttpStatus.OK).body(cartService.getCarts());
    }
    /**
     * Get a specific data by passing an id.
     * @param id id of the data
     */
    @GetMapping("{id}")
    ResponseEntity<Object> getCartByID(@PathVariable String id){
        if (!cartService.isCartByID(id)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api("Not Found, no ride with that id", HttpStatus.BAD_REQUEST));
        }
        return  ResponseEntity.status(HttpStatus.OK).body(cartService.getById(id));
    }
    /**
     * Add new data.
     * @param cart date to be added
     * @param errors errors if any found from the date validation
     */
    @PostMapping()
    ResponseEntity<Api> addCart(@RequestBody @Valid Cart cart, Errors errors){
        try {
            check(errors);
            return (cartService.addCart(cart)) ? ResponseEntity.status(HttpStatus.CREATED).body(new Api("Adding was successful!", HttpStatus.CREATED)) : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Api("Adding was NOT successful!!", HttpStatus.INTERNAL_SERVER_ERROR));
        } catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api(e.getMessage(), HttpStatus.BAD_REQUEST));
        }
    }@PostMapping("/add")
    ResponseEntity<Api> addProductToUser(@PathParam("userid") String userid,@PathParam("productid") String productid){
        if(cartService.checkUserId(userid) && cartService.checkProductId(productid)) {
            return (cartService.addProductToUser(userid,productid)) ? ResponseEntity.status(HttpStatus.CREATED).body(new Api("Adding was successful!", HttpStatus.CREATED)) : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Api("Adding was NOT successful!!", HttpStatus.INTERNAL_SERVER_ERROR));
        } else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api("userid or productid is not valid", HttpStatus.BAD_REQUEST));
    }
    /**
     * Update/Create data by passing an id.
     * @param id id of the data
     */
    @PutMapping("{id}")
    public ResponseEntity<Api> putCart(@RequestBody @Valid Cart newCart, Errors errors, @PathVariable String id){
        try {
            check(errors);
            Cart cart = cartService.getById(id);
            if (cart != null) {
                cartService.updateCart(cart, newCart);
                return ResponseEntity.status(HttpStatus.OK).body(new Api("Updated successfully!", HttpStatus.OK));
            } else
                return addCart(newCart, errors);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api(e.getMessage(), HttpStatus.BAD_REQUEST));
        }
    }
    /**
     * Delete a data by passing an id.
     * @param id id of the data
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Api> deleteCart(@PathVariable String id){
        boolean status;
        status = cartService.deleteCart(id);
        return (!status) ? ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api("No data found!", HttpStatus.BAD_REQUEST))
                :          ResponseEntity.status(HttpStatus.OK).body(new Api("Successfully deleted!",HttpStatus.OK));
    }

    /**
     * Checks if they are any errors from the given errors class, if any error found throw the appropriate message.
     * @param errors
     */
    public void check(Errors errors) throws IllegalArgumentException{
        if (errors.hasErrors()){
            String error = errors.getFieldError().getDefaultMessage();
            throw new IllegalArgumentException(error);
        }
    }
}
