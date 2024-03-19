package org.example;


import java.io.IOException;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        IVehicleRepository repository = new VehicleRepository("vehicles.csv");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Rent a car");
            System.out.println("2. Return a car");
            System.out.println("3. Add a car");
            System.out.println("4. Print available cars");
            System.out.println("5. Exit");
            System.out.print("Select an option: ");


            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    System.out.print("Enter brand of the car: ");
                    String rentBrand = scanner.nextLine();
                    System.out.print("Enter model of the car: ");
                    String rentModel = scanner.nextLine();
                    repository.rentCar(rentBrand, rentModel);
                    break;
                case 2:
                    System.out.print("Enter brand of the car: ");
                    String returnBrand = scanner.nextLine();
                    System.out.print("Enter model of the car: ");
                    String returnModel = scanner.nextLine();
                    repository.returnCar(returnBrand, returnModel);
                    break;
                case 3:
                    System.out.print("Enter type of the vehicle (Car/Motorcycle): ");
                    String type = scanner.nextLine();
                    System.out.print("Enter brand of the vehicle: ");
                    String brand = scanner.nextLine();
                    System.out.print("Enter model of the vehicle: ");
                    String model = scanner.nextLine();
                    System.out.print("Enter year of the vehicle: ");
                    int year = scanner.nextInt();
                    System.out.print("Enter price of the vehicle: ");
                    double price = scanner.nextDouble();
                    scanner.nextLine();
                    if (type.equalsIgnoreCase("Car")) {
                        repository.addVehicle(new Car(brand, model, year, price));
                    } else if (type.equalsIgnoreCase("Motorcycle")) {
                        System.out.print("Enter category of the motorcycle: ");
                        String category = scanner.nextLine();
                        repository.addVehicle(new Motorcycle(brand, model, year, price, category));
                    } else {
                        System.out.println("Invalid vehicle type.");
                    }
                    break;
                case 4:
                    ((VehicleRepository) repository).printAvailableCars();
                        break;

                case 5:
                    try {
                        repository.save();
                    } catch (IOException e) {
                        System.out.println("Error saving data: " + e.getMessage());
                    }
                    System.out.println("Exiting...");
                    return;
            }
        }
    }
}