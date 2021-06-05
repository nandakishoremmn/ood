package org.auction;

import lombok.Getter;
import lombok.ToString;
import org.auction.enums.AuctionState;
import org.auction.ifaces.Auction;
import org.auction.ifaces.Buyer;
import org.auction.ifaces.Seller;
import org.auction.ifaces.WinnerUtil;
import org.auction.utils.WinnerUtilPreferredImpl;

import java.util.HashMap;
import java.util.Map;

@Getter
@ToString
public class AuctionImpl implements Auction {
    private final String id;
    private final Seller seller;
    private final Float participationFee;
    private final Float upperLimit;
    private final Float lowerLimit;
    private AuctionState state = AuctionState.OPEN;
    private final Map<Buyer, Float> bids = new HashMap<>();
    @ToString.Exclude
//    private final WinnerUtil winnerUtil = new WinnerUtilImpl();
    private final WinnerUtil winnerUtil = new WinnerUtilPreferredImpl();
    private Float winningBid = 0f;
    private Integer withdrawCount = 0;

    public AuctionImpl(String id, Seller seller, Float lowerLimit, Float upperLimit, Float participationFee) {
        this.id = id;
        this.seller = seller;
        if(upperLimit < lowerLimit){
            throw new RuntimeException("Upper limit must be >= lower limit");
        }
        this.upperLimit = upperLimit;
        this.lowerLimit = lowerLimit;
        this.participationFee = participationFee;
    }

    @Override
    public void bid(Buyer buyer, float amount) {
        if(AuctionState.CLOSE.equals(state)){
            throw new RuntimeException("Cannot bid closed auction");
        }
        if(lowerLimit <= amount && amount <= upperLimit) {
            bids.put(buyer, amount);
        } else {
            System.out.println("Bid rejected [" + this.id + "] " + amount);
        }
    }

    @Override
    public void withdraw(Buyer buyer) {
        if(!bids.containsKey(buyer)){
            throw new RuntimeException("Buyer has not bidded " + buyer);
        }
        withdrawCount += 1;
        bids.remove(buyer);
    }

    @Override
    public void close() {
        this.state = AuctionState.CLOSE;
        bids.keySet().forEach(Buyer::incrementAuctionParticipated);
        Buyer buyer = winnerUtil.getWinner(bids);
        if(buyer != null){
            System.out.println(id + " won by " + buyer + " for " + bids.get(buyer));
            winningBid = bids.get(buyer);
        } else {
            System.out.println("Nobody won");
        }
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Float getProfit() {
        if(winningBid > 0) {
            return winningBid + .2f * ((bids.size() + withdrawCount) * participationFee) - (upperLimit + lowerLimit) * .5f;
        } else {
            return .2f * ((bids.size() + withdrawCount) * participationFee);
        }
    }
}
