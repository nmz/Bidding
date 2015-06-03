package com.apigee.bidding.stats;

import com.apigee.bidding.data.model.Item;
import com.apigee.bidding.data.model.User;

import java.util.*;

public class UsersAndItems {
    private Map<Integer,User> users = new HashMap<>();

    private Map<Integer,Item> items = new HashMap<>();

    private static UsersAndItems instance;

    public static UsersAndItems getInstance() {
        if (instance == null) {
            instance = new UsersAndItems();
        }
        return instance;

    }

    public void addUser(User user) {
        users.putIfAbsent(user.getUserId(), user);
    }

    public void addItem(Item item, Integer userId) {
        User user = users.get(userId);
        if(user == null){
            throw new IllegalArgumentException("No user exist for user-id"+userId);
        }
        user.addItem(item);
        items.putIfAbsent(item.getId(),item);
    }

    public User getUser(int userId) {
        return users.get(userId);
    }


    public Item getItem(int itemId) {
        return items.get(itemId);
    }

    public Set<Integer> getItems(int userId){
        return users.get(userId).getItems();
    }


    public void removeItem(Integer userId, Item item){
        User user = users.get(userId);
        user.removeItem(item);
        items.remove(item);
    }
}
