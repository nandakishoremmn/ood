package org.auction.ifaces;

public interface Seller {
    Auction createAuction(int andIncrement, float lowerLimit, float upperLimit, float participationFee);
}
