package org.example;

import java.io.IOException;
import java.util.List;

interface IVehicleRepository {
    void rentCar(String brand, String model);
    void returnVehicle(Vehicle vehicle);
    void removeVehicle(Vehicle vehicle);
    Vehicle getVehicle(String brand, String model);
    List<Vehicle> getVehicles();
    void save() throws IOException;
    void addVehicle(Vehicle vehicle);
    boolean vehicleExists(Vehicle vehicle);

    void returnCar(String returnBrand, String returnModel);
}