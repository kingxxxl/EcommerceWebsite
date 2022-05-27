package com.example.ecommercewebsite.service;

import com.example.ecommercewebsite.model.Merchant;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MerchantService {
    List<Merchant> merchants = new ArrayList<>();

    public MerchantService() {
        this.merchants.addAll(
                List.of(
                        new Merchant("101","Abdullah"),
                        new Merchant("102","Ahmed"),
                        new Merchant("103","Salah"),
                        new Merchant("104","Ali")
                ));
    }
    public List<Merchant> getMerchants(){
        return merchants;
    }

    public void updateMerchant(Merchant merchant, Merchant newMerchant){
        merchant.setId(newMerchant.getId());
        merchant.setName(newMerchant.getName());
    }

    public boolean deleteMerchant(String id){

        if (isMerchantByID(id)){
            Merchant merchant = getById(id);
            getMerchants().remove(merchant);
            return true;
        }
        return false;
    }

    public boolean isMerchantByID(String id){
        int checkForWork = -1;
        Merchant merchant = getById(id);
        if (merchant != null){
            checkForWork = Integer.parseInt(id);
        }
        return (checkForWork == -1) ? false :  true;
    }


    public boolean addMerchant(Merchant merchant){
        return merchants.add(merchant);
    }

    public  Merchant getById(String id){
        for (Merchant merchant: this.merchants) {
            if (merchant.getId().equals(id)){
                return merchant;
            }
        }
        return null;
    }
}
