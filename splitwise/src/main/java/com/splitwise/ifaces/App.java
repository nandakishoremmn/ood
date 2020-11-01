package com.splitwise.ifaces;

import com.splitwise.data.User;
import com.splitwise.exceptions.InvalidExpenseException;
import com.splitwise.exceptions.UserRegistrationException;

public interface App {
    void addUser(User user) throws UserRegistrationException;

    void addExpense(Expense expense) throws InvalidExpenseException;

    void printBalance(User user);

    void printBalances();
}
