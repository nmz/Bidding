package com.apigee.bidding.data.model;


import java.util.Observable;

public interface Bid {
    int getId();
    Integer getAmount();
    User getUser();
    Item getItem();
    long getBidTime();
    void execute();

}
