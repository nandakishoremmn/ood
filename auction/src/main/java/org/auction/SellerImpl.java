package org.auction;

import lombok.Getter;
import lombok.ToString;
import org.auction.ifaces.Auction;
import org.auction.ifaces.Seller;

import java.util.HashSet;
import java.util.Set;

@ToString
public class SellerImpl implements Seller {
    @Getter
    private final String name;
    Set<String> auctionIds = new HashSet<>();

    public SellerImpl(String name) {
        this.name = name;
    }

    @Override
    public Auction createAuction(int id, float lowerLimit, float upperLimit, float participationFee) {
        Auction auction = new AuctionImpl("A" + id, this, lowerLimit, upperLimit, participationFee);
        auctionIds.add(auction.getId());
        return auction;
    }
}
