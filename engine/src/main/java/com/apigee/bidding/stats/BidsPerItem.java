package com.apigee.bidding.stats;

import com.apigee.bidding.data.model.Bid;
import com.apigee.bidding.data.model.Item;
import com.apigee.bidding.engine.CleanUpService;

import java.util.*;
import java.util.concurrent.*;

public class BidsPerItem {

    private ConcurrentHashMap<Integer,ConcurrentSkipListSet<Bid>> bidsPerItem = new ConcurrentHashMap<>();

     private static BidsPerItem instance;

    public static BidsPerItem getInstance() {
        if (instance == null) {
            instance = new BidsPerItem();
        }
        return instance;

    }

    public void add(Item item, Bid bid){
        ConcurrentSkipListSet<Bid> bidSet;
        if(bidsPerItem.containsKey(item.getId())){
            bidSet = bidsPerItem.get(item.getId());
        }else {
            bidSet = new ConcurrentSkipListSet<>();
            bidsPerItem.put(item.getId(),bidSet);
        }
        bidSet.add(bid);
    }

    public boolean containsItem(Item item){
        return bidsPerItem.containsKey(item.getId());
    }

    public int getHighestBid(Item item){
        Bid bid = (Bid)((ConcurrentSkipListSet) bidsPerItem.get(item.getId())).last();
        return bid.getAmount();
    }

    public int getNumberOfBidsPerItem(Item item){
        return bidsPerItem.get(item.getId()).size();
    }

    public ConcurrentSkipListSet<Bid> removeItem(Item item){
        return bidsPerItem.remove(item.getId());
    }

    public List<Bid> getTopFiveBids(Item item){
        NavigableSet navigableSet = bidsPerItem.get(item.getId()).descendingSet();
        Iterator itr = navigableSet.iterator();
        List<Bid> bids = new ArrayList<>();
        int count = 0;

        while (itr.hasNext() && count < 5) {
            bids.add((Bid)itr.next());
            count++;
        }
        return bids;
    }


}
