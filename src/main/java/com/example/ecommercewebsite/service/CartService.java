package com.example.ecommercewebsite.service;

import com.example.ecommercewebsite.model.Cart;
import com.example.ecommercewebsite.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    List<Cart> carts = new ArrayList<>();

    public CartService() {
        this.carts.addAll(
                List.of(
                        new Cart("101","101",new ArrayList<Product>()),
                        new Cart("102","101",new ArrayList<Product>()),
                        new Cart("103","101",new ArrayList<Product>()),
                        new Cart("104","101",new ArrayList<Product>())
                ));
    }
    public List<Cart> getCarts(){
        return carts;
    }

    public void updateCart(Cart cart, Cart newCart){
        cart.setId(newCart.getId());
        cart.setUserid(newCart.getUserid());
        cart.setProductsList(newCart.getProductsList());
    }

    public boolean deleteCart(String id){

        if (isCartByID(id)){
            Cart cart = getById(id);
            getCarts().remove(cart);
            return true;
        }
        return false;
    }

    public boolean isCartByID(String id){
        int checkForWork = -1;
        Cart cart = getById(id);
        if (cart != null){
            checkForWork = Integer.parseInt(id);
        }
        return (checkForWork == -1) ? false :  true;
    }


    public boolean addCart(Cart cart){
        return carts.add(cart);
    }

    public  Cart getById(String id){
        for (Cart cart: this.carts) {
            if (cart.getId().equals(id)){
                return cart;
            }
        }
        return null;
    }

}
