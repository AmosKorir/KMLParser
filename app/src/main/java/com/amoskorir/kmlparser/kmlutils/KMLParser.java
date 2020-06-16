package com.amoskorir.kmlparser.kmlutils;

import android.util.Xml;

import com.amoskorir.kmlparser.kmlutils.models.Geometry;
import com.amoskorir.kmlparser.kmlutils.models.MultiGeometry;
import com.amoskorir.kmlparser.kmlutils.models.PlaceMarker;
import com.amoskorir.kmlparser.kmlutils.models.Point;
import com.amoskorir.kmlparser.kmlutils.models.Polygon;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import kotlin.collections.ArrayDeque;

public class KMLParser {
    private static final String ns = null;

    public List<PlaceMarker> parse(InputStream in) throws IOException {

        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readKml(parser);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            in.close();
        }
        return new ArrayDeque<>();
    }

    private List<PlaceMarker> readKml(XmlPullParser parser) throws IOException, XmlPullParserException {
        List placeMarkers = new ArrayList<PlaceMarker>();

        parser.require(XmlPullParser.START_TAG, ns, "kml");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("Document")) {
                placeMarkers.add(readPlaceMarkers(parser));
            } else {
                skip(parser);
            }
        }
        return placeMarkers;
    }


    private List<PlaceMarker> readPlaceMarkers(XmlPullParser parser) throws IOException, XmlPullParserException {
        List placeMarkers = new ArrayList<PlaceMarker>();

        parser.require(XmlPullParser.START_TAG, ns, "Document");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("Placemark")) {
                placeMarkers.add(readMarker(parser));
            } else {
                skip(parser);
            }
        }
        return placeMarkers;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

    private PlaceMarker readMarker(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "Placemark");
        Geometry geometry = null;
        String markerName = "";
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("name")) {
                markerName = readName(parser);
            } else if (name.equals("MultiGeometry")) {
                geometry = readTopMultiGeometry(parser);
            } else {
                skip(parser);
            }
        }
        return new PlaceMarker(geometry, markerName);
    }

    private String readName(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "name");
        String markerName = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "name");
        return markerName;
    }


    private Geometry readTopMultiGeometry(XmlPullParser parser) throws IOException, XmlPullParserException {

        parser.require(XmlPullParser.START_TAG, ns, "MultiGeometry");

        MultiGeometry multiGeometry = null;
        Point point = null;

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("Point")) {
                point = readPoint(parser);
            } else if (name.equals("MultiGeometry")) {
                multiGeometry = readMultiGeometry(parser);
            } else {
                skip(parser);
            }
        }
        return new Geometry(multiGeometry, point);

    }

    private MultiGeometry readMultiGeometry(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "MultiGeometry");
        Polygon polygon = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("Polygon")) {
                polygon = readPolygon(parser);
            } else {
                skip(parser);
            }
        }
        return new MultiGeometry(polygon);


    }

    private Point readPoint(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "Point");
        String line = "";
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("coordinates")) {
                line = reaCoordinates(parser);
            } else {
                skip(parser);
            }
        }
        return new Point(line);
    }

    private Polygon readPolygon(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "Polygon");
        String line = "";
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("outerBoundaryIs")) {
                line = readOuterBoundary(parser);
            } else {
                skip(parser);
            }
        }
        return new Polygon(line);
    }

    private String readOuterBoundary(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "outerBoundaryIs");
        String line = "";
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("LinearRing")) {
                line = readLineRing(parser);
            } else {
                skip(parser);
            }
        }
        return line;
    }

    private String readLineRing(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "LinearRing");
        String line = "";
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("coordinates")) {
                line = reaCoordinates(parser);
            } else {
                skip(parser);
            }
        }
        return line;
    }

    private String reaCoordinates(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "coordinates");
        String line = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "coordinates");
        return line;
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }
}

