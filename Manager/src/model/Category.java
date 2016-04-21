package model;


public class Category {
    private String nameCategory;
    private String id;

    public Category(String nameCategory, String id) {
        this.nameCategory = nameCategory;
        this.id = id;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public String getId() {
        return id;
    }
}
