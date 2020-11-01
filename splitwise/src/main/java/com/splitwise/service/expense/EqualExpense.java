package com.splitwise.service.expense;

import com.splitwise.data.Payment;
import com.splitwise.enums.ExpenseType;
import com.splitwise.exceptions.InvalidExpenseException;
import com.splitwise.ifaces.Expense;
import lombok.Builder;

import java.util.HashSet;
import java.util.List;


public class EqualExpense extends Expense {
    @Builder
    public EqualExpense(List<Payment> payments, List<Integer> participants) {
        super(payments, participants);
    }

    @Override
    public String toString() {
        return "EqualSplitData{" +
                "payments=" + payments +
                ", participants=" + participants +
                '}';
    }

    @Override
    public ExpenseType getType() {
        return ExpenseType.EQUAL;
    }

    @Override
    public void validateData() throws InvalidExpenseException {
        HashSet<Integer> participants = new HashSet<>(this.participants);
        boolean valid = this.getPayments().stream().noneMatch(p -> participants.contains(p.getPayer()));
        if (!valid) {
            throw new InvalidExpenseException("Please check expense data " + this);
        }
    }

}
