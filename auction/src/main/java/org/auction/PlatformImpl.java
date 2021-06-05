package org.auction;

import org.auction.ifaces.Auction;
import org.auction.ifaces.Buyer;
import org.auction.ifaces.Platform;
import org.auction.ifaces.Seller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class PlatformImpl implements Platform {
    Map<String, Seller> sellers = new HashMap<>();
    Map<String, Buyer> buyers = new HashMap<>();
    Map<String, Auction> auctions = new HashMap<>();

    static AtomicInteger auctionId = new AtomicInteger(1);

    @Override
    public void createAuction(String sellerName, float lowerLimit, float upperLimit, float participationFee) {
        if(!sellers.containsKey(sellerName)){
            throw new RuntimeException("No seller with name " + sellerName);
        }
        Seller seller = sellers.get(sellerName);
        Auction auction = seller.createAuction(auctionId.getAndIncrement(), lowerLimit, upperLimit, participationFee);
        auctions.put(auction.getId(), auction);
    }

    @Override
    public void createSeller(String name) {
        if(sellers.containsKey(name)){
            throw new RuntimeException("Seller exists with id " + name);
        }
        sellers.put(name, new SellerImpl(name));
    }

    @Override
    public void createBuyer(String name) {
        if(buyers.containsKey(name)){
            throw new RuntimeException("Seller exists with id " + name);
        }
        buyers.put(name, new BuyerImpl(name));
    }

    @Override
    public void bid(String buyerName, String auctionId, float amount) {
        Auction auction = auctions.getOrDefault(auctionId, null);
        Buyer buyer = buyers.getOrDefault(buyerName, null);
        if(auction == null){
            throw new RuntimeException("Auction not found " + auctionId);
        }
        if(buyer == null){
            throw new RuntimeException("Buyer not found " + buyerName);
        }
        auction.bid(buyer, amount);
    }

    @Override
    public void getProfit(String auctionId) {
        Auction auction = auctions.getOrDefault(auctionId, null);
        if(auction == null){
            throw new RuntimeException("Auction not found " + auctionId);
        }
        System.out.println("Profit : " + auction.getProfit());
    }

    @Override
    public void withdraw(String buyerName, String auctionId) {
        Auction auction = auctions.getOrDefault(auctionId, null);
        Buyer buyer = buyers.getOrDefault(buyerName, null);
        if(auction == null){
            throw new RuntimeException("Auction not found " + auctionId);
        }
        if(buyer == null){
            throw new RuntimeException("Buyer not found " + buyerName);
        }
        auction.withdraw(buyer);
    }

    @Override
    public void close(String auctionName) {
        Auction auction = auctions.getOrDefault(auctionName, null);
        if(auction == null){
            throw new RuntimeException("Auction not found " + auctionId);
        }
        auction.close();
    }

}
