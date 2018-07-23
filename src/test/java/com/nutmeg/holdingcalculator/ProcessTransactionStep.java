package com.nutmeg.holdingcalculator;

import com.nutmeg.helpers.TransactionBuilder;
import com.nutmeg.holdingcalculatator.TransactionService;
import com.nutmeg.holdingcalculatator.TransactionServiceImpl;
import com.nutmeg.transactions.Holding;
import com.nutmeg.transactions.Transaction;
import com.nutmeg.transactions.TxnType;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;
import org.junit.Assert;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProcessTransactionStep {

    private TransactionService transactionService = new TransactionServiceImpl();
    private Transaction transaction;
    private LocalDate holdDate;
    private Double currentAmount;
    private List<Holding> currentHolding;
    private List<Holding> processedTransaction;


    @Given("^a stock purchase transaction \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void a_stock_purchase_transaction(String account,
                                             String date,
                                             String txnType,
                                             String units,
                                             String price,
                                             String asset) throws Exception {
        transaction = new TransactionBuilder()
                .setAccount(account)
                .setDate(null)
                .setTxnType(TxnType.valueOf(txnType))
                .setUnits(Double.valueOf(units))
                .setPrice(Double.valueOf(price))
                .setAsset(asset)
                .createTransaction();
    }

    @Given("^A hold date \"([^\"]*)\"$")
    public void a_hold_date(String localDateString) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        holdDate = LocalDate.parse(localDateString, formatter);
    }

    @Given("the user has these holdings")
    public void the_user_has_these_holdings(DataTable dataTable) {
        currentHolding = new ArrayList<>();
        for (Map<Object, Object> data : dataTable.asMaps(String.class, String.class)) {
            String amount = (String) data.get("amount");
            String asset = (String) data.get("asset");
            Holding holding = new Holding();
            holding.setAsset(asset);
            holding.setHoldings(Double.valueOf(amount));
            currentHolding.add(holding);
        }
    }

    @When("^I process the transaction$")
    public void i_process_the_transaction() throws Exception {
        processedTransaction = transactionService.processTransaction(transaction, holdDate, currentHolding );
    }

    @Then("I should deduct\\/increase the cash balance of the customer by the value of {string} times {string}")
    public void i_should_deduct_the_cash_balance_of_the_customer_by_the_value_of_the_transaction_times(String units, String price) throws Exception {
        BigDecimal unitsDecimal = new BigDecimal(units);
        BigDecimal priceDecimal = new BigDecimal(price);
        BigDecimal amount = unitsDecimal.multiply(priceDecimal, MathContext.DECIMAL64);
        amount = amount.setScale(4, RoundingMode.HALF_UP);
        Holding cash = processedTransaction.stream().filter(t -> t.getAsset().equals("CASH")).findFirst().get();
        BigDecimal cashDecimal = BigDecimal.valueOf(cash.getHolding()).setScale(4, RoundingMode.HALF_UP);
        Holding previousCash = currentHolding.stream().filter(t -> t.getAsset().equals("CASH")).findFirst().get();
        BigDecimal previousCashDecimal = BigDecimal.valueOf(previousCash.getHolding());
        BigDecimal expectedAmount = null;
        if(transaction.getTxnType().equals(TxnType.BOT)){
            expectedAmount = previousCashDecimal.subtract(amount).setScale(4,RoundingMode.HALF_UP);
        } else if(transaction.getTxnType().equals(TxnType.SLD)) {
            expectedAmount = previousCashDecimal.add(amount).setScale(4,RoundingMode.HALF_UP);
        } else if(transaction.getTxnType().equals(TxnType.DIV)) {
            expectedAmount = previousCashDecimal.add(amount).setScale(4,RoundingMode.HALF_UP);
        } else if(transaction.getTxnType().equals(TxnType.DEP)) {
            expectedAmount = previousCashDecimal.add(amount).setScale(4,RoundingMode.HALF_UP);
        } else if(transaction.getTxnType().equals(TxnType.WDR)) {
            expectedAmount = previousCashDecimal.subtract(amount).setScale(4,RoundingMode.HALF_UP);
        }
        Assert.assertEquals(expectedAmount, cashDecimal);
    }
}
