package com.apigee.bidding.data.model;


public interface Item {
    public enum Status {bidding, expired, sold};
    int getId();
    String getName();
    int getDesiredPrice();
    long getExpiryInSec();
    boolean isItemValid();
    void setStatus(Status status);
    Status getStatus();
    Bid getWonBid();
    void setWonBid(Bid bid);
    void removeObservers();
}
