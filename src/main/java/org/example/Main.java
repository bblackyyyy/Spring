package org.example;

import org.mindrot.jbcrypt.BCrypt;
import org.h2.tools.Server;

import java.io.*;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.Vector;


public class Main {
    public static void main(String[] args) {
        IVehicleRepository repository = new VehicleRepository("vehicles.csv");
        Vector<User> usersVector = new Vector<>();
        try (BufferedReader br = new BufferedReader(new FileReader("users.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                usersVector.add(new User(parts[0], parts[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }




        Scanner scanner = new Scanner(System.in);
        System.out.println("\n1. login");
        System.out.println("2. register");
        int opt = scanner.nextInt();
        scanner.nextLine();
        boolean old = false;

        switch (opt) {
            case 1:
                old = login(usersVector, scanner);
                break;

            case 2:
                register(usersVector, scanner);
                old = true;
                break;


        }


        if (old == true) {
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



    public static boolean login(Vector<User> users, Scanner scanner) {
    System.out.println("Login");
    System.out.println("Enter your username:");
    String login = scanner.nextLine();
    System.out.println("Enter your password:");
    String password = scanner.nextLine();

    if (login.equals("admin") && password.equals("admin")) {
        System.out.println("Showing all users:");
        for (User user : users) {
            System.out.println(user.getLogin());
        }
        return false;
    }

    for (User user : users) {
        if (user.getLogin().equals(login) && BCrypt.checkpw(password, user.getPassword())) {
            System.out.println("Login Success");
            return true;
        }
    }
    System.out.println("Login Failed: Incorrect username or password");
    return false;
}



public static void register(Vector<User> users, Scanner scanner) {
    boolean registered = false;
    while (!registered) {
        System.out.println("Register");
        System.out.println("Enter your username:");
        String login = scanner.nextLine();
        if (userExists(users, login)) {
            System.out.println("Username already exists. Please choose a different username.");
        } else {
            System.out.println("Enter your password:");
            String password = scanner.nextLine();
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            users.add(new User(login, hashedPassword));
            try {
                saveUsersToFile(users, "users.csv");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Registration Successful");
            registered = true;
        }
    }
}

    public static boolean userExists(Vector<User> users, String login) {
        for (User user : users) {
            if (user.getLogin().equals(login)) {
                return true;
            }
        }
        return false;
    }

    public static void saveUsersToFile(Vector<User> users, String filename) throws SQLException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true))) {
            for (User user : users) {
                bw.write(user.getLogin() + ";" + user.getPassword());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Server.createWebServer("-webAllowOthers","-webPort","8082");
        } catch (SQLException e) {
            throw new RuntimeException(e);


        }
        Server.createTcpServer("-tcpSSL", "-tcpAllowOthers", "-tcpPort", "9092", "-key", "server.jks", "password").start();
    }
}