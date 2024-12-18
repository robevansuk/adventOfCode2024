package day18;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Day18 {

    private final List<List<Integer>> input;
    private final HashSet<Point> nodes;
    private final HashMap<Point, Set<Point>> connections;
    private String[][] grid;
    private MyScorer targetScorer;
    private MyScorer nextNodeScorer;
    private Graph graph;

    public Day18(List<List<Integer>> input) {
        this.input = input;
        targetScorer = new MyScorer();

        nodes = new HashSet<>();
        connections = new HashMap<>();

        this.graph = new Graph(nodes, connections);
    }

    public List<List<String>> createGrid(List<List<Integer>> coords) {
        for (int i=0; i<coords.size(); i++) {
            for (int j=0; j<coords.get(i).size(); j++) {
                if (coords.contains(List.of(i, j))) {
                    grid[i][j] = "#";
                } else {
                    grid[i][j] = ".";
                }
            }
        }
        return (List<List<String>>) Arrays.asList(Arrays.asList(grid));
    }

    public List<Point> findRoute() {
        Queue<RouteNode> openSet = new PriorityQueue<>();
        Map<Point, RouteNode> allNodes = new HashMap<>();

        Point from = new Point(0, 0);
        Point to = new Point(70,70);

        RouteNode start = new RouteNode(from, null, 0d, targetScorer.computeCost(from, to));
        openSet.add(start);
        allNodes.put(from, start);

        while (!openSet.isEmpty()) {
            RouteNode next = openSet.poll();

            if (next.getCurrent().equals(to)) {
                List<Point> route = new ArrayList<>();
                RouteNode current = next;
                do {
                    route.add(0, current.getCurrent());
                    current = allNodes.get(current.getPrevious());
                } while (current != null);
                return route;
            }

            graph.getConnections(next.getCurrent()).forEach(connection -> {
                RouteNode nextNode = allNodes.getOrDefault(connection, new RouteNode(connection));
                allNodes.put(connection, nextNode);

                double newScore = next.getRouteScore() + nextNodeScorer.computeCost(next.getCurrent(), connection);
                if (newScore < nextNode.getRouteScore()) {
                    nextNode.setPrevious(next.getCurrent());
                    nextNode.setRouteScore(newScore);
                    nextNode.setEstimatedScore(newScore + targetScorer.computeCost(connection, to));
                    openSet.add(nextNode);
                }
            });
        }
        throw new IllegalStateException("No route found");
    }
}
