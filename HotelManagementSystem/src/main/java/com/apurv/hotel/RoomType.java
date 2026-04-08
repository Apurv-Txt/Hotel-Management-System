package com.apurv.hotel;

public enum RoomType {

    STANDARD(2000),
    DELUXE(4000),
    SUITE(8000);

    private double price;

    RoomType(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}