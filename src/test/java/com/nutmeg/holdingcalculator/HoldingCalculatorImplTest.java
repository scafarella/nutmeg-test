package com.nutmeg.holdingcalculator;


import com.nutmeg.holdingcalculatator.HoldingCalculator;
import com.nutmeg.holdingcalculatator.HoldingCalculatorImpl;
import com.nutmeg.transactions.Holding;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HoldingCalculatorImplTest{

    private static final String TRANSACTIONS_FILE = "transactions.csv";
    private final HoldingCalculator holdingCalculator = new HoldingCalculatorImpl();
    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final String USER2 = "NEAB0001";
    private static final String USER1 = "NEAA0000";

    private static Map<String,List<Holding>> getExpectedHoldings() {
        Holding h1 = new Holding();
        h1.setHoldings(10.0000);
        h1.setAsset("VUSA");

        Holding h2 = new Holding();
        h2.setHoldings(20.0000);
        h2.setAsset("VUKE");

        Holding h3 = new Holding();
        h3.setHoldings(10.5120);
        h3.setAsset("GILS");

        Holding h4 = new Holding();
        h4.setHoldings(17.6848);
        h4.setAsset("CASH");

        Holding h5 = new Holding();
        h5.setHoldings(10000.0000);
        h5.setAsset("CASH");

        List<Holding> u1 = Arrays.asList(
            h1, h2, h3, h4
        );

        List<Holding> u2 = Arrays.asList(
                h5
        );

        Map<String, List<Holding>> holdings = new HashMap<>();
        holdings.put("NEAA0000", u1);
        holdings.put("NEAB0001", u2);

        return holdings;

    }

    @Test
    public void shouldCalculateHoldings () throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File transactions = new File(classLoader.getResource(TRANSACTIONS_FILE).getFile());
        LocalDate date = LocalDate.parse("20170201",formatter);
        Map<String,List<Holding>> holdings = holdingCalculator.calculateHoldings(transactions, date);

        List<Holding> user1Holdings = holdings.get(USER1);
        BigDecimal vusaValue = getAsset(user1Holdings, "VUSA");
        Assert.assertEquals(BigDecimal.valueOf(10.0000), vusaValue);
        BigDecimal vukeValue = getAsset(user1Holdings, "VUKE");
        Assert.assertEquals(BigDecimal.valueOf(20.0000), vukeValue);
        BigDecimal gilsValue = getAsset(user1Holdings, "GILS");
        Assert.assertEquals(BigDecimal.valueOf(10.5120), gilsValue);
        BigDecimal cashValue = getAsset(user1Holdings, "CASH");
        Assert.assertEquals(BigDecimal.valueOf(17.6848), cashValue);
    }

    private BigDecimal getAsset(List<Holding> user1Holdings, String asset) {
        return BigDecimal.valueOf(
                user1Holdings.stream().filter(h -> h.getAsset().equals(asset))
                        .findFirst()
                        .get().getHolding()
        );
    }
}
