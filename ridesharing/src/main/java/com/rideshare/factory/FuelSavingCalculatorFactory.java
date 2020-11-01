package com.rideshare.factory;

import com.rideshare.ifaces.FuelSavingCalculator;
import com.rideshare.impl.FuelSavingCalculatorImpl;

public class FuelSavingCalculatorFactory {
    public static FuelSavingCalculator get() {
        return new FuelSavingCalculatorImpl();
    }
}
