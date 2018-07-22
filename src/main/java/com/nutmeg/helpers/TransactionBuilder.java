package com.nutmeg.helpers;

import com.nutmeg.transactions.Transaction;
import com.nutmeg.transactions.TxnType;

import java.time.LocalDate;

public class TransactionBuilder {
    private String account;
    private LocalDate date;
    private TxnType txnType;
    private Double units;
    private Double price;
    private String asset;

    public TransactionBuilder setAccount(String account) {
        this.account = account;
        return this;
    }

    public TransactionBuilder setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public TransactionBuilder setTxnType(TxnType txnType) {
        this.txnType = txnType;
        return this;
    }

    public TransactionBuilder setUnits(Double units) {
        this.units = units;
        return this;
    }

    public TransactionBuilder setPrice(Double price) {
        this.price = price;
        return this;
    }

    public TransactionBuilder setAsset(String asset) {
        this.asset = asset;
        return this;
    }

    public Transaction createTransaction() {
        return new Transaction(account, date, txnType, units, price, asset);
    }
}