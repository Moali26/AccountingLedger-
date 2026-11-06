package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    public static List<Transaction> getTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader("src/main/resources/transactions.csv");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String row;
            bufferedReader.readLine();
            while ((row = bufferedReader.readLine()) != null) {
                String[] brokenRow = row.split("\\|");
                String date = brokenRow[0];
                String time = brokenRow[1];
                String description = brokenRow[2];
                String vendor = brokenRow[3];
                double amount = Double.parseDouble(brokenRow[4]);
                Transaction transaction = new Transaction(date, time, description, vendor, amount);
                transactions.add(transaction);

            }
            bufferedReader.close();


        } catch (Exception e) {
            CliColors.error("Error reading transactions: " + e.getMessage());
        }
        return transactions;

    }

    public static void saveTransaction(Transaction transaction) {
        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/transactions.csv", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);


            bufferedWriter.write(transaction.toString());
            bufferedWriter.newLine();
            bufferedWriter.close();

        }
        catch(IOException e){
            CliColors.error("Error saving transaction: " + e.getMessage());
        }
    }
}