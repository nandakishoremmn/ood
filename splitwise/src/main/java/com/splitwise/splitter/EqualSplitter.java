package com.splitwise.splitter;

import com.splitwise.data.Payment;
import com.splitwise.data.Settlement;
import com.splitwise.ifaces.Splitter;
import com.splitwise.service.expense.EqualExpense;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EqualSplitter implements Splitter<EqualExpense> {
    @Override
    public List<Settlement> split(EqualExpense splitData) {
        Map<Integer, Integer> amountMap = splitData.getPayments().stream()
                .collect(Collectors.toMap(Payment::getPayer, Payment::getAmount));

        Integer totalAmount = amountMap.values().stream().reduce(0, Integer::sum);

        splitData.getParticipants()
                .forEach(p -> amountMap.put(p, 0));

        Integer splitAmount = totalAmount/splitData.getTotalUserCount();

        Integer extra = totalAmount - splitAmount * splitData.getTotalUserCount();

        amountMap.keySet().forEach(k -> {
            amountMap.put(k, amountMap.get(k) - splitAmount);
        });

        List<Payment> finalBalances = amountMap.entrySet().stream()
                .map(e -> Payment.builder().payer(e.getKey()).amount(e.getValue()).build())
                .sorted(Comparator.comparing(Payment::getAmount))
                .collect(Collectors.toList());

        System.out.println(finalBalances);

        fixExtraAmount(extra, finalBalances);

        return getSettlements(finalBalances);

    }
}
