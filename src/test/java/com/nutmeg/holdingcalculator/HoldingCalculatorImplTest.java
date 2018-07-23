package com.nutmeg.holdingcalculator;


import com.nutmeg.holdingcalculatator.HoldingCalculator;
import com.nutmeg.holdingcalculatator.HoldingCalculatorImpl;
import com.nutmeg.transactions.Holding;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class HoldingCalculatorImplTest{

    private static final String TRANSACTIONS_FILE = "transactions.csv";
    private final HoldingCalculator holdingCalculator = new HoldingCalculatorImpl();
    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Test
    public void shouldCalculateHoldings () throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File transactions = new File(classLoader.getResource(TRANSACTIONS_FILE).getFile());
        LocalDate date = LocalDate.parse("20170201",formatter);
        Map<String,List<Holding>> holdings = holdingCalculator.calculateHoldings(transactions, date);
        System.out.println(holdings);
    }
}
