package day18;

import java.awt.*;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Graph {
    private final Set<Point> nodes;
    private final Map<Point, Set<Point>> connections;

    public Graph(Set<Point> nodes, Map<Point, Set<Point>> connections) {
        this.nodes = nodes;
        this.connections = connections;
    }

    public Point getNode(Point id) {
        return nodes.stream()
            .filter(node -> node.equals(id))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("No node found with ID"));
    }

    public Set<Point> getConnections(Point node) {
        return connections.get(node).stream()
            .map(this::getNode)
            .collect(Collectors.toSet());
    }
}


