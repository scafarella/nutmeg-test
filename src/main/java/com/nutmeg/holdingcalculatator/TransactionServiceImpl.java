package com.nutmeg.holdingcalculatator;

import com.nutmeg.transactions.Holding;
import com.nutmeg.transactions.Transaction;
import com.nutmeg.transactions.TxnType;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class TransactionServiceImpl implements TransactionService {

    private final static String CASH_ASSET = "CASH";
    private final static Integer SCALE = 4;

    @Override
    public List<Holding> processTransaction(final Transaction transaction, final LocalDate localDate, final List<Holding> currentHolding) {
        Holding assetHolding = null;
        Holding cashHolding = null;
        List<Holding> updatedHolding = new ArrayList<>();

        if(localDate.isBefore(transaction.getDate())) {
            return currentHolding;
        }

        for(Holding holding : currentHolding) {
            if(holding.getAsset().equals(CASH_ASSET)){
                cashHolding = holding;
            } else if(holding.getAsset().equals(transaction.getAsset())){
                assetHolding = holding;
            } else {
                updatedHolding.add(holding);
            }
        }
        updatedHolding.addAll(processHolding(transaction, assetHolding, cashHolding));
        return updatedHolding;

    }

    private List<Holding> processHolding(final Transaction transaction, final Holding assetHolding, final Holding cashHolding) {
        final List<Holding> updatedHoldings = new ArrayList<>();
        final BigDecimal currentCashAmount = cashHolding!=null?BigDecimal.valueOf(cashHolding.getHolding()):BigDecimal.ZERO;
        final BigDecimal currentAssetQuantity = assetHolding != null? BigDecimal.valueOf(assetHolding.getHolding()): BigDecimal.ZERO;
        final BigDecimal transactionPrice = BigDecimal.valueOf(transaction.getPrice());
        final BigDecimal transactionUnits = BigDecimal.valueOf(transaction.getUnits());
        BigDecimal updatedCashAmount = BigDecimal.ZERO;
        BigDecimal updatedAssetQuantity = BigDecimal.ZERO;
        if(transaction.getTxnType().equals(TxnType.BOT)){
            updatedCashAmount = currentCashAmount.subtract(transactionPrice.multiply(transactionUnits, MathContext.DECIMAL64));
            updatedAssetQuantity = currentAssetQuantity.add(transactionUnits);
        } else if(transaction.getTxnType().equals(TxnType.SLD)){
            updatedCashAmount = currentCashAmount.add(transactionPrice.multiply(transactionUnits, MathContext.DECIMAL64));
            updatedAssetQuantity = currentAssetQuantity.subtract(transactionUnits);
        } else if(transaction.getTxnType().equals(TxnType.DIV)){
            updatedCashAmount = currentCashAmount.add(transactionPrice.multiply(transactionUnits, MathContext.DECIMAL64));
            updatedAssetQuantity = currentAssetQuantity;
        } else if(transaction.getTxnType().equals(TxnType.DEP)){
            updatedCashAmount = currentCashAmount.add(transactionPrice.multiply(transactionUnits, MathContext.DECIMAL64));
            updatedAssetQuantity = currentAssetQuantity;
        } else if(transaction.getTxnType().equals(TxnType.WDR)){
            updatedCashAmount = currentCashAmount.subtract(transactionPrice.multiply(transactionUnits, MathContext.DECIMAL64));
            updatedAssetQuantity = currentAssetQuantity;
        }

        Holding upadatedCashHolding = new Holding();
        upadatedCashHolding.setAsset(CASH_ASSET);
        BigDecimal updatedCashAmountRounded = updatedCashAmount.setScale(SCALE, RoundingMode.HALF_UP);
        upadatedCashHolding.setHoldings(updatedCashAmountRounded.doubleValue());
        updatedHoldings.add(upadatedCashHolding);

        if(transaction.getTxnType().equals(TxnType.BOT) ||
                transaction.getTxnType().equals(TxnType.SLD) ||
                transaction.getTxnType().equals(TxnType.DIV)){
            Holding upadatedAssetHolding = new Holding();
            upadatedAssetHolding.setAsset(transaction.getAsset());
            BigDecimal updatedAssetQuantityRounded = updatedAssetQuantity.setScale(SCALE, RoundingMode.HALF_UP);
            upadatedAssetHolding.setHoldings(updatedAssetQuantityRounded.doubleValue());
            if(updatedAssetQuantityRounded.compareTo(BigDecimal.ZERO) > 0){
                updatedHoldings.add(upadatedAssetHolding);
            }
        }

        return updatedHoldings;
    }
}
