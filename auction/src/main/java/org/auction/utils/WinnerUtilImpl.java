package org.auction.utils;

import org.auction.ifaces.Buyer;
import org.auction.ifaces.WinnerUtil;

import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class WinnerUtilImpl implements WinnerUtil {
    @Override
    public Buyer getWinner(Map<Buyer, Float> bids) {
        return bids.entrySet().stream()
                .collect(Collectors.groupingBy(Map.Entry::getValue, TreeMap::new,
                        mapping(Map.Entry::getKey, toList())))
                .values().stream().filter(buyers -> buyers.size() == 1)
                .map(buyers -> buyers.get(0))
                .reduce((f, s) -> s)
                .orElse(null);
    }
}
