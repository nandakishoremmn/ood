package com.splitwise.service;

import com.splitwise.data.Settlement;
import com.splitwise.data.User;
import com.splitwise.ifaces.BalanceSheet;

import java.util.HashMap;
import java.util.Map;

public class BalanceSheetImpl implements BalanceSheet {
    private final Map<Integer, Map<Integer, Integer>> data = new HashMap<Integer, Map<Integer, Integer>>();

    @Override
    public void addUserRecord(User user) {
        data.put(user.getId(), new HashMap<>());
    }

    @Override
    public void updateExpense(Settlement settlement){
        updateExpenseGeneric(settlement);
        updateExpenseGeneric(Settlement.builder()
                .source(settlement.getTarget())
                .target(settlement.getSource())
                .amount(-settlement.getAmount())
                .build());
    }

    @Override
    public void updateExpenseGeneric(Settlement settlement) {
        if (!data.containsKey(settlement.getSource())) {
            data.put(settlement.getSource(), new HashMap<>());
        }
        if (!data.get(settlement.getSource()).containsKey(settlement.getTarget())) {
            data.get(settlement.getSource()).put(settlement.getTarget(), 0);
        }
        data.get(settlement.getSource()).put(settlement.getTarget(), settlement.getAmount() + data.get(settlement.getSource()).get(settlement.getTarget()));
    }

    @Override
    public void printBalance(User user) {
        System.out.println("==============");
        System.out.println("User balance for " + user.getName());
        data.get(user.getId()).forEach((key, value) -> {
            if (value > 0) {
                System.out.printf("User %s owes Rs %s to %s\n", user.getId(), value, key);
            } else {
                System.out.printf("User %s gets Rs %s from %s\n", user.getId(), -value, key);
            }
        });
        System.out.println("==============");
    }


    @Override
    public void printBalances() {
        System.out.println("==============");
        System.out.println("User balances");
        data.forEach((k, v) -> {
            Integer total = v.values().stream().reduce(0, Integer::sum);
            if (total > 0) {
                System.out.printf("%s owes %s in total\n", k, total);
            } else {
                System.out.printf("%s gets %s in total\n", k, -total);
            }
        });
        System.out.println("==============");
    }

}
