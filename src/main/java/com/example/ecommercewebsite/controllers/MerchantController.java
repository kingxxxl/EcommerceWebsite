package com.example.ecommercewebsite.controllers;

import com.example.ecommercewebsite.model.Merchant;
import com.example.ecommercewebsite.service.MerchantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/merchant")
public class MerchantController {
    private MerchantService merchantService;
    public MerchantController(MerchantService merchantService){
        this.merchantService = merchantService;
    }
    /**
     * Get all the domain data.
     */
    @GetMapping()
    public ResponseEntity<List> getMerchant(){
        return  ResponseEntity.status(HttpStatus.OK).body(merchantService.getMerchants());
    }
    /**
     * Get a specific data by passing an id.
     * @param id id of the data
     */
    @GetMapping("{id}")
    ResponseEntity<Object> getMerchantByID(@PathVariable String id){
        if (!merchantService.isMerchantByID(id)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api("Not Found, no ride with that id", HttpStatus.BAD_REQUEST));
        }
        return  ResponseEntity.status(HttpStatus.OK).body(merchantService.getById(id));
    }
    /**
     * Add new data.
     * @param merchant date to be added
     * @param errors errors if any found from the date validation
     */
    @PostMapping()
    ResponseEntity<Api> addRide(@RequestBody @Valid Merchant merchant, Errors errors){
        try {
            check(errors);
            return (merchantService.addMerchant(merchant)) ? ResponseEntity.status(HttpStatus.CREATED).body(new Api("Adding was successful!", HttpStatus.CREATED)) : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Api("Adding was NOT successful!!", HttpStatus.INTERNAL_SERVER_ERROR));
        } catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api(e.getMessage(), HttpStatus.BAD_REQUEST));
        }
    }
    /**
     * Update/Create data by passing an id.
     * @param id id of the data
     */
    @PutMapping("{id}")
    public ResponseEntity<Api> putMerchant(@RequestBody @Valid Merchant newMerchant, Errors errors, @PathVariable String id){
        try {
            check(errors);
            Merchant merchant = merchantService.getById(id);
            if (merchant != null) {
                merchantService.updateMerchant(merchant, newMerchant);
                return ResponseEntity.status(HttpStatus.OK).body(new Api("Updated successfully!", HttpStatus.OK));
            } else
                return addRide(newMerchant, errors);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api(e.getMessage(), HttpStatus.BAD_REQUEST));
        }
    }
    /**
     * Delete a data by passing an id.
     * @param id id of the data
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Api> deleteMerchant(@PathVariable String id){
        boolean status;
        status = merchantService.deleteMerchant(id);
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
