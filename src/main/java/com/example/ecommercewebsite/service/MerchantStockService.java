package com.example.ecommercewebsite.service;

import com.example.ecommercewebsite.model.Merchant;
import com.example.ecommercewebsite.model.MerchantStock;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MerchantStockService {

    List<MerchantStock> merchantStocks = new ArrayList<>();
    final MerchantService merchantService;
    final ProductService productService;


    public MerchantStockService(MerchantService merchantService, ProductService productService) {
        this.merchantService = merchantService;
        this.productService = productService;
        this.merchantStocks.addAll(
                List.of(
                        new MerchantStock("1","701","301",1),
                        new MerchantStock("2","702","302",1),
                        new MerchantStock("3","703","303",1),
                        new MerchantStock("4","704","304",1)
                ));
    }
    public List<MerchantStock> getMerchantStocks(){
        return merchantStocks;
    }

    public void updateMerchantStock(MerchantStock merchantStock, MerchantStock newMerchantStock){
        merchantStock.setId(newMerchantStock.getId());
        merchantStock.setProductid(newMerchantStock.getProductid());
        merchantStock.setMerchantid(newMerchantStock.getMerchantid());
        merchantStock.setStock(newMerchantStock.getStock());
    }

    public boolean deleteMerchantStock(String id){

        if (isMerchantStockByID(id)){
            MerchantStock merchantStock = getById(id);
            getMerchantStocks().remove(merchantStock);
            return true;
        }
        return false;
    }

    public boolean isMerchantStockByID(String id){
        return getById(id) != null;
    }public boolean isMerchantStockByMerchantAndProductID(String merchantid,String productid){
        return getMerchantStockByMerchantAndProductID(merchantid,productid) != null;
    }

    private MerchantStock getMerchantStockByMerchantAndProductID(String merchantid, String productid) {
        for (MerchantStock merchantStock: this.merchantStocks) {
            if (merchantStock.getMerchantid().equals(merchantid) && merchantStock.getProductid().equals(productid) ){
                return merchantStock;
            }
        }
        return null;
    }


    public boolean addMerchantStock(MerchantStock merchantStock){
        return merchantStocks.add(merchantStock);
    }

    public  MerchantStock getById(String id){
        for (MerchantStock merchantStock: this.merchantStocks) {
            if (merchantStock.getId().equals(id)){
                return merchantStock;
            }
        }
        return null;
    }
    public Merchant getMerchantByID(String merchantid) {
        for (Merchant m: merchantService.merchants) {
            if (m.getId().equals(merchantid)){
                return m;
            }
        }
        return null;
    }

    public MerchantStock getMerchantByID(String merchantid, String productid) {
        for (MerchantStock m: merchantStocks) {
            if (m.getProductid().equals(productid) && m.getMerchantid().equals(merchantid)){
                return m;
            }
        }
        return null;
    }

    public boolean isMerchantByID(String merchantid) {
        for (Merchant m: merchantService.merchants) {
            if (m.getId().equals(merchantid)){
                return true;
            }
        }
        return false ;
    }

    public String getMerchantByProductID(String productid) {
        for (MerchantStock merchantStock: merchantStocks) {
            if (merchantStock.getProductid().equals(productid)){
                return merchantStock.getMerchantid();
            }
        }
        return null;
    }

    public Integer getStockByproductId(String prodctid) {
        for (MerchantStock merchantStock: merchantStocks) {
            if (merchantStock.getProductid().equals(prodctid)) {
                return merchantStock.getStock();
            }
        }
        return 0;
    }
}
