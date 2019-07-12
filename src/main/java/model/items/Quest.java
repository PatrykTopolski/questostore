package model.items;

public class Quest {

    private int id;
    private String name;
    private String description;
    private String category;
    private int reward;

    public Quest(int id, String name, String description, String category, int reward) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.reward = reward;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getReward() {
        return reward;
    }

    public String getDescription() {
        return description;
    }
}
