package model;


public class Account {
    private String nameAccount;
    private double balance;
    private String description;
    private int id;

    public Account(String nameAccount, double balance, String description, int id) {
        this.nameAccount = nameAccount;
        this.balance = balance;
        this.description = description;
        this.id = id;
    }

    public Account(String nameAccount, double balance, String description) {
        this.nameAccount = nameAccount;
        this.balance = balance;
        this.description = description;
    }

    public String getNameAccount() {
        return nameAccount;
    }

    public double getBalance() {
        return balance;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
