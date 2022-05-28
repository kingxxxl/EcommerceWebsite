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
    final ProductService productService;
    final UserService userService;
    public CartService(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
        this.carts.addAll(
                List.of(
                        new Cart("801","301",new ArrayList<Product>()),
                        new Cart("802","302",new ArrayList<Product>()),
                        new Cart("803","303",new ArrayList<Product>()),
                        new Cart("804","304",new ArrayList<Product>())
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

    public boolean checkProductId(String productid) {
        return (productService.isProductByID(productid)) ? true : false;

    }



    private Cart getByUserId(String userid) {
        for (Cart c:carts) {
            if (c.getUserid().equals(userid)){
                return c;
            }
        }
        return null;
    }

    public boolean addProductToUser(String userid, String productid) {
        Product product = productService.getById(productid);
        if (!isUserCartByID(userid)){
            String newCartId = String.valueOf(carts.size()+1);
            carts.add(new Cart(newCartId,userid,new ArrayList<Product>()));
        }
        Cart cart = getByUserId(userid);
        ArrayList<Product> products = cart.getProductsList();
        products.add(product);
        cart.setProductsList(products);
        return true;
    }
    public boolean removeProductToUser(String userid, String productid) {
        Product product = productService.getById(productid);
        if (!isUserCartByID(userid)){
            String newCartId = String.valueOf(carts.size()+1);
            carts.add(new Cart(newCartId,userid,new ArrayList<Product>()));
        }
        Cart cart = getByUserId(userid);
        ArrayList<Product> products = cart.getProductsList();
        products.remove(product);
        cart.setProductsList(products);
        return true;
    }
    public boolean isUserCartByID(String userid) {
        for (Cart c:carts) {
            if (c.getUserid().equals(userid)){
                return true;
            }
        }
        return false ;
    }

    public boolean checkUserId(String userid) {
        for (User u:userService.users) {
            if (u.getId().equals(userid)){
                return true;
            }
        }
       return false;
    }
}
