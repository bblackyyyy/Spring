package org.example;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;



class VehicleRepository implements IVehicleRepository {
    private List<Vehicle> vehicles;
    private String fileName = "vehicles.csv";
    private boolean modified;
    public VehicleRepository(String fileName) {
        this.fileName = fileName;
        this.vehicles = new ArrayList<>();
        this.modified = false;
        load();
    }



    @Override
    public void rentCar(String brand, String model) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.brand.equals(brand) && vehicle.model.equals(model) && !vehicle.rented) {
                vehicle.rented = true;
                System.out.println("Car " + brand + " " + model + " rented successfully.");
                return;
            }
        }
        System.out.println("Car " + brand + " " + model + " not available for rent.");
    }

    @Override
    public void returnCar(String brand, String model) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.brand.equals(brand) && vehicle.model.equals(model) && vehicle.rented) {
                vehicle.rented = false;
                System.out.println("Car " + brand + " " + model + " returned successfully.");
                return;
            }
        }
        System.out.println("Car " + brand + " " + model + " not rented.");
    }

    @Override
    public List<Vehicle> getVehicles() {
        return vehicles;
    }





    public void printAvailableCars() {
        System.out.println("Available cars:");
        for (Vehicle vehicle : vehicles) {
            if (vehicle instanceof Car && !vehicle.rented) {
                System.out.println(vehicle);
            }
        }
    }


    @Override
    public void save() throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (Vehicle vehicle : vehicles) {
                writer.println(vehicle.toCSV());
            }
        }
    }

    @Override
    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
        System.out.println(vehicle.getType() + " " + vehicle.brand + " " + vehicle.model + " added successfully.");
        try {
            save();
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }




    private void load() {
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(";");
                if (parts[0].equals("Car")) {
                    vehicles.add(new Car(parts[1], parts[2], Integer.parseInt(parts[3]), Double.parseDouble(parts[4])));
                } else if (parts[0].equals("Motorcycle")) {
                    vehicles.add(new Motorcycle(parts[1], parts[2], Integer.parseInt(parts[3]), Double.parseDouble(parts[4]), parts[5]));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }
}