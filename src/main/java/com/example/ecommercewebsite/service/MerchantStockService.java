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


    public MerchantStockService(MerchantService merchantService) {
        this.merchantService = merchantService;
        this.merchantStocks.addAll(
                List.of(
                        new MerchantStock("101","101","101",100),
                        new MerchantStock("102","101","101",100),
                        new MerchantStock("103","101","101",100),
                        new MerchantStock("104","101","101",100)
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
}
