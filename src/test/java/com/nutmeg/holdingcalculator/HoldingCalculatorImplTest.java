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
import java.util.List;
import java.util.Map;

public class HoldingCalculatorImplTest{

    private static final String TRANSACTIONS_FILE = "transactions.csv";
    private final HoldingCalculator holdingCalculator = new HoldingCalculatorImpl();
    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final String USER2 = "NEAB0001";
    private static final String USER1 = "NEAA0000";

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
        Assert.assertEquals(BigDecimal.valueOf(17.6849), cashValue);
    }

    private BigDecimal getAsset(List<Holding> user1Holdings, String asset) {
        return BigDecimal.valueOf(
                user1Holdings.stream().filter(h -> h.getAsset().equals(asset))
                        .findFirst()
                        .get().getHolding()
        );
    }
}
