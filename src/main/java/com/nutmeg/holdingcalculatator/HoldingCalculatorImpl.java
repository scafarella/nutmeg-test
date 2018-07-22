package com.nutmeg.holdingcalculatator;

import com.nutmeg.transactions.Holding;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class HoldingCalculatorImpl implements HoldingCalculator {
    @Override
    public Map<String, List<Holding>> calculateHoldings(File transactionFile, LocalDate date) {
        return null;
    }
}
