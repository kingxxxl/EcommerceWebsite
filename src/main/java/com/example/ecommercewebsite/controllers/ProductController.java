package com.example.ecommercewebsite.controllers;

import com.example.ecommercewebsite.model.Comment;
import com.example.ecommercewebsite.model.Product;
import com.example.ecommercewebsite.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;
@RestController
@RequestMapping("/product")
public class ProductController {
    private ProductService productService;
    public ProductController(ProductService productService){
        this.productService = productService;
    }
    /**
     * Get all the domain data.
     */
    @GetMapping()
    public ResponseEntity<List> getProduct(){
        return  ResponseEntity.status(HttpStatus.OK).body(productService.getProducts());
    }
    /**
     * Get a specific data by passing an id.
     * @param id id of the data
     */
    @GetMapping("{id}")
    ResponseEntity<Object> getProductByID(@PathVariable String id){
        if (!productService.isProductByID(id)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api("Not Found, no ride with that id", HttpStatus.BAD_REQUEST));
        }
        return  ResponseEntity.status(HttpStatus.OK).body(productService.getById(id));
    }
    /**
     * Add new data.
     * @param product date to be added
     * @param errors errors if any found from the date validation
     */
    @PostMapping()
    ResponseEntity<Api> addProduct(@RequestBody @Valid Product product, Errors errors){
        try {
            check(errors);
            return (productService.addProduct(product)) ? ResponseEntity.status(HttpStatus.CREATED).body(new Api("Adding was successful!", HttpStatus.CREATED)) : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Api("Adding was NOT successful!!", HttpStatus.INTERNAL_SERVER_ERROR));
        } catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api(e.getMessage(), HttpStatus.BAD_REQUEST));
        }
    }
    @PostMapping("/comment")
    ResponseEntity<Api> addProduct(@PathParam("userid") String userid, @PathParam("productid") String productid, @RequestBody @Valid Comment comment, Errors errors){
        try {
            check(errors);
            return (productService.addComment(userid, productid, comment)) ? ResponseEntity.status(HttpStatus.CREATED).body(new Api("Adding comment was successful!", HttpStatus.CREATED)) : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Api("Adding was NOT successful!!", HttpStatus.INTERNAL_SERVER_ERROR));
        } catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api(e.getMessage(), HttpStatus.BAD_REQUEST));
        }
    }
    @GetMapping("/comment/{id}")
    ResponseEntity<Api> getAllComment(@PathVariable String id){
        Product product = productService.getById(id);
        if (product != null){
            return (product.getCommentsList().size() >0) ? ResponseEntity.status(HttpStatus.CREATED).body(new Api(product.getCommentsList().toString(), HttpStatus.CREATED)) : ResponseEntity.status(HttpStatus.OK).body(new Api("No comments for this product", HttpStatus.OK));

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api( "no product with that id!", HttpStatus.BAD_REQUEST));

    }

    @GetMapping("rate/{rate}")
    ResponseEntity<Object> getAllProductRate(@PathVariable String rate){
        Integer intRate = Integer.valueOf(rate);
        if (intRate > 0 && intRate < 6){
            return (productService.isAllByRate(intRate)) ? ResponseEntity.status(HttpStatus.CREATED).body(productService.getAllByRate(intRate)) : ResponseEntity.status(HttpStatus.OK).body(new Api("No product with rate of "+rate, HttpStatus.OK));

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api( "rate between 1 and 5!", HttpStatus.BAD_REQUEST));

    }
    /**
     * Update/Create data by passing an id.
     * @param id id of the data
     */
    @PutMapping("{id}")
    public ResponseEntity<Api> putProduct(@RequestBody @Valid Product newProduct, Errors errors, @PathVariable String id){
        try {
            check(errors);
            Product product = productService.getById(id);
            if (product != null) {
                productService.updateProduct(product, newProduct);
                return ResponseEntity.status(HttpStatus.OK).body(new Api("Updated successfully!", HttpStatus.OK));
            } else
                return addProduct(newProduct, errors);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api(e.getMessage(), HttpStatus.BAD_REQUEST));
        }
    }
    /**
     * Delete a data by passing an id.
     * @param id id of the data
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Api> deleteProduct(@PathVariable String id){
        boolean status;
        status = productService.deleteProduct(id);
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
