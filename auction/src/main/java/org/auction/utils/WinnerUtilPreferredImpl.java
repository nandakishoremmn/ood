package org.auction.utils;

import org.auction.ifaces.Buyer;
import org.auction.ifaces.WinnerUtil;

import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

public class WinnerUtilPreferredImpl implements WinnerUtil {
    @Override
    public Buyer getWinner(Map<Buyer, Float> bids) {
        return bids.entrySet().stream()
                .collect(Collectors.groupingBy(Map.Entry::getValue, TreeMap::new,
                        mapping(Map.Entry::getKey, toList())))
                .values().stream().filter(buyers -> {
                    return buyers.size() == 1 || buyers.stream().filter(Buyer::isPreferred).count() == 1;
                })
                .map(buyers -> {
                    if(buyers.size() == 1) {
                        return buyers.get(0);
                    } else {
                        return buyers.stream().filter(Buyer::isPreferred).findFirst().get();
                    }
                })
                .reduce((f, s) -> s)
                .orElse(null);
    }
}
