package com.splitwise.service;

import com.splitwise.data.Settlement;
import com.splitwise.data.User;
import com.splitwise.exceptions.InvalidExpenseException;
import com.splitwise.exceptions.UserRegistrationException;
import com.splitwise.factory.SplitterFactory;
import com.splitwise.ifaces.App;
import com.splitwise.ifaces.BalanceSheet;
import com.splitwise.ifaces.Expense;
import com.splitwise.ifaces.Splitter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// https://workat.tech/machine-coding/practice/splitwise-problem-0kp2yneec2q2
public class AppImpl implements App {
    private static App instance = null;
    Map<Integer, User> users = new HashMap<Integer, User>();
    List<Expense> expenses = new ArrayList<Expense>();
    BalanceSheet balancesheet = new BalanceSheetImpl();

    public static App getInstance(){ // move to factory
        if(instance == null){
            instance = new AppImpl();
        }
        return instance;
    }

    public void setBalancesheet(BalanceSheet balancesheet) {
        this.balancesheet = balancesheet;
    }

    @Override
    public void addUser(User user) throws UserRegistrationException {
        if(users.containsKey(user.getId())){
            throw new UserRegistrationException("User already registered");
        }
        users.put(user.getId(), user);
        balancesheet.addUserRecord(user);
    }

    @Override
    public void addExpense(Expense expense) throws InvalidExpenseException {
        expense.validateData();
        Splitter splitter = SplitterFactory.get(expense.getType());
        List<Settlement> settlements = splitter.split(expense);
        expenses.add(expense);
        settlements.forEach(s -> balancesheet.updateExpense(s));

        // print
        System.out.println("===================");
        System.out.printf("Expense [%s] settled :\n", expense);
        settlements.forEach(System.out::println);
        System.out.println("===================");
    }
     @Override
     public void printBalance(User user){
        balancesheet.printBalance(user);
     }

    @Override
    public void printBalances(){
        balancesheet.printBalances();
    }


}
