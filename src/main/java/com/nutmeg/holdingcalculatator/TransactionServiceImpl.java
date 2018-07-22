package com.nutmeg.holdingcalculatator;

import com.nutmeg.transactions.ProcessedTransaction;
import com.nutmeg.transactions.Transaction;
import com.nutmeg.transactions.TxnType;

import java.time.LocalDate;

public class TransactionServiceImpl implements TransactionService {
    @Override
    public ProcessedTransaction processTransaction(Transaction transaction, LocalDate localDate, Double currentAmount) {
        
    }
}
