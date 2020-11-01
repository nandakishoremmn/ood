package com.splitwise.service.expense;

import com.splitwise.data.Payment;
import com.splitwise.enums.ExpenseType;
import com.splitwise.exceptions.InvalidExpenseException;
import com.splitwise.ifaces.Expense;
import lombok.Builder;
import lombok.Getter;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Getter
public class PercentageExpense extends Expense {
    private Map<Integer, Float> shares;

    @Builder
    public PercentageExpense(List<Payment> payments, List<Integer> participants, Map<Integer, Float> shares) {
        super(payments, participants);
        this.shares = shares;
    }

    @Override
    public String toString() {
        return "PercentageSplitData{" +
                "shares=" + shares +
                ", payments=" + payments +
                ", participants=" + participants +
                '}';
    }

    @Override
    public ExpenseType getType() {
        return ExpenseType.PERCENT;
    }

    @Override
    public void validateData() throws InvalidExpenseException {
        HashSet<Integer> participants = new HashSet<>(this.participants);
        boolean valid = this.getPayments().stream().noneMatch(p -> participants.contains(p.getPayer()));
        valid = valid && Math.round(this.getShares().values().stream().reduce(0f, Float::sum)) == 100;
        if (!valid) {
            throw new InvalidExpenseException("Please check expense data " + this);
        }
    }
}
