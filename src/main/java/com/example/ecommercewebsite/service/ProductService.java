package com.example.ecommercewebsite.service;

import com.example.ecommercewebsite.model.Comment;
import com.example.ecommercewebsite.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    List<Product> products = new ArrayList<>();

    public ProductService() {
        this.products.addAll(
                List.of(
                        new Product("701","ball",350,"1",new ArrayList<Comment>()),
                        new Product("702","shirt",90,"2",new ArrayList<Comment>()),
                        new Product("703","mug",55,"3",new ArrayList<Comment>()),
                        new Product("704","car",120500,"4",new ArrayList<Comment>())
                ));
    }
    public List<Product> getProducts(){
        return products;
    }

    public void updateProduct(Product product, Product newProduct){
        product.setId(newProduct.getId());
        product.setName(newProduct.getName());
    }

    public boolean deleteProduct(String id){

        if (isProductByID(id)){
            Product product = getById(id);
            getProducts().remove(product);
            return true;
        }
        return false;
    }

    public boolean isProductByID(String id){
        int checkForWork = -1;
        Product product = getById(id);
        if (product != null){
            checkForWork = Integer.parseInt(id);
        }
        return (checkForWork == -1) ? false :  true;
    }


    public boolean addProduct(Product product){
        return products.add(product);
    }

    public  Product getById(String id){
        for (Product product: this.products) {
            if (product.getId().equals(id)){
                return product;
            }
        }
        return null;
    }
}
