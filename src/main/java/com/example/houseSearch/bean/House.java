package com.example.houseSearch.bean;

import java.sql.Date;

public class House {

    private String name;

    private String position;

    private Date today;

    private int price;

    private int height;

    private float size;

    private double distance;

    private String shape;

    private String destination;

    public void setName(String name) {
        this.name = name;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public void setToday(Date today) {
        this.today = today;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Date getToday() {
        return today;
    }

    public float getSize() {
        return size;
    }

    public double getDistance() {
        return distance;
    }

    public String getPosition() {
        return position;
    }

    public String getShape() {
        return shape;
    }

    public String getName() {
        return name;
    }

    public int getHeight() {
        return height;
    }

    public int getPrice() {
        return price;
    }

    public String getDestination() {
        return destination;
    }

    @Override
    public String toString() {
        return "House{" +
                "name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", today=" + today +
                ", price=" + price +
                ", height=" + height +
                ", size=" + size +
                ", distance=" + distance +
                ", shape='" + shape + '\'' +
                ", destination='" + destination + '\'' +
                '}';
    }
}
