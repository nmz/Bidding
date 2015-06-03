package com.apigee.bidding.data.model;

import java.util.Collection;
import java.util.Observer;
import java.util.Queue;
import java.util.Set;

public interface User extends Observer{
    public enum Roles { admin , user};
    int getUserId();
    String getUserName();
    String getPassWord();
    Set<Integer> getItems();
    void addItem(Item item);
    void removeItem(Item item);

}
