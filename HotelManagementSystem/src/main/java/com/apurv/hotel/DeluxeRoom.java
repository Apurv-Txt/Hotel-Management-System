package com.apurv.hotel;
import java.io.Serializable;

public class DeluxeRoom extends Room implements Serializable {

    private boolean wifi;
    private boolean breakfast;

    public DeluxeRoom(int roomNumber, RoomType roomType, double price,
                      boolean wifi, boolean breakfast) {

        super(roomNumber, roomType, price);
        this.wifi = wifi;
        this.breakfast = breakfast;
    }

    public void displayDeluxeFeatures() {
        System.out.println("WiFi: " + wifi);
        System.out.println("Breakfast: " + breakfast);
    }
}