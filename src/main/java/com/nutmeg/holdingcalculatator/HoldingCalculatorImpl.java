package com.nutmeg.holdingcalculatator;

import com.nutmeg.helpers.TransactionsParser;
import com.nutmeg.transactions.Holding;
import com.nutmeg.transactions.Transaction;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HoldingCalculatorImpl implements HoldingCalculator {
    private TransactionService transactionService;

    public HoldingCalculatorImpl() {
        this.transactionService = new TransactionServiceImpl();
    }

    @Override
    public Map<String, List<Holding>> calculateHoldings(File transactionFile, LocalDate date) {
        Map<String,List<Holding>> holdings = new HashMap<>();
        try {
            List<Transaction> transactions = TransactionsParser.parseTransactions(transactionFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return holdings;
    }
}
