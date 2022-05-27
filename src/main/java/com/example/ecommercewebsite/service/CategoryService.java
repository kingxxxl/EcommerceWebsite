package com.example.ecommercewebsite.service;

import com.example.ecommercewebsite.model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    List<Category> categorys = new ArrayList<>();

    public CategoryService() {
        this.categorys.addAll(
                List.of(
                        new Category("101","Abdullah"),
                        new Category("102","Ahmed"),
                        new Category("103","Salah"),
                        new Category("104","Ali")
                ));
    }
    public List<Category> getCategorys(){
        return categorys;
    }

    public void updateCategory(Category category, Category newCategory){
        category.setId(newCategory.getId());
        category.setName(newCategory.getName());
    }

    public boolean deleteCategory(String id){

        if (isCategoryByID(id)){
            Category category = getById(id);
            getCategorys().remove(category);
            return true;
        }
        return false;
    }

    public boolean isCategoryByID(String id){
        int checkForWork = -1;
        Category category = getById(id);
        if (category != null){
            checkForWork = Integer.parseInt(id);
        }
        return (checkForWork == -1) ? false :  true;
    }


    public boolean addCategory(Category category){
        return categorys.add(category);
    }

    public  Category getById(String id){
        for (Category category: this.categorys) {
            if (category.getId().equals(id)){
                return category;
            }
        }
        return null;
    }
}
