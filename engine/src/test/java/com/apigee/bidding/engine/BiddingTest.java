package com.apigee.bidding.engine;

import com.apigee.bidding.data.model.*;
import com.apigee.bidding.stats.BidsPerItem;
import com.apigee.bidding.stats.UsersAndItems;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BiddingTest {

    Item ITEM_1 = new ItemImpl("Item 1", 60, 120);
    Item ITEM_2 = new ItemImpl("Item 2", 120, 120);
    Item ITEM_3 = new ItemImpl("Item 3", 10, 240);
    Item ITEM_4 = new ItemImpl("Item 4", 150, 120);

    User USER_1 = new UserImpl("name", "pass");
    User USER_2 = new UserImpl("name-1", "pass-1");
    User USER_3 = new UserImpl("name-2", "pass-2");

    BidsPerItem bidsPerItem = BidsPerItem.getInstance();
    UsersAndItems usersAndItems = UsersAndItems.getInstance();


    @Before
    public void setUp() {
        usersAndItems.addUser(USER_1);
        usersAndItems.addUser(USER_2);
        usersAndItems.addUser(USER_3);
        usersAndItems.addItem(ITEM_1, USER_2.getUserId());
        usersAndItems.addItem(ITEM_2, USER_1.getUserId());
        usersAndItems.addItem(ITEM_3, USER_1.getUserId());
        usersAndItems.addItem(ITEM_4, USER_2.getUserId());

    }

    @Test
    public void testTwoBidsWithEqualValueOnlyRegisterOnce() {

        Bidding bidding = new Bidding();
        bidding.bid(101, USER_1.getUserId(), ITEM_1.getId());
        try {
            bidding.bid(101, USER_1.getUserId(), ITEM_1.getId());
        }catch (IllegalArgumentException ie){

        }

        assertThat(bidsPerItem.getNumberOfBidsPerItem(ITEM_1), is(1));

        bidding.getTopFiveBids(ITEM_1.getId(), USER_1.getUserId());
    }

    @Test
    public void testRetrieveAllBidsForAnItem() {

        Bidding bidding = new Bidding();
        bidding.bid(101, USER_1.getUserId(), ITEM_1.getId());
        bidding.bid(102, USER_1.getUserId(), ITEM_1.getId());
        bidding.bid(103, USER_1.getUserId(), ITEM_1.getId());

        assertThat(bidsPerItem.getNumberOfBidsPerItem(ITEM_1), is(3));
    }

    @Test
    public void WinningBidGivenMultipleBidsOnAnItem() {

        Bidding bidding = new Bidding();
        bidding.bid(10, USER_1.getUserId(), ITEM_1.getId());
        bidding.bid(20, USER_1.getUserId(), ITEM_1.getId());
        bidding.bid(30, USER_1.getUserId(), ITEM_1.getId());
        try {
            bidding.bid(20, USER_2.getUserId(), ITEM_1.getId()); //value less than the current highest bid
        }catch (IllegalArgumentException ie){
            assertThat(ie.getMessage(), is("The bid amount is less than the current max bid"));
        }
        bidding.bid(60, USER_3.getUserId(), ITEM_1.getId()); //desired price


        assertThat(usersAndItems.getItem(ITEM_1.getId()).getWonBid().getAmount(), is(60));

        assertThat(bidsPerItem.getNumberOfBidsPerItem(ITEM_1), is(4));
    }

}
