package com.splitwise.factory;

import com.splitwise.enums.ExpenseType;
import com.splitwise.splitter.EqualSplitter;
import com.splitwise.splitter.ExactSplitter;
import com.splitwise.splitter.PercentageSplitter;
import com.splitwise.ifaces.Splitter;

public class SplitterFactory {
    public static Splitter get(ExpenseType type){
        switch (type){
            case EQUAL: return new EqualSplitter();
            case EXACT: return new ExactSplitter();
            case PERCENT: return new PercentageSplitter();
            default: throw new UnsupportedOperationException("This splitter is not available : " + type);
        }
    }
}
