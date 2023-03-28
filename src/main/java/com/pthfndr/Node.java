package com.pthfndr;

import java.util.ArrayList;

public class Node implements Comparable<Node> {

    // Id for readability of result purposes
    public static int idCounter = 0;
    private int id;

    //Coordinates
    Pair<Integer, Integer> coordinates;

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
        this.coordinates = null;
    }

    Node(int x, int y) {
        this();
        this.coordinates = new Pair<>(x, y);
    }

    // Getters

    public int getId() {
        return this.id;
    }

    public Node getParent() {
        return this.parent;
    }

    public ArrayList<Edge> getNeighbours() {
        return this.neighbours;
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

    public int getX() { return this.coordinates.getL(); }

    public int getY() { return this.coordinates.getR(); }

    public Pair<Integer, Integer> getCoordinates() { return this.coordinates; }

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
        this.neighbours.add(newEdge);
    }

    public double calculateHeuristic(Node target) { //Chebyshev distance heuristic
        this.h = Math.max(Math.abs(target.getX() - this.coordinates.getL()), Math.abs(target.getY() - this.coordinates.getR()));
        return this.h;
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
