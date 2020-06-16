package com.amoskorir.kmlparser.kmlutils.models;

public class PlaceMarker {
    private Geometry geometry;

    private String name;


    public PlaceMarker(Geometry geometry, String name) {
        this.geometry = geometry;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public Geometry getGeometry() {
        return geometry;
    }
}