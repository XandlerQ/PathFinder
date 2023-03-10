import java.util.ArrayList;

public class GraphBuilder {
    public Node head; //Graph head
    public Node target; //Graph target
    private int n, m; // Matrix dimensions
    private double X, Y;
    private double maxTan;
    public double[][] heightMatrix;    //Height matrix

    private ArrayList<Node> nodes;

    GraphBuilder() {
        this.head = null;
        this.target = null;
        this.n = 0;
        this.m = 0;
        this.X = 0;
        this.Y = 0;
        this.maxTan = 0;
        this.heightMatrix = null;
        this.nodes = null;
    }

    GraphBuilder(int n, int m, double X, double Y) {
        this();
        this.n = n;
        this.m = m;
        this.X = X;
        this.Y = Y;
        this.heightMatrix = new double[n][m];
        nodes = new ArrayList<>(n * m);
    }

    public void setMaxTan(double maxTan) {
        this.maxTan = maxTan;
    }

    public void setDimensions(double X, double Y) { this.X = X; this.Y = Y; }

    public void setSize(int n, int m) {
        this.n = n; this.m = m;
        heightMatrix = null;
        heightMatrix = new double[n][m];
        nodes = new ArrayList<>(n * m);
    }

    public boolean buildGraph(int xH, int yH, int xT, int yT) {
        if (xH < 0 || xH >= n || yH < 0 || yH >= m) {
            return false;
        }
        if (xT < 0 || xT >= n || yT < 0 || yT >= m) {
            return false;
        }

        double xNet = X / m;
        double yNet = Y / n;

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
                }
                if (j == xT && i == yT){
                    target = currNode;
                }

                int nodeType = nodeType(i, j);
                double currHeight = heightMatrix[i][j];
                double s;
                double h;
                int nI, nJ;
                double diagonalS = Math.sqrt(xNet * xNet + yNet * yNet);

                switch (nodeType) {
                    case 0 -> {
                        s = yNet;
                        //U neighbour
                        nI = i - 1;
                        nJ = j;
                        h = heightMatrix[nI][nJ] - currHeight;
                        if (Math.abs(h) / s <= maxTan) {
                            currNode.addEdge(calculateWeight(s, h), nodes.get(nI * m + nJ));
                        }

                        //B neighbour
                        nI = i + 1;
                        h = heightMatrix[nI][nJ] - currHeight;
                        if (Math.abs(h) / s <= maxTan) {
                            currNode.addEdge(calculateWeight(s, h), nodes.get(nI * m + nJ));
                        }
                        s = xNet;
                        //L neighbour
                        nI = i;
                        nJ = j - 1;
                        h = heightMatrix[nI][nJ] - currHeight;
                        if (Math.abs(h) / s <= maxTan) {
                            currNode.addEdge(calculateWeight(s, h), nodes.get(nI * m + nJ));
                        }

                        //R neighbour
                        nJ = j + 1;
                        h = heightMatrix[nI][nJ] - currHeight;
                        if (Math.abs(h) / s <= maxTan) {
                            currNode.addEdge(calculateWeight(s, h), nodes.get(nI * m + nJ));
                        }
                        s = diagonalS;
                        //LU neighbour
                        nI = i - 1;
                        nJ = j - 1;
                        h = heightMatrix[nI][nJ] - currHeight;
                        if (Math.abs(h) / s <= maxTan) {
                            currNode.addEdge(calculateWeight(s, h), nodes.get(nI * m + nJ));
                        }

                        //LB neighbour
                        nI = i + 1;
                        nJ = j - 1;
                        h = heightMatrix[nI][nJ] - currHeight;
                        if (Math.abs(h) / s <= maxTan) {
                            currNode.addEdge(calculateWeight(s, h), nodes.get(nI * m + nJ));
                        }

                        //RB neighbour
                        nI = i + 1;
                        nJ = j + 1;
                        h = heightMatrix[nI][nJ] - currHeight;
                        if (Math.abs(h) / s <= maxTan) {
                            currNode.addEdge(calculateWeight(s, h), nodes.get(nI * m + nJ));
                        }

                        //RU neighbour
                        nI = i - 1;
                        nJ = j + 1;
                        h = heightMatrix[nI][nJ] - currHeight;
                        if (Math.abs(h) / s <= maxTan) {
                            currNode.addEdge(calculateWeight(s, h), nodes.get(nI * m + nJ));
                        }
                    }
                    case 1 -> {
                        s = yNet;
                        //B neighbour
                        nI = i + 1;
                        nJ = j;
                        h = heightMatrix[nI][nJ] - currHeight;
                        if (Math.abs(h) / s <= maxTan) {
                            currNode.addEdge(calculateWeight(s, h), nodes.get(nI * m + nJ));
                        }
                        s = xNet;
                        //R neighbour
                        nI = i;
                        nJ = j + 1;
                        h = heightMatrix[nI][nJ] - currHeight;
                        if (Math.abs(h) / s <= maxTan) {
                            currNode.addEdge(calculateWeight(s, h), nodes.get(nI * m + nJ));
                        }
                        s = diagonalS;
                        //RB neighbour
                        nI = i + 1;
                        nJ = j + 1;
                        h = heightMatrix[nI][nJ] - currHeight;
                        if (Math.abs(h) / s <= maxTan) {
                            currNode.addEdge(calculateWeight(s, h), nodes.get(nI * m + nJ));
                        }
                    }
                    case 2 -> {
                        s = yNet;
                        //U neighbour
                        nI = i - 1;
                        nJ = j;
                        h = heightMatrix[nI][nJ] - currHeight;
                        if (Math.abs(h) / s <= maxTan) {
                            currNode.addEdge(calculateWeight(s, h), nodes.get(nI * m + nJ));
                        }
                        s = xNet;
                        //R neighbour
                        nI = i;
                        nJ = j + 1;
                        h = heightMatrix[nI][nJ] - currHeight;
                        if (Math.abs(h) / s <= maxTan) {
                            currNode.addEdge(calculateWeight(s, h), nodes.get(nI * m + nJ));
                        }
                        s = diagonalS;
                        //RU neighbour
                        nI = i - 1;
                        nJ = j + 1;
                        h = heightMatrix[nI][nJ] - currHeight;
                        if (Math.abs(h) / s <= maxTan) {
                            currNode.addEdge(calculateWeight(s, h), nodes.get(nI * m + nJ));
                        }
                    }
                    case 3 -> {
                        s = yNet;
                        //U neighbour
                        nI = i - 1;
                        nJ = j;
                        h = heightMatrix[nI][nJ] - currHeight;
                        if (Math.abs(h) / s <= maxTan) {
                            currNode.addEdge(calculateWeight(s, h), nodes.get(nI * m + nJ));
                        }
                        s = xNet;
                        //L neighbour
                        nI = i;
                        nJ = j - 1;
                        h = heightMatrix[nI][nJ] - currHeight;
                        if (Math.abs(h) / s <= maxTan) {
                            currNode.addEdge(calculateWeight(s, h), nodes.get(nI * m + nJ));
                        }
                        s = diagonalS;
                        //LU neighbour
                        nI = i - 1;
                        nJ = j - 1;
                        h = heightMatrix[nI][nJ] - currHeight;
                        if (Math.abs(h) / s <= maxTan) {
                            currNode.addEdge(calculateWeight(s, h), nodes.get(nI * m + nJ));
                        }
                    }
                    case 4 -> {
                        s = yNet;
                        //B neighbour
                        nI = i + 1;
                        nJ = j;
                        h = heightMatrix[nI][nJ] - currHeight;
                        if (Math.abs(h) / s <= maxTan) {
                            currNode.addEdge(calculateWeight(s, h), nodes.get(nI * m + nJ));
                        }
                        s = xNet;
                        //L neighbour
                        nI = i;
                        nJ = j - 1;
                        h = heightMatrix[nI][nJ] - currHeight;
                        if (Math.abs(h) / s <= maxTan) {
                            currNode.addEdge(calculateWeight(s, h), nodes.get(nI * m + nJ));
                        }
                        s = diagonalS;
                        //LB neighbour
                        nI = i + 1;
                        nJ = j - 1;
                        h = heightMatrix[nI][nJ] - currHeight;
                        if (Math.abs(h) / s <= maxTan) {
                            currNode.addEdge(calculateWeight(s, h), nodes.get(nI * m + nJ));
                        }
                    }
                    case 5 -> {
                        s = yNet;
                        //U neighbour
                        nI = i - 1;
                        nJ = j;
                        h = heightMatrix[nI][nJ] - currHeight;
                        if (Math.abs(h) / s <= maxTan) {
                            currNode.addEdge(calculateWeight(s, h), nodes.get(nI * m + nJ));
                        }

                        //B neighbour
                        nI = i + 1;
                        h = heightMatrix[nI][nJ] - currHeight;
                        if (Math.abs(h) / s <= maxTan) {
                            currNode.addEdge(calculateWeight(s, h), nodes.get(nI * m + nJ));
                        }
                        s = xNet;
                        //R neighbour
                        nI = i;
                        nJ = j + 1;
                        h = heightMatrix[nI][nJ] - currHeight;
                        if (Math.abs(h) / s <= maxTan) {
                            currNode.addEdge(calculateWeight(s, h), nodes.get(nI * m + nJ));
                        }
                        s = diagonalS;
                        //RB neighbour
                        nI = i + 1;
                        nJ = j + 1;
                        h = heightMatrix[nI][nJ] - currHeight;
                        if (Math.abs(h) / s <= maxTan) {
                            currNode.addEdge(calculateWeight(s, h), nodes.get(nI * m + nJ));
                        }

                        //RU neighbour
                        nI = i - 1;
                        nJ = j + 1;
                        h = heightMatrix[nI][nJ] - currHeight;
                        if (Math.abs(h) / s <= maxTan) {
                            currNode.addEdge(calculateWeight(s, h), nodes.get(nI * m + nJ));
                        }
                    }
                    case 6 -> {
                        s = yNet;
                        //U neighbour
                        nI = i - 1;
                        nJ = j;
                        h = heightMatrix[nI][nJ] - currHeight;
                        if (Math.abs(h) / s <= maxTan) {
                            currNode.addEdge(calculateWeight(s, h), nodes.get(nI * m + nJ));
                        }
                        s = xNet;
                        //L neighbour
                        nI = i;
                        nJ = j - 1;
                        h = heightMatrix[nI][nJ] - currHeight;
                        if (Math.abs(h) / s <= maxTan) {
                            currNode.addEdge(calculateWeight(s, h), nodes.get(nI * m + nJ));
                        }

                        //R neighbour
                        nJ = j + 1;
                        h = heightMatrix[nI][nJ] - currHeight;
                        if (Math.abs(h) / s <= maxTan) {
                            currNode.addEdge(calculateWeight(s, h), nodes.get(nI * m + nJ));
                        }
                        s = diagonalS;
                        //LU neighbour
                        nI = i - 1;
                        nJ = j - 1;
                        h = heightMatrix[nI][nJ] - currHeight;
                        if (Math.abs(h) / s <= maxTan) {
                            currNode.addEdge(calculateWeight(s, h), nodes.get(nI * m + nJ));
                        }

                        //RU neighbour
                        nI = i - 1;
                        nJ = j + 1;
                        h = heightMatrix[nI][nJ] - currHeight;
                        if (Math.abs(h) / s <= maxTan) {
                            currNode.addEdge(calculateWeight(s, h), nodes.get(nI * m + nJ));
                        }
                    }
                    case 7 -> {
                        s = yNet;
                        //U neighbour
                        nI = i - 1;
                        nJ = j;
                        h = heightMatrix[nI][nJ] - currHeight;
                        if (Math.abs(h) / s <= maxTan) {
                            currNode.addEdge(calculateWeight(s, h), nodes.get(nI * m + nJ));
                        }

                        //B neighbour
                        nI = i + 1;
                        h = heightMatrix[nI][nJ] - currHeight;
                        if (Math.abs(h) / s <= maxTan) {
                            currNode.addEdge(calculateWeight(s, h), nodes.get(nI * m + nJ));
                        }
                        s = xNet;
                        //L neighbour
                        nI = i;
                        nJ = j - 1;
                        h = heightMatrix[nI][nJ] - currHeight;
                        if (Math.abs(h) / s <= maxTan) {
                            currNode.addEdge(calculateWeight(s, h), nodes.get(nI * m + nJ));
                        }
                        s = diagonalS;
                        //LU neighbour
                        nI = i - 1;
                        nJ = j - 1;
                        h = heightMatrix[nI][nJ] - currHeight;
                        if (Math.abs(h) / s <= maxTan) {
                            currNode.addEdge(calculateWeight(s, h), nodes.get(nI * m + nJ));
                        }

                        //LB neighbour
                        nI = i + 1;
                        nJ = j - 1;
                        h = heightMatrix[nI][nJ] - currHeight;
                        if (Math.abs(h) / s <= maxTan) {
                            currNode.addEdge(calculateWeight(s, h), nodes.get(nI * m + nJ));
                        }
                    }
                    case 8 -> {
                        s = yNet;
                        //B neighbour
                        nI = i + 1;
                        nJ = j;
                        h = heightMatrix[nI][nJ] - currHeight;
                        if (Math.abs(h) / s <= maxTan) {
                            currNode.addEdge(calculateWeight(s, h), nodes.get(nI * m + nJ));
                        }
                        s = xNet;
                        //L neighbour
                        nI = i;
                        nJ = j - 1;
                        h = heightMatrix[nI][nJ] - currHeight;
                        if (Math.abs(h) / s <= maxTan) {
                            currNode.addEdge(calculateWeight(s, h), nodes.get(nI * m + nJ));
                        }

                        //R neighbour
                        nJ = j + 1;
                        h = heightMatrix[nI][nJ] - currHeight;
                        if (Math.abs(h) / s <= maxTan) {
                            currNode.addEdge(calculateWeight(s, h), nodes.get(nI * m + nJ));
                        }
                        s = diagonalS;
                        //LB neighbour
                        nI = i + 1;
                        nJ = j - 1;
                        h = heightMatrix[nI][nJ] - currHeight;
                        if (Math.abs(h) / s <= maxTan) {
                            currNode.addEdge(calculateWeight(s, h), nodes.get(nI * m + nJ));
                        }

                        //RB neighbour
                        nI = i + 1;
                        nJ = j + 1;
                        h = heightMatrix[nI][nJ] - currHeight;
                        if (Math.abs(h) / s <= maxTan) {
                            currNode.addEdge(calculateWeight(s, h), nodes.get(nI * m + nJ));
                        }
                    }
                }
            }
        }



        return true;
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

    private double calculateWeight(double s, double h) {
        return h + Math.sqrt(s * s + h * h);
    }

    public void setHeightMatrix(double[][] heightMatrix){
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                this.heightMatrix[i][j] = heightMatrix[i][j];
            }
        }
    }

    public ArrayList<Pair<Integer, Integer>> getCoordinateListByPath(ArrayList<Integer> path) {

        if(nodes == null) {
            return null;
        }

        ArrayList<Pair<Integer, Integer>> coordinateList = new ArrayList<>(path.size());
        for (Integer id: path) {
            coordinateList.add(
                    new Pair<>(
                            nodes.get(id).getCoordinates()
                    )
            );
        }

        return  coordinateList;
    }

    public void flushHeightMatrix() {
        heightMatrix = null;
    }



}
