package com.example.ecommercewebsite.controllers;

import com.example.ecommercewebsite.model.Cart;
import com.example.ecommercewebsite.model.Product;
import com.example.ecommercewebsite.model.User;
import com.example.ecommercewebsite.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }
    /**
     * Get all the domain data.
     */
    @GetMapping()
    public ResponseEntity<List> getUser(){
        return  ResponseEntity.status(HttpStatus.OK).body(userService.getUsers());
    }
    /**
     * Get a specific data by passing an id.
     * @param id id of the data
     */
    @GetMapping("{id}")
    ResponseEntity<Object> getUserByID(@PathVariable String id){
        if (!userService.isUserByID(id)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api("Not Found, no ride with that id", HttpStatus.BAD_REQUEST));
        }
        return  ResponseEntity.status(HttpStatus.OK).body(userService.getById(id));
    }
    /**
     * Add new data.
     * @param user date to be added
     * @param errors errors if any found from the date validation
     */
    @PostMapping()
    ResponseEntity<Api> addUser(@RequestBody @Valid User user, Errors errors){
        try {
            check(errors);
            return (userService.addUser(user)) ? ResponseEntity.status(HttpStatus.CREATED).body(new Api("Adding was successful!", HttpStatus.CREATED)) : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Api("Adding was NOT successful!!", HttpStatus.INTERNAL_SERVER_ERROR));
        } catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api(e.getMessage(), HttpStatus.BAD_REQUEST));
        }
    }
    @PostMapping("/add-stock")
    ResponseEntity<Api> addProductToMerchant( @PathParam("merchantid") String merchantid, @RequestBody @Valid Product product, Errors errors, @PathParam("stock") String stock){
        try {
            check(errors);
            if(userService.checkMerchantId(merchantid) && Integer.parseInt(stock) > 10) {
                return (userService.addProductToMerchant(merchantid,product,stock)) ? ResponseEntity.status(HttpStatus.CREATED).body(new Api("Adding was successful!", HttpStatus.CREATED)) : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Api("Adding was NOT successful!!", HttpStatus.INTERNAL_SERVER_ERROR));
            } else
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api("user id, merchant id, product or stock  is not valid", HttpStatus.BAD_REQUEST));
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api(e.getMessage(), HttpStatus.BAD_REQUEST));
        }
    }
    @PutMapping("/buy")
    ResponseEntity<Api> addProductToMerchant( @PathParam("userid") String userid,@PathParam("productid") String productid,@PathParam("merchantid") String merchantid){

            if(userService.isUserByID(userid) && userService.checkProductId(productid) && userService.checkMerchantId(merchantid)){
                return (userService.buyNoCart(userid,productid,merchantid)) ? ResponseEntity.status(HttpStatus.CREATED).body(new Api("Adding was successful!", HttpStatus.CREATED)) : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Api("Adding was NOT successful!!", HttpStatus.INTERNAL_SERVER_ERROR));
            } else
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api("user id, merchant id, product or stock  is not valid", HttpStatus.BAD_REQUEST));

    }
    @PutMapping("/buy/cart")
    ResponseEntity<Api> addProductToMerchant(@RequestBody @Valid Cart cart, Errors errors) {
        try {
            check(errors);
            if (userService.checkCart(cart)) {
                return (userService.buyCart(cart)) ? ResponseEntity.status(HttpStatus.CREATED).body(new Api("purchase was successful!", HttpStatus.CREATED)) : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Api("purchase was NOT successful!!", HttpStatus.INTERNAL_SERVER_ERROR));
            } else
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api("some products or all are not valid", HttpStatus.BAD_REQUEST));
        }catch(IllegalArgumentException e){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api(e.getMessage(), HttpStatus.BAD_REQUEST));
            }
    }
    /**
     * Update/Create data by passing an id.
     * @param id id of the data
     */
    @PutMapping("{id}")
    public ResponseEntity<Api> putUser(@RequestBody @Valid User newUser, Errors errors, @PathVariable String id){
        try {
            check(errors);
            User user = userService.getById(id);
            if (user != null) {
                userService.updateUser(user, newUser);
                return ResponseEntity.status(HttpStatus.OK).body(new Api("Updated successfully!", HttpStatus.OK));
            } else
                return addUser(newUser, errors);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api(e.getMessage(), HttpStatus.BAD_REQUEST));
        }
    }
    /**
     * Delete a data by passing an id.
     * @param id id of the data
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Api> deleteUser(@PathVariable String id){
        boolean status;
        status = userService.deleteUser(id);
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
