package com.pthfndr;

import java.util.ArrayList;

public class GraphBuilder {
    public Node head; //Graph head
    public Node target; //Graph target
    private int n, m; // com.pthfndr.Matrix dimensions
    private double X, Y;

    private double xNet, yNet;
    private double diagonalS;
    private double maxTan;
    private double minTan;
    private double friction;
    private double wElevation;

    private ArrayList<Node> nodes;

    GraphBuilder() {
        this.head = null;
        this.target = null;
        this.n = 0;
        this.m = 0;
        this.X = 0;
        this.Y = 0;
        this.xNet = 0;
        this.yNet = 0;
        this.maxTan = 0;
        this.minTan = 0;
        this.friction = 0.02;
        this.wElevation = 0;
        this.nodes = null;
    }

    GraphBuilder(int n, int m, double X, double Y) {
        this();
        this.n = n;
        this.m = m;
        this.X = X;
        this.Y = Y;
        nodes = new ArrayList<>(n * m);
    }

    public double getxNet() {
        return this.xNet;
    }

    public double getyNet() {
        return this.yNet;
    }

    public void setMaxTan(double maxTan) {
        this.maxTan = maxTan;
    }
    public void setMinTan(double minTan) {
        this.minTan = minTan;
    }
    public void setFriction(double friction) {
        this.friction = friction;
    }

    public void setwElevation(double wElevation) {
        this.wElevation = wElevation;
    }

    public void setDimensions(double X, double Y) { this.X = X; this.Y = Y; }

    public void setSize(int n, int m) {
        this.n = n; this.m = m;
        nodes = new ArrayList<>(n * m);
    }

    public boolean buildGraph(int xH, int yH, int xT, int yT, Matrix heightMatrix) {
        if (xH < 0 || xH >= m || yH < 0 || yH >= n) {
            return false;
        }
        if (xT < 0 || xT >= m || yT < 0 || yT >= n) {
            return false;
        }

        Node.idCounter = 0;

        xNet = X / m;
        yNet = Y / n;

        for (int i = 0; i < n; i ++) {
            for (int j = 0; j < m; j++) {
                nodes.add(new Node(j, i));
            }
        }

        for (int i = 0; i < n; i ++) {
            for (int j = 0; j < m; j++) {
                Node currNode = nodes.get(i * m + j);
                if (j == xH && i == yH){
                    head = currNode;
                    head.setG(0);
                    head.setL(0);
                }
                if (j == xT && i == yT){
                    target = currNode;
                }

                int nodeType = nodeType(i, j);
                diagonalS = Math.sqrt(xNet * xNet + yNet * yNet);

                switch (nodeType) {
                    case 0 -> {
                        addUNeighbour(currNode, i, j, heightMatrix); //U
                        addBNeighbour(currNode, i, j, heightMatrix); //B
                        addLNeighbour(currNode, i, j, heightMatrix); //L
                        addRNeighbour(currNode, i, j, heightMatrix); //R
                        addLUNeighbour(currNode, i, j, heightMatrix); //LU
                        addLBNeighbour(currNode, i, j, heightMatrix); //LB
                        addRBNeighbour(currNode, i, j, heightMatrix); //RB
                        addRUNeighbour(currNode, i, j, heightMatrix); //RU
                    }
                    case 1 -> {
                        addBNeighbour(currNode, i, j, heightMatrix); //B
                        addRNeighbour(currNode, i, j, heightMatrix); //R
                        addRBNeighbour(currNode, i, j, heightMatrix); //RB
                    }
                    case 2 -> {
                        addUNeighbour(currNode, i, j, heightMatrix); //U
                        addRNeighbour(currNode, i, j, heightMatrix); //R
                        addRUNeighbour(currNode, i, j, heightMatrix); //RU
                    }
                    case 3 -> {
                        addUNeighbour(currNode, i, j, heightMatrix); //U
                        addLNeighbour(currNode, i, j, heightMatrix); //L
                        addLUNeighbour(currNode, i, j, heightMatrix); //LU
                    }
                    case 4 -> {
                        addBNeighbour(currNode, i, j, heightMatrix); //B
                        addLNeighbour(currNode, i, j, heightMatrix); //L
                        addLBNeighbour(currNode, i, j, heightMatrix); //LB
                    }
                    case 5 -> {
                        addUNeighbour(currNode, i, j, heightMatrix); //U
                        addBNeighbour(currNode, i, j, heightMatrix); //B
                        addRNeighbour(currNode, i, j, heightMatrix); //R
                        addRBNeighbour(currNode, i, j, heightMatrix); //RB
                        addRUNeighbour(currNode, i, j, heightMatrix); //RU
                    }
                    case 6 -> {
                        addUNeighbour(currNode, i, j, heightMatrix); //U
                        addLNeighbour(currNode, i, j, heightMatrix); //L
                        addRNeighbour(currNode, i, j, heightMatrix); //R
                        addLUNeighbour(currNode, i, j, heightMatrix); //LU
                        addRUNeighbour(currNode, i, j, heightMatrix); //RU
                    }
                    case 7 -> {
                        addUNeighbour(currNode, i, j, heightMatrix); //U
                        addBNeighbour(currNode, i, j, heightMatrix); //B
                        addLNeighbour(currNode, i, j, heightMatrix); //L
                        addLUNeighbour(currNode, i, j, heightMatrix); //LU
                        addLBNeighbour(currNode, i, j, heightMatrix); //LB
                    }
                    case 8 -> {
                        addBNeighbour(currNode, i, j, heightMatrix); //B
                        addLNeighbour(currNode, i, j, heightMatrix); //L
                        addRNeighbour(currNode, i, j, heightMatrix); //R
                        addLBNeighbour(currNode, i, j, heightMatrix); //LB
                        addRBNeighbour(currNode, i, j, heightMatrix); //RB
                    }
                }
            }
        }

        nodes.forEach(node -> {
            node.calculateHeuristic(target);
        });

        return true;
    }

    private void addUNeighbour(Node currNode, int i, int j, Matrix heightMatrix) {
        double s = yNet;
        //U neighbour
        int nI = i - 1;
        int nJ = j;
        double currHeight = heightMatrix.getVal(i, j);
        double h = heightMatrix.getVal(nI, nJ) - currHeight;
        if (validEdge(s, h)) {
            currNode.addEdge(calculateWeight(s, h, heightMatrix.getVal(nI, nJ)), calculateLength(s, h), nodes.get(nI * m + nJ));
        }
    }

    private void addBNeighbour(Node currNode, int i, int j, Matrix heightMatrix) {
        double s = yNet;
        //B neighbour
        int nI = i + 1;
        int nJ = j;
        double currHeight = heightMatrix.getVal(i, j);
        double h = heightMatrix.getVal(nI, nJ) - currHeight;
        if (validEdge(s, h)) {
            currNode.addEdge(calculateWeight(s, h, heightMatrix.getVal(nI, nJ)), calculateLength(s, h), nodes.get(nI * m + nJ));
        }
    }

    private void addLNeighbour(Node currNode, int i, int j, Matrix heightMatrix) {
        double s = xNet;
        //L neighbour
        int nI = i;
        int nJ = j - 1;
        double currHeight = heightMatrix.getVal(i, j);
        double h = heightMatrix.getVal(nI, nJ) - currHeight;
        if (validEdge(s, h)) {
            currNode.addEdge(calculateWeight(s, h, heightMatrix.getVal(nI, nJ)), calculateLength(s, h), nodes.get(nI * m + nJ));
        }
    }

    private void addRNeighbour(Node currNode, int i, int j, Matrix heightMatrix) {
        double s = xNet;
        //R neighbour
        int nI = i;
        int nJ = j + 1;
        double currHeight = heightMatrix.getVal(i, j);
        double h = heightMatrix.getVal(nI, nJ) - currHeight;
        if (validEdge(s, h)) {
            currNode.addEdge(calculateWeight(s, h, heightMatrix.getVal(nI, nJ)), calculateLength(s, h), nodes.get(nI * m + nJ));
        }
    }

    private void addLUNeighbour(Node currNode, int i, int j, Matrix heightMatrix) {
        double s = diagonalS;
        //LU neighbour
        int nI = i - 1;
        int nJ = j - 1;
        double currHeight = heightMatrix.getVal(i, j);
        double h = heightMatrix.getVal(nI, nJ) - currHeight;
        if (validEdge(s, h)) {
            currNode.addEdge(calculateWeight(s, h, heightMatrix.getVal(nI, nJ)), calculateLength(s, h), nodes.get(nI * m + nJ));
        }
    }

    private void addLBNeighbour(Node currNode, int i, int j, Matrix heightMatrix) {
        double s = diagonalS;
        //LU neighbour
        int nI = i + 1;
        int nJ = j - 1;
        double currHeight = heightMatrix.getVal(i, j);
        double h = heightMatrix.getVal(nI, nJ) - currHeight;
        if (validEdge(s, h)) {
            currNode.addEdge(calculateWeight(s, h, heightMatrix.getVal(nI, nJ)), calculateLength(s, h), nodes.get(nI * m + nJ));
        }
    }

    private void addRBNeighbour(Node currNode, int i, int j, Matrix heightMatrix) {
        double s = diagonalS;
        //LU neighbour
        int nI = i + 1;
        int nJ = j + 1;
        double currHeight = heightMatrix.getVal(i, j);
        double h = heightMatrix.getVal(nI, nJ) - currHeight;
        if (validEdge(s, h)) {
            currNode.addEdge(calculateWeight(s, h, heightMatrix.getVal(nI, nJ)), calculateLength(s, h), nodes.get(nI * m + nJ));
        }
    }

    private void addRUNeighbour(Node currNode, int i, int j, Matrix heightMatrix) {
        double s = diagonalS;
        //LU neighbour
        int nI = i - 1;
        int nJ = j + 1;
        double currHeight = heightMatrix.getVal(i, j);
        double h = heightMatrix.getVal(nI, nJ) - currHeight;
        if (validEdge(s, h)) {
            currNode.addEdge(calculateWeight(s, h, heightMatrix.getVal(nI, nJ)), calculateLength(s, h), nodes.get(nI * m + nJ));
        }
    }

    private boolean validEdge(double s, double h) {
        return h / s <= maxTan && h / s >= minTan;
    }

    private int nodeType(int i, int j) {
        if (i == 0 && j == 0) { //LU corner node
            return 1;
        }
        else if (i == n - 1 && j == 0) { //LB corner node
            return 2;
        }
        else if (i == n - 1 && j == m - 1) { //RB corner node
            return 3;
        }
        else if (i == 0 && j == m - 1) { //RU corner node
            return 4;
        }
        else if (j == 0) { //L side node
            return 5;
        }
        else if (i == n - 1) { //B side node
            return 6;
        }
        else if (j == m - 1) { //R side node
            return 7;
        }
        else if (i == 0) { //U side node
            return 8;
        }
        else { //Center node
            return 0;
        }
    }

    private double calculateLength(double s, double h) {
        return Math.sqrt(s * s + h * h);
    }

    private double calculateWeight(double s, double h, double elevation) {
        double weight;
        if (h > 0) {
            weight = h + friction * s;
        }
        else {
            weight =  friction * s;
        }

        if (elevation <= wElevation) {
            weight *= 50;
        }

        return weight;
    }

    public ArrayList<Pair<Integer, Integer>> getCoordinateListByPath(ArrayList<Integer> path) {

        if(nodes == null) {
            return null;
        }

        ArrayList<Pair<Integer, Integer>> coordinateList = new ArrayList<>(path.size());
        for (Integer id: path) {
            coordinateList.add(
                    new Pair<>(nodes.get(id).getCoordinates())
            );
        }

        return  coordinateList;
    }

}
