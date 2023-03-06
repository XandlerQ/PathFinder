import java.util.ArrayList;

public class Node implements Comparable<Node> {

    // Id for readability of result purposes
    private static int idCounter = 0;
    private int id;

    //Coordinates
    private int x;
    private int y;

    // Parent in the path
    private Node parent = null;

    private ArrayList<Edge> neighbours;

    // Evaluation functions
    private double f = Double.MAX_VALUE; //Cost function
    private double g = Double.MAX_VALUE; //Move function
    // Hardcoded heuristic
    private double h;

    Node() {
        this.id = idCounter++;
        this.neighbours = new ArrayList<>();
        this.x = 0;
        this.y = 0;
    }

    Node(int x, int y) {
        this();
        this.x = x;
        this.y = y;
    }

    // Getters

    public int getId() {
        return id;
    }

    public Node getParent() {
        return parent;
    }

    public ArrayList<Edge> getNeighbours() {
        return neighbours;
    }

    public double getF() {
        return f;
    }

    public double getG() {
        return g;
    }

    public double getH() {
        return h;
    }

    //Setters

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setF(double f) {
        this.f = f;
    }

    public void setG(double g) {
        this.g = g;
    }

    public void setH(double h) {
        this.h = h;
    }

    //Methods

    @Override
    public int compareTo(Node n) { //Compare two nodes by cost function
        return Double.compare(this.f, n.f);
    }

    public void addEdge(double weight, Node node) { //Add edge to node
        Edge newEdge = new Edge(weight, node);
        neighbours.add(newEdge);
    }

    public double calculateHeuristic(Node target) { //Chebyshev distance heuristic
        h = Math.max(Math.abs(target.x - x), Math.abs(target.y - y));
        return h;
    }

    public static class Edge {
        private double weight;
        private Node node;

        public double getWeight() {
            return weight;
        }

        public Node getNode() {
            return node;
        }
        Edge(double weight, Node node) {
            this.weight = weight;
            this.node = node;
        }
    }
}
