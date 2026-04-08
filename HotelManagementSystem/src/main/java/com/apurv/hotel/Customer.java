package com.apurv.hotel;
import java.io.Serializable;

//uniqueness for customer ids has not been enforced ->
//one customer can book multiple rooms......
public class Customer implements Serializable {

    private int customerId;
    private String name;
    private int roomNumber;

    public Customer(int customerId, String name, int roomNumber) {
        this.customerId = customerId;
        this.name = name;
        this.roomNumber = roomNumber;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void displayCustomer() {
        System.out.println("Customer ID: " + customerId);
        System.out.println("Name: " + name);
        System.out.println("Room No: " + roomNumber);
        System.out.println("----------------------");
    }
}
