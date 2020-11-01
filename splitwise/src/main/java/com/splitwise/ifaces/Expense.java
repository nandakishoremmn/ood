package com.splitwise.ifaces;

import com.splitwise.data.Payment;
import com.splitwise.enums.ExpenseType;
import com.splitwise.exceptions.InvalidExpenseException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public abstract class Expense {
    public List<Payment> payments;
    public List<Integer> participants;

    public abstract ExpenseType getType();
    abstract public void validateData() throws InvalidExpenseException;
    public int getTotalUserCount(){
        return participants.size() + payments.size();
    }
}
