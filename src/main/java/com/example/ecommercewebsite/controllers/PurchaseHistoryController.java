package com.example.ecommercewebsite.controllers;

import com.example.ecommercewebsite.model.PurchaseHistory;
import com.example.ecommercewebsite.service.PurchaseHistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/purchasehistory")
public class PurchaseHistoryController {
    private PurchaseHistoryService purchaseHistoryService;
    public PurchaseHistoryController(PurchaseHistoryService purchaseHistoryService){
        this.purchaseHistoryService = purchaseHistoryService;
    }
    /**
     * Get all the domain data.
     */
    @GetMapping()
    public ResponseEntity<List> getPurchaseHistory(){
        return  ResponseEntity.status(HttpStatus.OK).body(purchaseHistoryService.getPurchaseHistorys());
    }
    /**
     * Get a specific data by passing an id.
     * @param id id of the data
     */
    @GetMapping("{id}")
    ResponseEntity<Object> getPurchaseHistoryByID(@PathVariable String id){
        if (!purchaseHistoryService.isPurchaseHistoryByID(id)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api("Not Found, no ride with that id", HttpStatus.BAD_REQUEST));
        }
        return  ResponseEntity.status(HttpStatus.OK).body(purchaseHistoryService.getById(id));
    }
    /**
     * Add new data.
     * @param purchaseHistory date to be added
     * @param errors errors if any found from the date validation
     */
    @PostMapping()
    ResponseEntity<Api> addPurchaseHistory(@RequestBody @Valid PurchaseHistory purchaseHistory, Errors errors){
        try {
            check(errors);
            return (purchaseHistoryService.addPurchaseHistory(purchaseHistory)) ? ResponseEntity.status(HttpStatus.CREATED).body(new Api("Adding was successful!", HttpStatus.CREATED)) : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Api("Adding was NOT successful!!", HttpStatus.INTERNAL_SERVER_ERROR));
        } catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api(e.getMessage(), HttpStatus.BAD_REQUEST));
        }
    }
    /**
     * Update/Create data by passing an id.
     * @param id id of the data
     */
    @PutMapping("{id}")
    public ResponseEntity<Api> putPurchaseHistory(@RequestBody @Valid PurchaseHistory newPurchaseHistory, Errors errors, @PathVariable String id){
        try {
            check(errors);
            PurchaseHistory purchaseHistory = purchaseHistoryService.getById(id);
            if (purchaseHistory != null) {
                purchaseHistoryService.updatePurchaseHistory(purchaseHistory, newPurchaseHistory);
                return ResponseEntity.status(HttpStatus.OK).body(new Api("Updated successfully!", HttpStatus.OK));
            } else
                return addPurchaseHistory(newPurchaseHistory, errors);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api(e.getMessage(), HttpStatus.BAD_REQUEST));
        }
    }
    /**
     * Delete a data by passing an id.
     * @param id id of the data
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Api> deletePurchaseHistory(@PathVariable String id){
        boolean status;
        status = purchaseHistoryService.deletePurchaseHistory(id);
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
