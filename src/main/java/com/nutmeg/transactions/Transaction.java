package com.nutmeg.transactions;

import java.time.LocalDate;

public class Transaction {
    public Transaction(String account, LocalDate date, TxnType txnType, Double units, Double price, String asset) {
        this.account = account;
        this.date = date;
        this.txnType = txnType;
        this.units = units;
        this.price = price;
        this.asset = asset;
    }

    private String account;
    private LocalDate date;
    private TxnType txnType;
    private Double units;
    private Double price;
    private String asset;

    public String getAccount() {
        return account;
    }

    public LocalDate getDate() {
        return date;
    }

    public TxnType getTxnType() {
        return txnType;
    }

    public Double getUnits() {
        return units;
    }

    public Double getPrice() {
        return price;
    }

    public String getAsset() {
        return asset;
    }
}
