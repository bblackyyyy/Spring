package org.example;

import java.io.IOException;
import java.util.List;

interface IVehicleRepository {
    void rentCar(String brand, String model);

    void returnCar(String brand, String model);

    List<Vehicle> getVehicles();

    void save() throws IOException;

    void addVehicle(Vehicle vehicle);
}
