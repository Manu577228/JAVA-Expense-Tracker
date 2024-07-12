package org.example;

import java.io.*;
import java.util.*;

class Expense implements Serializable {
    private static final long serialVersionUID = 1L;
    private String description;
    private double amount;
    private String category;
    private Date date;

    // Constructor to initialize an expense
    public Expense(String description, double amount, String category, Date date) {
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    // Getter methods for expense attributes
    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Expense [Description: " + description + ", Amount: " + amount + ", Category: " + category + ", Date: " + date + "]";
    }
}

// Class to manage expenses
class ExpenseManager {
    private List<Expense> expenses;

    public ExpenseManager() {
        expenses = new ArrayList<>();
        loadExpenses();
    }

    // Method to add an expense
    public void addExpense(Expense expense) {
        expenses.add(expense);
        saveExpenses();
    }

    // Method to view all expenses
    public List<Expense> viewExpenses() {
        return new ArrayList<>(expenses);
    }

    // Method to get expenses by category
    public List<Expense> getExpenseByCategory(String category) {
        List<Expense> result = new ArrayList<>();
        for (Expense expense : expenses) {
            if (expense.getCategory().equalsIgnoreCase((category))) {
                result.add(expense);
            }
        }
        return result;
    }

    // Method to get total expenses
    public double getTotalExpenses() {
        double total = 0;
        for (Expense expense : expenses) {
            total += expense.getAmount();
        }
        return total;
    }

    // Method to save expenses to a file
    private void saveExpenses() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("expenses.dat"))) {
            oos.writeObject(expenses);
        } catch (IOException e) {
            System.out.println("Error saving expenses: " + e.getMessage());
        }
    }

    // Method to load the expenses from a file
    private void loadExpenses() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("expenses.dat"))) {
            expenses = (List<Expense>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No existing expense data found. Starting new.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading expenses: " + e.getMessage());
        }
    }
}

public class ExpenseTracker {
    private static ExpenseManager manager = new ExpenseManager();

    // Method to display menu options
    private static void displayMenu() {
        System.out.println("\nExpense Tracker");
        System.out.println("1. Add Expense");
        System.out.println("2. View Expenses");
        System.out.println("3. View Expenses by Category");
        System.out.println("4. View total expenses");
        System.out.println("5. Exit");
        System.out.println("Enter your choice");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    // Add a new expense
                    System.out.println("enter description");
                    String description = scanner.nextLine();
                    System.out.println("enter amount: ");
                    double amount = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.println("enter category: ");
                    String category = scanner.nextLine();
                    Date date = new Date();
                    Expense expense = new Expense(description, amount, category, date);
                    manager.addExpense(expense);
                    System.out.println("Expense added successfully.");
                    break;
                case 2:
                    // Viewing all expenses
                    System.out.println("listing all expenses: ");
                    List<Expense> expenses = manager.viewExpenses();
                    if (expenses.isEmpty()) {
                        System.out.println("no expenses recorded.");
                    } else {
                        for (Expense exp : expenses) {
                            System.out.println(exp);
                        }
                    }
                    break;
                case 3:
                    // Viewing the expenses by category
                    System.out.println("enter category: ");
                    category = scanner.nextLine();
                    System.out.println("Listing expenses for category: " + category);
                    expenses = manager.getExpenseByCategory(category);
                    if (expenses.isEmpty()) {
                        System.out.println("No expenses found for this category.");
                    } else {
                        for (Expense exp : expenses) {
                            System.out.println(exp);
                        }
                    }
                    break;
                case 4:
                    // view total expenses
                    System.out.println("Total expenses: " + manager.getTotalExpenses());
                    break;
                case 5:
                    // exiting the program
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
