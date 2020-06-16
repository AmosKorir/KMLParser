package com.amoskorir.kmlparser.kmlutils.models;

public class Point {
    private String coordinates;

    public Point(String coordinates) {
        this.coordinates = coordinates;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }
}

