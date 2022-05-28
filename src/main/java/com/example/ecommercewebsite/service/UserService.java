package com.example.ecommercewebsite.service;

import com.example.ecommercewebsite.model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class UserService {
    List<User> users = new ArrayList<>();
    final MerchantStockService merchantStockService;
    final PurchaseHistoryService purchaseHistoryService;
    final CartService cartService;
    public UserService(MerchantService merchantService, MerchantStockService merchantStockService, PurchaseHistoryService purchaseHistoryService, CartService cartService) {
        this.merchantStockService = merchantStockService;
        this.purchaseHistoryService = purchaseHistoryService;
        this.cartService = cartService;
        this.users.addAll(
                List.of(
                        new User("101","Abdullah","a123456","email@email.com","admin",1000),
                        new User("102","Ahmed","a123456","email@email.com","admin",100),
                        new User("103","Salah","a123456","email@email.com","admin",100),
                        new User("104","Ali","a123456","email@email.com","admin",100)
                ));
    }



    public List<User> getUsers(){
        return users;
    }

    public void updateUser(User user, User newUser){
        user.setId(newUser.getId());
        user.setUsername(newUser.getUsername());
        user.setPassword(newUser.getPassword());
        user.setEmail(newUser.getEmail());
        user.setRole(newUser.getRole());
        user.setBalance(newUser.getBalance());
    }

    public boolean deleteUser(String id){

        if (isUserByID(id)){
            User user = getById(id);
            getUsers().remove(user);
            return true;
        }
        return false;
    }

    public boolean isUserByID(String id){
        int checkForWork = -1;
        User user = getById(id);
        if (user != null){
            checkForWork = Integer.parseInt(id);
        }
        return (checkForWork == -1) ? false :  true;
    }


    public boolean addUser(User user){
        return users.add(user);
    }

    public  User getById(String id){
        for (User user: this.users) {
            if (user.getId().equals(id)){
                return user;
            }
        }
        return null;
    }

    public boolean checkMerchantId(String merchantid) {
        return merchantStockService.isMerchantByID(merchantid);
    }

    public boolean addProductToMerchant(String merchantid, Product product, String stock) {
    //Merchant merchant = merchantStockService.getMerchantByID(merchantid);
        if(!merchantStockService.isMerchantStockByMerchantAndProductID(merchantid, product.getId())){
            String newId = String.valueOf(merchantStockService.merchantStocks.size()+1);
            MerchantStock newMerchantStock = new MerchantStock(newId, product.getId(), merchantid, Integer.parseInt(stock));
            merchantStockService.merchantStocks.add(newMerchantStock);
        }else {
            MerchantStock merchantStock = merchantStockService.getMerchantByID(merchantid, product.getId());
            merchantStock.setStock(merchantStock.getStock()+Integer.parseInt(stock));
        }

        return true;
    }

    public boolean isUserAdminByID(String userid) {
        return (getById(userid).equals("admin")) ? true : false;
    }

    public boolean checkProductId(String productid) {
        return merchantStockService.productService.isProductByID(productid);
    }

    public boolean buyNoCart(String userid, String productid, String merchantid) {
        User user = getById(userid);
        Product product = merchantStockService.productService.getById(productid);
        Merchant merchant = merchantStockService.getMerchantByID(merchantid);
        MerchantStock merchantStock = merchantStockService.getMerchantByID(merchantid,productid);
        if (!(merchantStock.getStock() > 0) || !(user.getBalance() > product.getPrice())){
            return false;
        }
        merchantStock.setStock(merchantStock.getStock()-1);
        user.setBalance(user.getBalance()-product.getPrice());
        String newPurchaseHistoryId = String.valueOf(purchaseHistoryService.purchaseHistorys.size()+1);
        purchaseHistoryService.purchaseHistorys.add(new PurchaseHistory(newPurchaseHistoryId, userid, productid,product.getPrice()));
        System.out.println("I was here3");
        return true;
    }

    public boolean checkCart(Cart cart) {
        for (Product p: cart.getProductsList()) {
            if(!merchantStockService.productService.isProductByID(p.getId())){
                return false;
            }
        }
        return true;
    }

    public boolean buyCart(Cart cart) {
        boolean what = isEnoughStock(cart);
        System.out.println(what);
        if (!isEnoughMoney(cart) ||!isEnoughStock(cart)){
            return false;
        }
        for (Product p:cart.getProductsList()) {
            String merchantId = merchantStockService.getMerchantByProductID(p.getId());
            if (!buyNoCart(cart.getUserid(), p.getId(),merchantId)){
                return false;
            }
        }
        return true;
    }

    public boolean isEnoughMoney(Cart cart){
        User user = getById(cart.getUserid());
        Integer userBalance = user.getBalance();
        Integer count = 0;
        for (Product p:cart.getProductsList()){
            count+=p.getPrice();
        }
        return userBalance >= count;
    }
    public boolean isEnoughStock(Cart cart){
        HashMap<Product,Integer> count = new HashMap();
        for (Product p:cart.getProductsList()){
            Product product = merchantStockService.productService.getById(p.getId());
            if (count.containsKey(p)){
                count.replace(p, (count.get(p))+1);
            }
            else {
                count.put(p,1);
            }
        }

        System.out.println(count);
        for (Product p: cart.getProductsList()) {
            System.out.println("p= "+p.getId()+"count.p= "+count.get(p));
            if (count.get(p) > merchantStockService.getStockByproductId(p.getId())){
                return false;
            }
        }
        return true;
    }
}
