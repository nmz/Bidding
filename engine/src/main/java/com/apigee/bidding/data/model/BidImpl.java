package com.apigee.bidding.data.model;

import jdk.nashorn.internal.ir.annotations.Immutable;

import java.util.Observable;
import java.util.concurrent.atomic.AtomicInteger;

@Immutable
public class BidImpl implements Bid, Comparable {

    private static final AtomicInteger NEXT_ID = new AtomicInteger(0);

    private final int id = NEXT_ID.getAndIncrement();

    private final Integer amount;
    private final long bidTime;
    private final User user;
    private final Item item;

    private int hashCode;

    public BidImpl(int amount, User user, Item item) {
        if (amount <= 0) throw new IllegalArgumentException("Bid cannot be created with an amount less than or equal " +
                "to zero, Please provide a valid input");

        long currTime = System.currentTimeMillis() / 1000L;


        if (currTime > item.getExpiryInSec()) {
            throw new IllegalArgumentException("The bid cannot be placed for item since the bidding time " +
                    "for item has been expired");

        }
        ((ItemImpl)item).addObserver(user);

        this.amount = amount;
        this.bidTime = currTime;
        this.user = user;
        this.item = item;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Integer getAmount() {
        return amount;
    }

    @Override
    public long getBidTime() {
        return bidTime;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public Item getItem() {
        return item;
    }

    @Override
    public void execute() {
        if (amount >= item.getDesiredPrice() && item.isItemValid()) {
            item.setStatus(Item.Status.sold);
            item.setWonBid(this);
            ((ItemImpl)item).notifyObservers();
            ((ItemImpl)item).removeObservers();
        }
        //TODO: if bid cannot be executed log it
    }

    @Override
    public boolean equals(Object anObject) {
        if (this == anObject) {
            return true;
        } else if (anObject != null && anObject instanceof BidImpl) {
            BidImpl bid = (BidImpl) anObject;
            return id == bid.getId();

        }
        return false;
    }

    @Override
    public int hashCode() {
        if (hashCode == 0) {
            hashCode = id;
        }
        return hashCode;
    }

    @Override
    public int compareTo(Object anObject) {
        if (this == anObject) {
            return 0;
        } else if (anObject != null && anObject instanceof BidImpl) {
            BidImpl bid = (BidImpl) anObject;
            return amount.compareTo(bid.getAmount());
        }
        return -1;

    }
}