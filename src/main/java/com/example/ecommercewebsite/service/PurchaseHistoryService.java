package com.example.ecommercewebsite.service;

import com.example.ecommercewebsite.model.PurchaseHistory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseHistoryService {
    List<PurchaseHistory> purchaseHistorys = new ArrayList<>();

    public PurchaseHistoryService() {
        this.purchaseHistorys.addAll(
                List.of(
                        new PurchaseHistory("101","101","101",1200),
                        new PurchaseHistory("102","101","101",1200),
                        new PurchaseHistory("103","101","101",1200),
                        new PurchaseHistory("104","101","101",1200)
                ));
    }
    public List<PurchaseHistory> getPurchaseHistorys(){
        return purchaseHistorys;
    }

    public void updatePurchaseHistory(PurchaseHistory purchaseHistory, PurchaseHistory newPurchaseHistory){
        purchaseHistory.setId(newPurchaseHistory.getId());
        purchaseHistory.setUserid(newPurchaseHistory.getUserid());
    }

    public boolean deletePurchaseHistory(String id){

        if (isPurchaseHistoryByID(id)){
            PurchaseHistory purchaseHistory = getById(id);
            getPurchaseHistorys().remove(purchaseHistory);
            return true;
        }
        return false;
    }

    public boolean isPurchaseHistoryByID(String id){
        int checkForWork = -1;
        PurchaseHistory purchaseHistory = getById(id);
        if (purchaseHistory != null){
            checkForWork = Integer.parseInt(id);
        }
        return (checkForWork == -1) ? false :  true;
    }


    public boolean addPurchaseHistory(PurchaseHistory purchaseHistory){
        return purchaseHistorys.add(purchaseHistory);
    }

    public  PurchaseHistory getById(String id){
        for (PurchaseHistory purchaseHistory: this.purchaseHistorys) {
            if (purchaseHistory.getId().equals(id)){
                return purchaseHistory;
            }
        }
        return null;
    }
}
