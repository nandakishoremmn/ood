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
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class ExactExpense extends Expense {
    private Map<Integer, Integer> shares;

    @Builder
    public ExactExpense(List<Payment> payments, List<Integer> participants, Map<Integer, Integer> shares) {
        super(payments, participants);
        this.shares = shares;
    }

    @Override
    public String toString() {
        return "ExactSplitData{" +
                "shares=" + shares +
                ", payments=" + payments +
                ", participants=" + participants +
                '}';
    }

    @Override
    public ExpenseType getType() {
        return ExpenseType.EXACT;
    }

    @Override
    public void validateData() throws InvalidExpenseException {
        boolean valid = this.getPayments().stream().map(Payment::getAmount).reduce(0, Integer::sum).equals(
                this.getShares().values().stream().reduce(0, Integer::sum));
        Set<Integer> participants = new HashSet<>(this.getParticipants());
        Set<Integer> payers = this.getPayments().stream().map(Payment::getPayer).collect(Collectors.toSet());
        valid = valid && this.getShares().keySet().stream().allMatch(k -> {
            return (participants.contains(k) || payers.contains(k)) && !(participants.contains(k) && payers.contains(k));
        });
        if (!valid) {
            throw new InvalidExpenseException("Please check expense data " + this);
        }
    }
}
