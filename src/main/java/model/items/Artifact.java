package model.items;

public class Artifact {
    private int id;
    private String name;
    private String description;
    private String category;
    private int price;
    private boolean availability;

    public Artifact(int id, String name, String description, String category, int price, boolean availability) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.availability = availability;
    }

    public Artifact(int id, String category, int price, boolean availability) {
        this.id = id;
        this.category = category;
        this.price = price;
        this.availability = availability;
    }

    public Artifact(int id, String name, String category, int price, String description){
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public int getPrice() {
        return price;
    }

    public boolean isAvailability() {
        return availability;
    }
}
