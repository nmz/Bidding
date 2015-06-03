package com.apigee.bidding.data.model;

import com.apigee.bidding.conf.Configuration;

import java.util.Observable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ItemImpl extends Observable implements Item {

    private static final AtomicInteger NEXT_ID = new AtomicInteger(0);

    private final int id = NEXT_ID.getAndIncrement();
    private String name;
    private int desiredPrice;
    private long expiryInSec;
    private Status status;
    private Bid wonBid;

    private int hashCode;

    public ItemImpl(String name, int desiredPrice, int durationInSec) {
        this.name = name;
        this.desiredPrice = desiredPrice;
        if (durationInSec > Configuration.ITEM_MAX_EXPIRY_IN_SECONDS){
            throw new IllegalArgumentException("Expiry time :" +durationInSec+ " exceeds maximum" +
                    "permitted expiry time for an item (1 hour).");
        }
        this.expiryInSec = (System.currentTimeMillis() / 1000L) + durationInSec;
        status = Status.bidding;
    }

    public void notifyObservers(){
        setChanged();
        super.notifyObservers(this);
    }

    public void addObserver(User user){
        super.addObserver(user);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getDesiredPrice() {
        return desiredPrice;
    }

    @Override
    public long getExpiryInSec() {
        return expiryInSec;
    }

    @Override
    public boolean isItemValid() {
        return ((System.currentTimeMillis() / 1000L) < expiryInSec);
    }

    @Override
    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public Bid getWonBid() {
        return wonBid;
    }

    @Override
    public void setWonBid(Bid bid) {
        wonBid = bid;

    }

    @Override
    public void removeObservers(){
        super.deleteObservers();
    }

    @Override
    public boolean equals(Object anObject){
        if(this == anObject){
            return true;
        }
        else if(anObject != null && anObject instanceof ItemImpl){
            ItemImpl item = (ItemImpl) anObject;
            return id == item.getId();
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


}
