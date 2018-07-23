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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;

        if (account != null ? !account.equals(that.account) : that.account != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (txnType != that.txnType) return false;
        if (units != null ? !units.equals(that.units) : that.units != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        return asset != null ? asset.equals(that.asset) : that.asset == null;
    }

    @Override
    public int hashCode() {
        int result = account != null ? account.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (txnType != null ? txnType.hashCode() : 0);
        result = 31 * result + (units != null ? units.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (asset != null ? asset.hashCode() : 0);
        return result;
    }
}
