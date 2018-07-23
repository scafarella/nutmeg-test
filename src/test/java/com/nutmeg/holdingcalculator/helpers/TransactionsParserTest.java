package com.nutmeg.holdingcalculator.helpers;

import com.nutmeg.helpers.TransactionsParser;
import com.nutmeg.transactions.Transaction;
import com.nutmeg.transactions.TxnType;
import org.junit.Assert;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class TransactionsParserTest {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final String VALID_TRANSACTIONS_FILE = "validTransactions.csv";
    private static final String TRANSACTIONS_FILE_WITH_ERRORS = "validTransactions.csv";
    private static final String NOT_TRANSACTIONS_FILE = "notExists.csv";

    private static List<Transaction> validTransactions = Arrays.asList(
            new Transaction("NEAA0000", LocalDate.parse("20170101", formatter), TxnType.DEP, 100d, 1d, "CASH"),
            new Transaction("NEAA0000", LocalDate.parse("20170102", formatter), TxnType.BOT, 20d, 1.500d, "VUKE")
            );
    @Test
    public void shouldParseATransactionFile() throws IOException{
        ClassLoader classLoader = getClass().getClassLoader();
        File transactions = new File(classLoader.getResource(VALID_TRANSACTIONS_FILE).getFile());
        List<Transaction> transactionList = TransactionsParser.parseTransactions(transactions);
        int idx = 0;
        for(Transaction t : transactionList) {
            Assert.assertEquals(validTransactions.get(idx), t);
            idx++;
        }
    }

    @Test(expected = FileNotFoundException.class)
    public void shouldThrowFileNotFoundEexceptionATransactionFile() throws IOException{
        TransactionsParser.parseTransactions(new File(NOT_TRANSACTIONS_FILE));
    }

    @Test
    public void shouldSkipNotParsableTransactions() throws Exception{
        ClassLoader classLoader = getClass().getClassLoader();
        File transactions = new File(classLoader.getResource(TRANSACTIONS_FILE_WITH_ERRORS).getFile());
        List<Transaction> transactionList = TransactionsParser.parseTransactions(transactions);
        int idx = 0;
        for(Transaction t : transactionList) {
            Assert.assertEquals(validTransactions.get(idx), t);
            idx++;
        }
    }
}
