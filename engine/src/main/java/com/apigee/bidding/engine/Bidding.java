package com.apigee.bidding.engine;

import com.apigee.bidding.data.model.Bid;
import com.apigee.bidding.data.model.BidImpl;
import com.apigee.bidding.data.model.Item;
import com.apigee.bidding.data.model.User;
import com.apigee.bidding.stats.BidsPerItem;
import com.apigee.bidding.stats.UsersAndItems;

import java.util.List;

public class Bidding {

    private BidsPerItem bidsPerItem = BidsPerItem.getInstance();
    private UsersAndItems usersAndItems = UsersAndItems.getInstance();

    public void bid(int bidAmount, int userId, int itemId){

        Bid bid = validateAndRegisterbid(bidAmount, userId, itemId);
        bid.execute();

    }

    private Bid validateAndRegisterbid(int bidAmount, int userId, int itemId) {
        User user = usersAndItems.getUser(userId);
        Item item = usersAndItems.getItem(itemId);
        Bid bid ;
        if (user == null) throw new IllegalArgumentException("No user exists for userId :"+userId);
        else if (item == null) throw new IllegalArgumentException("No item exists for itemId :"+itemId);
        else {

            Integer maxCurrentBid = 0 ;
            if(bidsPerItem.containsItem(item)) {
                maxCurrentBid = bidsPerItem.getHighestBid(item);
            }

            if(bidAmount > maxCurrentBid){
                bid = new BidImpl(bidAmount,user,item);
            }else {
                throw new IllegalArgumentException("The bid amount is less than the current max bid");
            }
        }
        bidsPerItem.add(item,bid);
        return bid;
    }


    public List<Bid> getTopFiveBids(int itemId, int userId) {
        Item item = usersAndItems.getItem(itemId);
        if (item == null) throw new IllegalArgumentException("No item exists for itemId :"+itemId);

        return bidsPerItem.getTopFiveBids(item);
    }
}
