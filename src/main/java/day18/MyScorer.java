package day18;

import java.awt.*;

public class MyScorer {

    public MyScorer() {
    }

    public double computeCost(Point from, Point to) {
        double R = 6372.8; // Earth's Radius, in kilometers

        double dLat = Math.toRadians(to.getX() - from.getX());
        double dLon = Math.toRadians(to.getY() - from.getY());
        double lat1 = Math.toRadians(from.getX());
        double lat2 = Math.toRadians(to.getX());

        double a = Math.pow(Math.sin(dLat / 2), 2)
                + Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return R * c;
    }
}
