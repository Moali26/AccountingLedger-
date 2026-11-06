package org.example;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or

// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            CliColors.title("===== ACCOUNTING LEDGER =====");
            CliColors.option("(D) Add Deposit");
            CliColors.option("(P)Make Payment");
            CliColors.option("(L) Ledger");
            CliColors.option("(X) Exit");

            CliColors.prompt("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "D":
                    CliColors.prompt("Enter deposit description");
                    String depositDescription = scanner.nextLine();

                    CliColors.prompt("Enter deposit vendor");
                    String depositVendor = scanner.nextLine();

                    CliColors.prompt("Enter deposit amount");

                    try {
                        double depositAmount = Double.parseDouble(scanner.nextLine());

                        String depositDate = LocalDate.now().toString();
                        String depositTime = LocalTime.now().withNano(0).toString();

                        Transaction deposit = new Transaction(
                                depositDate, depositTime,
                                depositDescription, depositVendor, depositAmount
                        );

                        if (depositAmount > 0) {
                            FileManager.saveTransaction(deposit);
                            CliColors.success("Deposit saved!");
                        } else {
                            CliColors.error("Deposit amount must be greater than zero");
                        }

                    } catch (NumberFormatException e) {
                        CliColors.error("Invalid amount. Please enter a number.");
                    }

                    break;

                case "P":
                    CliColors.info("Make Payment selected");

                    CliColors.prompt("Enter payment description");
                    String paymentDescription = scanner.nextLine();

                    CliColors.prompt("Enter payment vendor");
                    String paymentVendor = scanner.nextLine();

                    CliColors.prompt("Enter payment amount");

                    try {
                        double paymentAmount = Double.parseDouble(scanner.nextLine());

                        String paymentDate = LocalDate.now().toString();
                        String paymentTime = LocalTime.now().withNano(0).toString();

                        if (paymentAmount > 0) {
                            paymentAmount = -paymentAmount;

                            Transaction payment = new Transaction(
                                    paymentDate, paymentTime,
                                    paymentDescription, paymentVendor, paymentAmount
                            );

                            FileManager.saveTransaction(payment);
                            CliColors.success("Payment saved!");
                        } else {
                            CliColors.error("Payment amount must be greater than zero");
                        }

                    } catch (NumberFormatException e) {
                        CliColors.error("Invalid amount. Please enter a number.");
                    }

                    break;

                case "L":
                    CliColors.info("Ledger selected");
                    boolean ledgerRunning = true;
                    while (ledgerRunning) {
                        CliColors.title("===== LEDGER =====");
                        CliColors.option("(A) All Transactions");
                        CliColors.option("(D) Deposits");
                        CliColors.option("(P) Payments");
                        CliColors.option("(R) Reports");
                        CliColors.option("(H) Home");
                        CliColors.prompt("Enter your choice:");
                        String ledgerChoice = scanner.nextLine();
                        // view all transactions from most recent to least recent
                        List<Transaction> transactions = FileManager.getTransactions();
                        if (ledgerChoice.equalsIgnoreCase("A")) {

                            sortTransactions(transactions);
                            // view all deposits from most recent to least recent
                        } else if (ledgerChoice.equalsIgnoreCase("D")) {
                            List<Transaction> deposits = new ArrayList<>();
                            for (Transaction t : transactions) {
                                if (t.getAmount() > 0) {
                                    deposits.add(t);
                                }
                            }
                            // Collections.sort(transactions,Comparator.comparing(t -> t.getAmount() > 0 ));
                            sortTransactions(deposits);

                        } else if (ledgerChoice.equalsIgnoreCase("P")) {
                            List<Transaction> payments = new ArrayList<>();
                            for (Transaction t : transactions) {
                                if (t.getAmount() < 0) {
                                    payments.add(t);
                                }
                            }
                            sortTransactions(payments);

                        } else if (ledgerChoice.equalsIgnoreCase("R")) {
                            boolean reportsRunning = true;
                            while (reportsRunning) {
                                CliColors.title("===== REPORTS =====");
                                CliColors.option("1) Month to Date");
                                CliColors.option("2) Previous Month");
                                CliColors.option("3) Year to Date");
                                CliColors.option("4) Previous Year");
                                CliColors.option("5) Search by vendor");
                                CliColors.option("6) Custom Search");
                                CliColors.option("0) Back");

                                CliColors.prompt("Enter your choice");
                                String reportChoice = scanner.nextLine();

                                switch (reportChoice) {
                                    case "1":
                                        LocalDate today = LocalDate.now();
                                        List<Transaction> monthtoDate = new ArrayList<>();

                                        for (Transaction t : transactions) {
                                            LocalDate transactionDate = LocalDate.parse(t.getDate());

                                            if (transactionDate.getMonthValue() == today.getMonthValue()
                                                    && transactionDate.getYear() == today.getYear()
                                                    && transactionDate.getDayOfMonth() <= today.getDayOfMonth()) {
                                                monthtoDate.add(t);
                                            }

                                        }
                                        sortTransactions(monthtoDate);
                                        break;

                                    case "2":
                                        LocalDate previousMonth = LocalDate.now().minusMonths(1);
                                        List<Transaction> previousMonthTransactions = new ArrayList<>();
                                        for (Transaction t : transactions) {
                                            LocalDate transactionDate = LocalDate.parse(t.getDate());
                                            if (transactionDate.getMonthValue() == previousMonth.getMonthValue()
                                                    && transactionDate.getYear() == previousMonth.getYear()) {

                                                previousMonthTransactions.add(t);

                                            }


                                        }
                                        sortTransactions(previousMonthTransactions);
                                        break;

                                    case "3":
                                        LocalDate currentDate = LocalDate.now();
                                        List<Transaction> yearToDate = new ArrayList<>();
                                        for (Transaction t : transactions) {
                                            LocalDate transactionDate = LocalDate.parse(t.getDate());
                                            if (transactionDate.getYear() == currentDate.getYear()) {
                                                yearToDate.add(t);
                                            }
                                        }
                                        sortTransactions(yearToDate);
                                        break;

                                    case "4":
                                        int previousYear = LocalDate.now().getYear() - 1;
                                        List<Transaction> previousYearTransactions = new ArrayList<>();
                                        for (Transaction t : transactions) {
                                            LocalDate transactionDate = LocalDate.parse(t.getDate());
                                            if (transactionDate.getYear() == previousYear) {
                                                previousYearTransactions.add(t);
                                            }
                                        }
                                        sortTransactions(previousYearTransactions);
                                        break;

                                    case "5":
                                        CliColors.prompt("Enter vendor name:");
                                        String searchVendor = scanner.nextLine();
                                        List<Transaction> vendorTransactions = new ArrayList<>();

                                        for (Transaction t : transactions) {
                                            if (t.getVendor().equalsIgnoreCase(searchVendor)) {
                                                vendorTransactions.add(t);
                                            }
                                        }
                                        sortTransactions(vendorTransactions);
                                        break;

                                    case "6":
                                        CliColors.prompt("Enter start date (YYYY-MM-DD) or leave blank");
                                        String startDateInput = scanner.nextLine();
                                        CliColors.prompt("Enter end date (YYYY-MM-DD) or leave blank");
                                        String endDateInput = scanner.nextLine();
                                        CliColors.prompt("Enter description or leave blank:");
                                        String descriptionInput = scanner.nextLine();
                                        CliColors.prompt("Enter vendor or leave blank:");
                                        String vendorInput = scanner.nextLine();
                                        CliColors.prompt("Enter amount or leave blank:");
                                        String amountInput = scanner.nextLine();
                                        List<Transaction> customResults = new ArrayList<>();
                                        try {
                                            for (Transaction t : transactions) {
                                                LocalDate transactionDate = LocalDate.parse(t.getDate());
                                                boolean matchesFilters = true;

                                                if (!startDateInput.isEmpty()) {
                                                    LocalDate startDate = LocalDate.parse(startDateInput);

                                                    if (transactionDate.isBefore(startDate)) {
                                                        matchesFilters = false;
                                                    }
                                                }

                                                if (!endDateInput.isEmpty()) {
                                                    LocalDate endDate = LocalDate.parse(endDateInput);

                                                    if (transactionDate.isAfter(endDate)) {
                                                        matchesFilters = false;
                                                    }
                                                }

                                                if (!descriptionInput.isEmpty()) {
                                                    if (!t.getDescription().equalsIgnoreCase(descriptionInput)) {
                                                        matchesFilters = false;
                                                    }
                                                }

                                                if (!vendorInput.isEmpty()) {
                                                    if (!t.getVendor().equalsIgnoreCase(vendorInput)) {
                                                        matchesFilters = false;
                                                    }
                                                }

                                                if (!amountInput.isEmpty()) {
                                                    double searchAmount = Double.parseDouble(amountInput);

                                                    if (t.getAmount() != searchAmount) {
                                                        matchesFilters = false;
                                                    }
                                                }
                                                if (matchesFilters) {
                                                    customResults.add(t);


                                                }

                                            }

                                            sortTransactions(customResults);
                                        } catch(java.time.format.DateTimeParseException e){
                                            CliColors.error("Invalid date. Please use YYYY-MM-DD");

                                        }catch (NumberFormatException e){
                                            CliColors.error("Invalid amount. Please enter a number.");
                                        }



                                        break;
                                    case "0":
                                        reportsRunning = false;
                                        break;
                                    default:
                                        CliColors.error("Invalid Reports choice. Please try again.");
                                        break;
                                }
                            }
                        } else if (ledgerChoice.equalsIgnoreCase("H")) {
                            ledgerRunning = false;

                        } else {
                            CliColors.error("Invalid Ledger choice. Please try again.");
                        }
                    }


                    break;

                case "X":
                    running = false;
                    CliColors.success("Thanks for using Accounting Ledger!");
                    break;

                default:
                    CliColors.error("Invalid choice. Please try again");
                    break;
            }

        }


    }

    // helper methods
    // utility method to sort transactions to most recent to least recent
    public static void sortTransactions(List<Transaction> e) {

        Collections.sort(e, Comparator.comparing(t -> t.getDate() + " " + t.getTime()));

        for (int i = e.size() - 1; i >= 0; i--) {
            CliColors.transaction(e.get(i));

        }
    }
}