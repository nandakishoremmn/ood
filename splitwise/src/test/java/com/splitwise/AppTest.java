package com.splitwise;

import com.splitwise.data.Payment;
import com.splitwise.data.User;
import com.splitwise.exceptions.InvalidExpenseException;
import com.splitwise.exceptions.UserRegistrationException;
import com.splitwise.ifaces.App;
import com.splitwise.service.AppImpl;
import com.splitwise.service.expense.EqualExpense;
import com.splitwise.service.expense.ExactExpense;
import com.splitwise.service.expense.PercentageExpense;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AppTest {
    private App app;
    private List<User> users;

    @Before
    public void setup() {
        app = new AppImpl();

        users = IntStream.range(0, 4)
                .mapToObj(i -> new User(i, "name" + i, "mail" + 1 + "@gmail.com", "904341292" + i))
                .collect(Collectors.toList());
        users.forEach(u -> {
            try {
                app.addUser(u);
            } catch (UserRegistrationException e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    public void equalSplitTest() throws InvalidExpenseException {
        EqualExpense expense1 = EqualExpense.builder().payments(Arrays.asList(
                new Payment(1, 100),
                new Payment(2, 100)
        ))
                .participants(Arrays.asList(3))
                .build();
        app.addExpense(expense1);

        app.printBalance(users.get(1));
        app.printBalance(users.get(2));
        app.printBalance(users.get(3));

        app.printBalances();
    }

    @Test
    public void equalSplitSingleUserTest() throws InvalidExpenseException {
        EqualExpense expense1 = EqualExpense.builder().payments(Arrays.asList(
                new Payment(1, 100)
        ))
                .participants(new ArrayList<>())
                .build();
        app.addExpense(expense1);

        app.printBalance(users.get(1));

        app.printBalances();
    }

    @Test(expected = InvalidExpenseException.class)
    public void equalSplitInvalidDataTest() throws InvalidExpenseException {
        EqualExpense expense1 = EqualExpense.builder().payments(Arrays.asList(
                new Payment(1, 100),
                new Payment(2, 100)
        ))
                .participants(Arrays.asList(2))
                .build();
        app.addExpense(expense1);
    }

    @Test
    public void exactSplitTest() throws InvalidExpenseException {
        ExactExpense expense2 = ExactExpense.builder().payments(Arrays.asList(
                new Payment(1, 100),
                new Payment(2, 100)
        ))
                .participants(Arrays.asList(3))
                .shares(new HashMap<Integer, Integer>() {{
                    put(1, 80);
                    put(2, 50);
                    put(3, 70);
                }})
                .build();
        app.addExpense(expense2);

        app.printBalance(users.get(1));
        app.printBalance(users.get(2));
        app.printBalance(users.get(3));

        app.printBalances();
    }

    @Test(expected = InvalidExpenseException.class)
    public void exactSplitInvalidTest() throws InvalidExpenseException {
        ExactExpense expense2 = ExactExpense.builder().payments(Arrays.asList(
                new Payment(1, 100),
                new Payment(2, 100)
        ))
                .participants(Arrays.asList(3))
                .shares(new HashMap<Integer, Integer>() {{
                    put(1, 80);
                    put(2, 50);
                    put(3, 71);
                }})
                .build();
        app.addExpense(expense2);

        app.printBalance(users.get(1));
        app.printBalance(users.get(2));
        app.printBalance(users.get(3));

        app.printBalances();
    }

    @Test
    public void percentageSplitTest() throws InvalidExpenseException {
        PercentageExpense expense2 = PercentageExpense.builder().payments(Arrays.asList(
                new Payment(1, 1000),
                new Payment(2, 500)
        ))
                .participants(Arrays.asList(3))
                .shares(new HashMap<Integer, Float>() {{
                    put(1, 30f);
                    put(2, 30f);
                    put(3, 40f);
                }})
                .build();
        app.addExpense(expense2);

        app.printBalance(users.get(1));
        app.printBalance(users.get(2));
        app.printBalance(users.get(3));

        app.printBalances();
    }


    @Test(expected = InvalidExpenseException.class)
    public void percentageSplitInvalidDataTest() throws InvalidExpenseException {
        PercentageExpense expense2 = PercentageExpense.builder().payments(Arrays.asList(
                new Payment(1, 100),
                new Payment(2, 100)
        ))
                .participants(Arrays.asList(3))
                .shares(new HashMap<Integer, Float>() {{
                    put(1, 31f);
                    put(2, 30f);
                    put(3, 40f);
                }})
                .build();
        app.addExpense(expense2);
    }
}