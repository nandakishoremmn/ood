package com.splitwise.ifaces;

import com.splitwise.data.Payment;
import com.splitwise.data.Settlement;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public interface Splitter<ExpenseMetaData extends Expense> {
    List<Settlement> split(ExpenseMetaData splitData);

    default List<Settlement> getSettlements(List<Payment> finalBalances) {
        finalBalances.sort(Comparator.comparing(Payment::getAmount));
        int start = 0;
        int end = finalBalances.size() - 1;
        List<Settlement> settlements = new ArrayList<>();

        while (!finalBalances.get(0).getAmount().equals(0)) {
            Payment lowest = finalBalances.get(start);
            Payment highest = finalBalances.get(end);
            if (highest.getAmount() + lowest.getAmount() > 0) {
                settlements.add(Settlement.builder()
                        .source(highest.getPayer())
                        .target(lowest.getPayer())
                        .amount(lowest.getAmount())
                        .build());
                highest.setAmount(highest.getAmount() + lowest.getAmount());
                lowest.setAmount(0);
            } else {
                settlements.add(Settlement.builder()
                        .source(lowest.getPayer())
                        .target(highest.getPayer())
                        .amount(highest.getAmount())
                        .build());
                lowest.setAmount(highest.getAmount() + lowest.getAmount());
                highest.setAmount(0);
            }
            finalBalances.sort(Comparator.comparing(Payment::getAmount));
        }
        return settlements;
    }

    default void fixExtraAmount(Integer extra, List<Payment> finalBalances) {
        int i = 0;
        while (extra.compareTo(0) > 0) {
            extra -= 1;
            finalBalances.get(i).setAmount(finalBalances.get(i).getAmount() - 1);
            i = (i+1)%finalBalances.size();
        }
    }
}
