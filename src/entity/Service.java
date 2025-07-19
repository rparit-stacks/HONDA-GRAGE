package entity;

public class Service {

    private int id;
   private String Description;
    private double cost;

    public Service(int id, String description, double cost) {
        this.id = id;
        Description = description;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        int i = 0;
        return "[ " + i++ + Description + cost + " ]";
    }
}
