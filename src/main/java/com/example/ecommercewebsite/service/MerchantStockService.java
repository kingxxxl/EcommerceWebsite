package com.example.ecommercewebsite.service;

import com.example.ecommercewebsite.model.MerchantStock;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MerchantStockService {
    List<MerchantStock> merchantStocks = new ArrayList<>();

    public MerchantStockService() {
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
        int checkForWork = -1;
        MerchantStock merchantStock = getById(id);
        if (merchantStock != null){
            checkForWork = Integer.parseInt(id);
        }
        return (checkForWork == -1) ? false :  true;
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
}
