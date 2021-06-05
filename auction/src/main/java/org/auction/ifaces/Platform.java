package org.auction.ifaces;

public interface Platform {
    void createAuction(String sellerName, float lowerLimit, float upperLimit, float participationFee);
    void createSeller(String name);
    void createBuyer(String name);

    void withdraw(String buyerName, String auctionId);

    void close(String auctionName);
    void bid(String buyerName, String auctionName, float amount);
    void getProfit(String auctionId);
}
