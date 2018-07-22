package com.nutmeg.holdingcalculatator;

import com.nutmeg.transactions.ProcessedTransaction;
import com.nutmeg.transactions.Transaction;

import java.time.LocalDate;

public interface TransactionService {

    ProcessedTransaction processTransaction(final Transaction transaction, final LocalDate localDate, final Double currentAmount);
}
