package com.apurv.hotel;

public class CleaningThread extends Thread {

    private Room room;

    public CleaningThread(Room room) {
        this.room = room;
    }

    public void run() {

        System.out.println("Room " + room.getRoomNumber() + " is being cleaned...");

        try {
            Thread.sleep(3000); // 3 seconds
        } catch (InterruptedException e) {
            System.out.println("Cleaning interrupted!");
        }

        room.setAvailable(true);    //set the room to be available after it is cleaned.

        System.out.println("Room " + room.getRoomNumber() + " is now available!");
    }
}