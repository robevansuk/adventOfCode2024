package day18;

import java.awt.*;

public class RouteNode {

    private Point current;
    private Point previous;
    private double routeScore;
    private double estimatedScore;

    RouteNode(Point current) {
        this(current, null, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    }

    RouteNode(Point current, Point previous, double routeScore, double estimatedScore) {
        this.current = current;
        this.previous = previous;
        this.routeScore = routeScore;
        this.estimatedScore = estimatedScore;
    }

    Point getPoint() {
        return current;
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

    public Point getCurrent() {
        return current;
    }

    public Point getPrevious() {
        return previous;
    }

    public double getRouteScore() {
        return routeScore;
    }

    public double getEstimatedScore() {
        return estimatedScore;
    }

    public void setPrevious(Point point) {
        this.previous = point;
    }

    public void setRouteScore(double newScore) {
        this.routeScore = newScore;
    }

    public void setEstimatedScore(double v) {
        this.estimatedScore = v;
    }
}
