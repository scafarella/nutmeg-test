package com.nutmeg.holdingcalculatator;

import com.nutmeg.transactions.Holding;
import com.nutmeg.transactions.ProcessedTransaction;
import com.nutmeg.transactions.Transaction;
import com.nutmeg.transactions.TxnType;

import java.time.LocalDate;
import java.util.List;

public class TransactionServiceImpl implements TransactionService {

    private final static String CASH_ASSET = "CASH";
    @Override
    public List<Holding> processTransaction(final Transaction transaction, final LocalDate localDate, final List<Holding> currentHolding) {
/*        Holding assetHolding = currentHolding.stream()
                .filter(holding -> holding.getAsset().equals(transaction.getAsset()))
                .findFirst()
                .get();
        Holding cashHolding = currentHolding.stream()
                .filter(holding -> holding.getAsset().equals(CASH_ASSET))
                .findFirst()
                .get();

        if(transaction.getTxnType().equals(TxnType.BOT)){

        }

        currentHolding.forEach(holding -> {

        });*/
        return null;


    }
}
