package org.auction.ifaces;

import java.util.Map;

public interface WinnerUtil {
    Buyer getWinner(Map<Buyer, Float> bids);
}
