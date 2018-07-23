package com.nutmeg.helpers;

import com.nutmeg.transactions.Transaction;
import com.nutmeg.transactions.TxnType;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TransactionsParser {

    private static final String SEPARATOR = ",";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static List<Transaction> parseTransactions(File transactionFile) throws IOException {
        List<Transaction> transactions = new ArrayList<>();
        BufferedReader br = null;
        String line = "";

        try {
            br = new BufferedReader(new FileReader(transactionFile));
            while ((line = br.readLine()) != null) {
                String[] parsedTransaction = line.split(SEPARATOR);
                try {
                    Transaction transaction = new TransactionBuilder()
                            .setAccount(parsedTransaction[0])
                            .setAsset(parsedTransaction[5])
                            .setDate(LocalDate.parse(parsedTransaction[1],formatter))
                            .setPrice(Double.valueOf(parsedTransaction[4]))
                            .setTxnType(TxnType.valueOf(parsedTransaction[2]))
                            .setUnits(Double.valueOf(parsedTransaction[3]))
                            .createTransaction();
                    transactions.add(transaction);
                } catch(Exception e) {
                    System.out.println( "error parsing line " + line);
                    System.out.println( e);
                }

            }

        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println( e);
                }
            }
        }
        return transactions;
    }
}
