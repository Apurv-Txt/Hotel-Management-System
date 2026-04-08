package com.apurv.hotel;
import java.io.Serializable;

public class SuiteRoom extends DeluxeRoom implements Serializable{

    private boolean privatePool;
    private boolean personalButler;

    public SuiteRoom(int roomNumber, RoomType roomType, double price,
                     boolean wifi, boolean breakfast,
                     boolean privatePool, boolean personalButler) {

        super(roomNumber, roomType, price, wifi, breakfast);
        this.privatePool = privatePool;
        this.personalButler = personalButler;
    }

    public void displaySuiteFeatures() {
        displayDeluxeFeatures(); // inherited features
        System.out.println("Private Pool: " + privatePool);
        System.out.println("Personal Butler: " + personalButler);
    }
}