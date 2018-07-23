package com.nutmeg.holdingcalculatator;

import com.nutmeg.helpers.TransactionsParser;
import com.nutmeg.transactions.Holding;
import com.nutmeg.transactions.Transaction;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
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
            transactions.forEach(t -> {
                List<Holding> currentHolding = new ArrayList<>();
                if(holdings.containsKey(t.getAccount())) {
                    currentHolding = holdings.get(t.getAccount());
                }
                List<Holding> updatedHolding = transactionService.processTransaction(t, date, currentHolding);
                holdings.put(t.getAccount(), updatedHolding);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
        return holdings;
    }
}
