package com.example.ecommercewebsite.service;

import com.example.ecommercewebsite.model.Cart;
import com.example.ecommercewebsite.model.Product;
import com.example.ecommercewebsite.model.User;
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

//    public boolean checkProductId(String productid) {
//        return (productService.isProductByID(productid)) ? true : false;
//
//    }



    private Cart getByUserId(String userid) {
        for (Cart c:carts) {
            if (c.getUserid().equals(userid)){
                return c;
            }
        }
        return null;
    }

    private boolean isUserCartByID(String userid) {
        for (Cart c:carts) {
            if (c.getUserid().equals(userid)){
                return true;
            }
        }
        return false ;
    }
//    public boolean addProductToUser(String userid, String productid) {
//        Product product = productService.getById(productid);
//        if (!isUserCartByID(userid)){
//            String newCartId = String.valueOf(carts.size()+1);
//            carts.add(new Cart(newCartId,userid,new ArrayList<Product>()));
//        }
//        Cart cart = getByUserId(userid);
//        ArrayList<Product> products = cart.getProductsList();
//        products.add(product);
//        cart.setProductsList(products);
//        return true;
//    }
//    public boolean removeProductToUser(String userid, String productid) {
//        Product product = productService.getById(productid);
//        if (!isUserCartByID(userid)){
//            String newCartId = String.valueOf(carts.size()+1);
//            carts.add(new Cart(newCartId,userid,new ArrayList<Product>()));
//        }
//        Cart cart = getByUserId(userid);
//        ArrayList<Product> products = cart.getProductsList();
//        products.remove(product);
//        cart.setProductsList(products);
//        return true;
//    }
}
