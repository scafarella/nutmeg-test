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
    private static final Logger LOGGER = Logger.getLogger(TransactionsParser.class.getName());
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static List<Transaction> parseTransactions(File transactionFile) throws IOException {
        List<Transaction> transactions = new ArrayList<>();
        BufferedReader br = null;
        String line = "";

        try {
            br = new BufferedReader(new FileReader(transactionFile));
            while ((line = br.readLine()) != null) {
                String[] parsedTransaction = line.split(SEPARATOR);
                Transaction transaction = new TransactionBuilder()
                        .setAccount(parsedTransaction[0])
                        .setAsset(parsedTransaction[5])
                        .setDate(LocalDate.parse(parsedTransaction[1],formatter))
                        .setPrice(Double.valueOf(parsedTransaction[4]))
                        .setTxnType(TxnType.valueOf(parsedTransaction[2]))
                        .setUnits(Double.valueOf(parsedTransaction[3]))
                        .createTransaction();
                transactions.add(transaction);
            }

        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    LOGGER.log(Level.SEVERE, "error closing  stream", e);
                }
            }
        }
        return transactions;
    }
}
