package model.items;

public class Level {

    private int id;
    private String name;
    private int maxValue;

    public Level(int id, String name, int maxValue) {
        this.id = id;
        this.name = name;
        this.maxValue = maxValue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }
}
