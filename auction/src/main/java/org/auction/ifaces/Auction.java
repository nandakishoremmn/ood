package org.auction.ifaces;

public interface Auction {
    String getId();
    void bid(Buyer buyer, float amount);
    void withdraw(Buyer buyer);
    void close();

    Float getProfit();
}
