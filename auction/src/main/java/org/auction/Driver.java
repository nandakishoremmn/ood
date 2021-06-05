package org.auction;

import org.auction.ifaces.Platform;

import java.util.stream.IntStream;

public class Driver {
    public static void main(String[] args) {
        Platform platform = new PlatformImpl();

        IntStream.range(0, 5)
                .forEach(i -> platform.createSeller("S" + i));
        IntStream.range(0, 5)
                .forEach(i -> platform.createBuyer("B" + i));

        platform.createAuction("S1", 10, 50, 1);
        platform.bid("B1", "A1", 17);
        platform.bid("B2", "A1", 15);
        platform.bid("B3", "A1", 19);
        platform.bid("B2", "A1", 19);
        platform.close("A1");
        platform.getProfit("A1");


        platform.createAuction("S2", 5, 20, 2);
        platform.bid("B3", "A2", 25);
        platform.bid("B2", "A2", 5);
        platform.withdraw("B2", "A2");
        platform.close("A2");
        platform.getProfit("A2");


    }
}
