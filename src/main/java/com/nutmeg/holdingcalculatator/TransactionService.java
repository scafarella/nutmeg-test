package com.nutmeg.holdingcalculatator;

import com.nutmeg.transactions.Holding;
import com.nutmeg.transactions.ProcessedTransaction;
import com.nutmeg.transactions.Transaction;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {

    List<Holding> processTransaction(final Transaction transaction, final LocalDate localDate, List<Holding> currentHolding);
}
