package com.apurv.hotel;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class HotelManagement {

    public static void saveRooms(ArrayList<Room> rooms) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(
                    new FileOutputStream("rooms.dat")
            );

            oos.writeObject(rooms);
            oos.close();

        } catch (Exception e) {
            System.out.println("Error saving rooms!");
        }
    }
    public static void saveCustomers(ArrayList<Customer> customers) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(
                    new FileOutputStream("customers.dat")
            );
            oos.writeObject(customers);
            oos.close();
        } catch (Exception e) {
            System.out.println("Error saving customers!");
        }
    }
    public static void loadRooms(ArrayList<Room> rooms) {
        try {
            ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream("rooms.dat")
            );
            ArrayList<Room> savedRooms = (ArrayList<Room>) ois.readObject();
            rooms.addAll(savedRooms);
            ois.close();
        } catch (Exception e) {
            System.out.println("No previous data found.");
        }
    }
    public static void loadCustomers(ArrayList<Customer> customers) {
        try {
            ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream("customers.dat")
            );
            ArrayList<Customer> savedCustomers =
                    (ArrayList<Customer>) ois.readObject();
            customers.addAll(savedCustomers);
            ois.close();
        } catch (Exception e) {
            System.out.println("No previous customer data found.");
        }
    }

    public static void main(String[] args) {
        //ArrayList is used to store all customer records sequentially
        // while HashMap is used to map room numbers to customers for faster access and lookup.
        ArrayList<Room> rooms = new ArrayList<>();
        loadRooms(rooms);  //loading data from the file
        ArrayList<Customer> customers = new ArrayList<>();
        loadCustomers(customers);
        HashMap<Integer, Customer> roomCustomerMap = new HashMap<>();
        for (int i = 0; i < customers.size(); i++) {
            roomCustomerMap.put(
                    customers.get(i).getRoomNumber(),
                    customers.get(i)
            );
        }

        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("1. Add Room");
            System.out.println("2. Display Rooms");
            System.out.println("3. Add Customer (Book Room)");
            System.out.println("4. Display Customers");
            System.out.println("5. Remove Customer (Checkout Room)");
            System.out.println("6. Remove Room");
            System.out.println("7. Exit");

            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.print("Enter Room Number: ");
                    int rno = sc.nextInt();
                    sc.nextLine();

                    boolean exists = false;  //at start, no room exists

                    for (int i = 0; i < rooms.size(); i++) {          //check if the room already exists
                        if (rooms.get(i).getRoomNumber() == rno) {
                            exists = true;
                            break;
                        }
                    }

                    if (exists) {
                        System.out.println("Room already exists!");
                    } else {
                        System.out.print("Enter Room Type: ");
                        System.out.println("1. STANDARD");
                        System.out.println("2. DELUXE");
                        System.out.println("3. SUITE");

                        int typeChoice = sc.nextInt();

                        RoomType type = null;

                        switch (typeChoice) {
                            case 1:
                                type = RoomType.STANDARD;
                                break;
                            case 2:
                                type = RoomType.DELUXE;
                                break;
                            case 3:
                                type = RoomType.SUITE;
                                break;
                            default:
                                System.out.println("Invalid type!");
                                break;
                        }

                        if (type == null) {
                            break;
                        }

                        double price = type.getPrice();

                        Room room;

                        if (type == RoomType.DELUXE) {
                            room = new DeluxeRoom(rno, type, price, true, true);
                        }
                        else if (type == RoomType.SUITE) {
                            room = new SuiteRoom(rno, type, price, true, true, true, true);
                        }
                        else {
                            room = new Room(rno, type, price);
                        }
                        rooms.add(room);
                        saveRooms(rooms);

                        System.out.println("Room added successfully!");
                    }

                    break;

                case 2:
                    if (rooms.size() == 0) {
                        System.out.println("No rooms available!");
                    } else {
                        for (int i = 0; i < rooms.size(); i++) {
                            Room r = rooms.get(i);
                            r.displayRoom();
                            if (r instanceof SuiteRoom) {             //actual obj type is checked using instance of
                                ((SuiteRoom) r).displaySuiteFeatures(); //suite features will only b there in suite room
                            }
                            else if (r instanceof DeluxeRoom) {
                                ((DeluxeRoom) r).displayDeluxeFeatures();
                            }
                            System.out.println("---------");
                        }
                    }
                    break;

                case 3:        //prevents double booking of room
                    System.out.print("Enter Customer ID: ");
                    int cid = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Room Number: ");
                    int roomNo = sc.nextInt();

                    boolean found = false;
                    // check the availability status of the room using a boolean flag, if true,
                    // booking is allowed,and it is set to false, otherwise booking is denied......
                    for (int i = 0; i < rooms.size(); i++) {
                        if (rooms.get(i).getRoomNumber() == roomNo) {
                            if (rooms.get(i).isAvailable()) {                   //checks the current state
                                rooms.get(i).setAvailable(false);               //updates the current state
                                saveRooms(rooms);

                                Customer cust = new Customer(cid, name, roomNo);
                                customers.add(cust);
                                roomCustomerMap.put(roomNo, cust);
                                saveCustomers(customers);

                                //Billing
                                System.out.print("Enter number of days: ");
                                Integer days = sc.nextInt();                        //AUTOBOXING

                                Double pricePerDay = rooms.get(i).getPrice();
                                Double total = pricePerDay * days;                //calculation performed using unboxing

                                System.out.println("----- BILL -----");
                                System.out.println("Customer Name: " + name);
                                System.out.println("Room Number: " + roomNo);
                                System.out.println("Price per Day: " + pricePerDay);
                                System.out.println("Number of Days: " + days);
                                System.out.println("Total Amount: " + total);
                                System.out.println("----------------");

                                System.out.println("Room booked successfully!");
                            } else {
                                System.out.println("Room is already booked!");
                            }

                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        System.out.println("Room not found!");
                    }
                    break;

                case 4:

                    if (customers.size() == 0) {
                        System.out.println("No customers found!");
                    } else {
                        for (int i = 0; i < customers.size(); i++) {
                            customers.get(i).displayCustomer();
                        }
                    }
                    break;


                case 5:
                    if (customers.size() == 0) {
                        System.out.println("No customers to remove!");
                        break;
                    }
                    System.out.print("Enter Customer ID to remove: ");
                    int removeId = sc.nextInt();

                    boolean removed = false;

                    for (int i = 0; i < customers.size(); i++) {
                        if (customers.get(i).getCustomerId() == removeId) {

                            int roomNoToFree = customers.get(i).getRoomNumber();//fetch the room no to be freed
                            // free the room
                            for (int j = 0; j < rooms.size(); j++) {        //cleaning thread
                                if (rooms.get(j).getRoomNumber() == roomNoToFree) {
                                    CleaningThread t = new CleaningThread(rooms.get(j));
                                    t.start();

                                    try {
                                        t.join();   // wait for cleaning to finish
                                    } catch (InterruptedException e) {
                                        System.out.println("Thread interrupted");
                                    }
                                    saveRooms(rooms);
                                    saveCustomers(customers);
                                    break;
                                }
                            }

                            // remove from hashmap
                            roomCustomerMap.remove(roomNoToFree);
                            // remove from list
                            customers.remove(i);
                            saveCustomers(customers);

                            System.out.println("Customer removed and room freed!");
                            removed = true;
                            break;
                        }
                    }
                    if (!removed) {
                        System.out.println("Customer not found!");
                    }
                    break;


                case 6:
                    System.out.print("Enter Room Number to remove: ");
                    int roomNoToRemove = sc.nextInt();
                    boolean roomFound = false;
                    for (int i = 0; i < rooms.size(); i++) {
                        if (rooms.get(i).getRoomNumber() == roomNoToRemove) {
                            roomFound = true;
                            if (!rooms.get(i).isAvailable()) {
                                System.out.println("Room is currently booked. Cannot remove!");
                            } else {
                                rooms.remove(i);
                                System.out.println("Room removed successfully!");
                                saveRooms(rooms);
                            }
                            break;
                        }
                    }
                    if (!roomFound) {
                        System.out.println("Room not found!");
                    }
                    break;

                case 7:
                    System.out.println("Exiting program...");
                    break;


                default:
                    System.out.println("Invalid choice! Please enter a valid option.");

            }

        } while (choice != 7);

        sc.close();
    }
}
