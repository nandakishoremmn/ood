package com.splitwise.splitter;

import com.splitwise.data.Payment;
import com.splitwise.data.Settlement;
import com.splitwise.ifaces.Splitter;
import com.splitwise.service.expense.PercentageExpense;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PercentageSplitter implements Splitter<PercentageExpense> {
    @Override
    public List<Settlement> split(PercentageExpense splitData) {
        Map<Integer, Integer> amountMap = splitData.getPayments().stream()
                .collect(Collectors.toMap(Payment::getPayer, Payment::getAmount));

        splitData.getParticipants()
                .forEach(p -> amountMap.put(p, 0));

        Integer totalAmount = amountMap.values().stream().reduce(0, Integer::sum);

        Integer extra = Math.round(totalAmount - splitData.getShares().values().stream().map(p -> p * totalAmount / 100)
                .reduce(0.0f, Float::sum));

        amountMap.keySet().forEach(k -> {
            amountMap.put(k, amountMap.get(k) - Math.round(splitData.getShares().get(k) * totalAmount / 100));
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
