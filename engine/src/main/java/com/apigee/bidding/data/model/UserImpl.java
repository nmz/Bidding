package com.apigee.bidding.data.model;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class UserImpl implements User {

    private static final AtomicInteger NEXT_ID = new AtomicInteger(0);

    private final int id = NEXT_ID.getAndIncrement();
    private String userName;
    private String passWord;

    private Set<Integer> items = new HashSet<>();

    private int hashCode;

    public UserImpl(String userName, String passWord){
        this.passWord = passWord;
        this.userName = userName;
    }

    @Override
    public int getUserId() {
        return id;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public String getPassWord() {
        return passWord;
    }

    @Override
    public boolean equals(Object anObject){
        if(this == anObject){
            return true;
        }
        else if(anObject != null && anObject instanceof UserImpl){
            UserImpl user = (UserImpl) anObject;
            return id == user.getUserId();

        }
        return false;
    }

    @Override
    public int hashCode(){
        if (hashCode == 0){
            hashCode = id;
        }
        return hashCode;
    }

    @Override
    public void update(Observable observable, Object o) {
        //TODO: log and notify user the user
    }

    @Override
    public Set<Integer> getItems() {
        return items;
    }

    @Override
    public void addItem(Item item){
        items.add(item.getId());
    }


    @Override
    public void removeItem(Item item){
       items.remove(item.getId());

    }

}
