package org.example;
class Motorcycle extends Vehicle {
    private String category;

    public Motorcycle(String brand, String model, int year, double price, String category) {
        super(brand, model, year, price);
        this.category = category;
    }

    @Override
    public String getType() {
        return "Motorcycle";
    }

    @Override
    public String toCSV() {
        return "Motorcycle;" + brand + ";" + model + ";" + year + ";" + price + ";" + category + ";" + rented;
    }
}