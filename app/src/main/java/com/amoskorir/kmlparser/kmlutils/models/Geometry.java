package com.amoskorir.kmlparser.kmlutils.models;

public class Geometry {


    private MultiGeometry multiGeometry;
    private Point point;

    public Geometry(MultiGeometry multiGeometry, Point point) {
        this.multiGeometry = multiGeometry;
        this.point = point;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public MultiGeometry getMultiGeometry() {
        return multiGeometry;
    }

    public void setMultiGeometry(MultiGeometry multiGeometry) {
        this.multiGeometry = multiGeometry;
    }
}
