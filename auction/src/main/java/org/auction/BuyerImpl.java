package org.auction;

import lombok.EqualsAndHashCode;
import org.auction.ifaces.Buyer;

import java.util.concurrent.atomic.AtomicInteger;

@EqualsAndHashCode
public class BuyerImpl implements Buyer {
    private final String name;
    private final AtomicInteger auctionCount = new AtomicInteger(0);

    public BuyerImpl(String name) {
        this.name = name;
    }

    @Override
    public boolean isPreferred() {
        return auctionCount.intValue() > 2;
    }

    @Override
    public void incrementAuctionParticipated() {
        auctionCount.incrementAndGet();
    }

    @Override
    public String toString() {
        return "BuyerImpl{" +
                "name='" + name + '\'' +
                ", auctionCount=" + auctionCount +
                '}';
    }
}
