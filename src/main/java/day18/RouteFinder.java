package day18;

import java.awt.*;
import java.util.List;

public class RouteFinder {
    private final Graph graph;
    private final MyScorer nextNodeScorer;
    private final MyScorer targetScorer;

    public RouteFinder(Graph graph, MyScorer nextNodeScorer, MyScorer targetScorer) {
        this.graph = graph;
        this.nextNodeScorer = nextNodeScorer;
        this.targetScorer = targetScorer;
    }

    public List<Point> findRoute(Point from, Point to) {
        throw new IllegalStateException("No route found");
    }
}
