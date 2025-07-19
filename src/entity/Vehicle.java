package entity;

public class Vehicle {

    int id;
    String number_plate;
    String model;

    public Vehicle(String number_plate, String model) {
        this.number_plate = number_plate;
        this.model = model;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getNumber_plate() {
        return number_plate;
    }

    public void setNumber_plate(String number_plate) {
        this.number_plate = number_plate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return STR."Vehicle{id=\{id}, number_plate='\{number_plate}', model='\{model}'}";
    }
}
