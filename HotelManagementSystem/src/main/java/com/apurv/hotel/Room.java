package com.apurv.hotel;
import java.io.Serializable;

public class Room implements Serializable {

    private int roomNumber;
    private RoomType roomType;
    private double price;
    private boolean isAvailable;

    public Room(int roomNumber, RoomType roomType, double price) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.price = price;
        this.isAvailable = true;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean status) {
        this.isAvailable = status;
    }

    public void displayRoom() {
        System.out.println("Room No: " + roomNumber);
        System.out.println("Type: " + roomType);
        System.out.println("Price: " + price);
        System.out.println("Available: " + isAvailable);
        System.out.println("----------------------");
    }
}
