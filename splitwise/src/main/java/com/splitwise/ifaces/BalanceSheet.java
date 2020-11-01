package com.splitwise.ifaces;

import com.splitwise.data.User;
import com.splitwise.data.Settlement;

public interface BalanceSheet {
    void addUserRecord(User user);

    void updateExpense(Settlement settlement);

    void updateExpenseGeneric(Settlement settlement);

    void printBalance(User user);

    void printBalances();
}
