# Accounting Ledger Application

## About the Project

This is my Capstone 1 Accounting Ledger project. I built this application using Java to track deposits and payments. Transactions are saved to a CSV file so users can view and search their history after closing and reopening the program.

## Features

- Add deposits as positive amounts and payments as negative amounts.
- Save and read transactions from a file.
- View all transactions from newest to oldest, or view deposits and payments separately.
- Search using date reports, vendor names, and Custom Search.
- Display error messages for invalid menu choices and nonnumeric amounts, with date validation in Custom Search.
- Use a separate `CliColors` class for colored menus, prompts, errors, and transactions.

## How to Run

1. Download or clone the project from GitHub.
2. Open the `AccountingLedger` folder in IntelliJ IDEA and configure a Java SDK compatible with the project settings.
3. Make sure `src/main/resources/transactions.csv` exists with this first-line header:

   ```text
   date|time|description|vendor|amount
   ```

4. Open `src/main/java/org/example/Main.java` and run the `Main` class.
5. Follow the console menu options.

Use the `AccountingLedger` project folder as the working directory so the program can access the file. Colors require a console that supports ANSI escape codes.

## Menus and Reports

| Menu | Options |
| --- | --- |
| Home | D: Add Deposit, P: Make Payment, L: Ledger, X: Exit |
| Ledger | A: All Transactions, D: Deposits, P: Payments, R: Reports, H: Home |
| Reports | 1: Month To Date, 2: Previous Month, 3: Year To Date, 4: Previous Year, 5: Search by Vendor, 6: Custom Search, 0: Back |

Use uppercase letters at the Home menu. Enter positive amounts for both deposits and payments; the program converts payments to negative amounts before saving.

Custom Search supports start date, end date, description, vendor, and amount. Leave fields blank to skip those filters. Dates use `YYYY-MM-DD`, and date boundaries are inclusive. Description and vendor searches match the full field while ignoring capitalization. Transactions must match every filter entered. Use a negative amount when searching for a payment.

## Transaction Storage

Transactions are stored in `src/main/resources/transactions.csv`. The file uses pipes (`|`) to separate fields, with a header on the first line:

```text
date|time|description|vendor|amount
2026-11-05|09:30:00|Paycheck|Employer|1000.00
2026-11-05|12:15:00|Groceries|Walmart|-50.00
```

These are example records. New transactions use the current date and time. Avoid entering pipes in descriptions or vendor names.

## Classes and Technologies

| Class | Purpose |
| --- | --- |
| `Main` | Menus, user input, report filters, and sorted output |
| `Transaction` | Transaction data, constructor, getters, setters, and record formatting |
| `FileManager` | Reading and saving transactions |
| `CliColors` | Console formatting using ANSI escape codes |

Built with Java using collections, date/time APIs, and file input/output. Console colors use no external libraries. Deposits display in green and payments in red; saved records contain no color codes.

## What I Learned

This project helped me practice classes, objects, ArrayLists, loops, switch statements, file reading and writing, and try/catch statements. I also learned more about filtering transactions and connecting different parts of an application. Working through the project one piece at a time helped me understand how the code works together.
