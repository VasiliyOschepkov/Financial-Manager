package model;


public class Record {
    private String date;
    private String isPut;
    private double amount;
    private String description;
    private Category category;

    public Record(String date, String isPut, double amount, String description, Category category) {
        this.date = date;
        this.isPut = isPut;
        this.amount = amount;
        this.description = description;
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public String getIsPut() {
        return isPut;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }
}
