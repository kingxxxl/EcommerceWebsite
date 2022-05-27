package com.example.ecommercewebsite.service;

import com.example.ecommercewebsite.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    List<User> users = new ArrayList<>();

    public UserService() {
        this.users.addAll(
                List.of(
                        new User("101","Abdullah","a123456","email@email.com","admin",100),
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
}
