package com.apigee.bidding.engine;

import com.apigee.bidding.stats.BidsPerItem;
import com.apigee.bidding.stats.UsersAndItems;


public class CleanUpService implements Runnable{

    UsersAndItems usersAndItems = UsersAndItems.getInstance();
    BidsPerItem bidsPerItem =BidsPerItem.getInstance();

    @Override
    public void run() {
        /*for (Item item : users.getItems()){
            if(!item.isItemValid() || item.getStatus() == Item.Status.sold){
                users.removeItem(item, userId);
                bidsPerItem.removeItem(item);
            }
        }*/

    }
}
