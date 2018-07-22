package com.nutmeg.holdingcalculator;

import com.nutmeg.helpers.TransactionBuilder;
import com.nutmeg.holdingcalculatator.TransactionService;
import com.nutmeg.holdingcalculatator.TransactionServiceImpl;
import com.nutmeg.transactions.ProcessedTransaction;
import com.nutmeg.transactions.Transaction;
import com.nutmeg.transactions.TxnType;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ProcessTransactionStep {

    private TransactionService transactionService = new TransactionServiceImpl();
    private Transaction transaction;
    private LocalDate holdDate;
    private Double currentAmount;
    private ProcessedTransaction processedTransaction;


    @Given("^a stock purchase transaction \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void a_stock_purchase_transaction(String account,
                                             String date,
                                             String txnType,
                                             Double units,
                                             Double price,
                                             String asset) throws Exception {
        transaction = new TransactionBuilder()
                .setAccount(account)
                .setDate(null)
                .setTxnType(TxnType.valueOf(txnType))
                .setUnits(units)
                .setPrice(price)
                .setAsset(asset)
                .createTransaction();
    }

    @Given("^A hold date \"([^\"]*)\"$")
    public void a_hold_date(String localDateString) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        holdDate = LocalDate.parse(localDateString, formatter);
    }

    @Given("^a \"([^\"]*)\"$")
    public void a(Double currentAmount) throws Exception {
        this.currentAmount = currentAmount;
    }

    @When("^I process the transaction$")
    public void i_process_the_transaction() throws Exception {
        processedTransaction = transactionService.processTransaction(transaction, holdDate, currentAmount );
    }

    @Then("^I should deduct the cash balance of the customer by the value of the transaction \\(\"([^\"]*)\" times \"([^\"]*)\"\\)$")
    public void i_should_deduct_the_cash_balance_of_the_customer_by_the_value_of_the_transaction_times(Double units, Double price) throws Exception {
        Assert.assertEquals(units*price, processedTransaction.getAmount(), 0 );
        throw new PendingException();
    }
}
