package com.nutmeg.holdingcalculator.helpers;

import com.nutmeg.helpers.TransactionsParser;
import com.nutmeg.transactions.Transaction;
import com.nutmeg.transactions.TxnType;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TransactionsParserTest {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static List<Transaction> validaTransactions = Arrays.asList(
            new Transaction("NEAA0000", LocalDate.parse("20170101", formatter), TxnType.DEP, 100d, 1d, "CASH"),
            new Transaction("NEAA0000", LocalDate.parse("20170102", formatter), TxnType.BOT, 20d, 1.500d, "VUKE")
            );
    @Test
    public void shouldParseATransactionFile() throws IOException{
        ClassLoader classLoader = getClass().getClassLoader();
        File transactions = new File(classLoader.getResource("validTransactions.csv").getFile());
        List<Transaction> transactionList = TransactionsParser.parseTransactions(transactions);
        int idx = 0;
        for(Transaction t : transactionList) {
            Assert.assertEquals(validaTransactions.get(idx), t);
            idx++;
        }
    }

    @Test
    public void shouldThrowFileNotFoundEexceptionATransactionFile() throws IOException{
        ClassLoader classLoader = getClass().getClassLoader();
        assertThrows(FileNotFoundException.class, () -> TransactionsParser.parseTransactions(new File("notExists.csv")));
    }
}
