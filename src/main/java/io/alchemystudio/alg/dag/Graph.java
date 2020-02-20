package io.alchemystudio.alg.dag;

import java.util.*;

/**
 * Created by weli on 26/02/2017.
 */
public class Graph {

    class Edge {
        private String from;
        private String to;
        private int weight;

        public String getFrom() {
            return from;
        }

        public String getTo() {
            return to;
        }

        public int getWeight() {
            return weight;
        }

        public Edge(String from, String to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "{" + from + "->" + to + " / " + weight + "}";
        }
    }

    private List<Edge> edges = new ArrayList<>();
    private Set<String> nodes = new HashSet<>();
    private Map<String, String> path = new HashMap<>(); // to -> from
    private Map<String, Integer> costs = new HashMap<>();

    private List<String> processed = new ArrayList<>();

    private String start = "start";
    private String fin = "fin";

    {
        processed.add(start);
        processed.add(fin);
    }

    public String getStart() {
        return start;
    }

    public String getFin() {
        return fin;
    }

    public void addEdge(String from, String to, int weight) {
        edges.add(new Edge(from, to, weight));
        nodes.add(from);
        nodes.add(to);

        if (from.equals(start)) {
            costs.put(to, weight);
        } else if (costs.get(to) == null) {
            costs.put(to, Integer.MAX_VALUE);
        }
    }

    private String nextCheapestNode() {
        if (nodes.size() == processed.size()) // all nodes are processed
            return null;

        int cheapest = Integer.MAX_VALUE;
        String cheapestNode = null;

        for (Map.Entry<String, Integer> cost : costs.entrySet()) {
            System.out.println("Updated cost:" + cost);
            if (cost.getValue() <= cheapest && !processed.contains(cost.getKey())) {
                cheapest = cost.getValue();
                cheapestNode = cost.getKey();
            }
        }
        System.out.println("next cheapest: " + cheapestNode);
        return cheapestNode;
    }

    public void dijkstra() {
        System.out.println("Initial costs: " + costs);

        String node = nextCheapestNode();

        path.put(node, start);
        while (node != null) {
            int cost = costs.get(node);
            Set<Edge> neighbors = findNeighbors(node);
            for (Edge neighbor : neighbors) {
                int newCost = cost + neighbor.getWeight();
                if (costs.get(neighbor.getTo()) > newCost) {
                    costs.put(neighbor.getTo(), newCost);
                    path.put(neighbor.getTo(), neighbor.getFrom());
                }
            }
            processed.add(node);
            node = nextCheapestNode();
        }
    }

    private String generatePath() {
        StringBuffer result = new StringBuffer();
        String next = null;
        for (Map.Entry<String, String> p : path.entrySet()) {
            if (p.getKey().equals(fin)) {
                result.append(p.getKey()).append(" <- ");
                next = p.getValue();
            }
        }

        while (next != null) {
            for (Map.Entry<String, String> p : path.entrySet()) {
                if (p.getKey().equals(next)) {
                    result.append(p.getKey()).append(" <- ");
                    next = p.getValue();
                    if (next.equals(start)) {
                        result.append(start);
                        next = null;
                    }
                }
            }
        }

        return result.toString();
    }

    private Set<Edge> findNeighbors(String node) {
        Set<Edge> neighbors = new HashSet<>();
        for (Edge edge : edges) {
            if (edge.getFrom().equals(node)) {
                neighbors.add(edge);
            }
        }
        return neighbors;
    }

    @Override
    public String toString() {
        return "Graph{" +
                "edges=" + edges +
                '}';
    }

    public Map<String, String> getPath() {
        return path;
    }

    public static void main(String[] args) throws Exception {

        Graph g = new Graph();
        g.addEdge("start", "a", 1);
        g.addEdge("start", "b", 1);
        g.addEdge("a", "b", 1);
        g.addEdge("b", "c", 1);
        g.addEdge("a", "c", 1);
        g.addEdge("c", "fin", 1);
        g.addEdge("b", "fin", 1);
        System.out.println(g);

        g.dijkstra();
        System.out.println(g.getPath());
        System.out.println(g.generatePath());
    }
}
