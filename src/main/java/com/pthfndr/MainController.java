package com.pthfndr;

import java.io.IOException;
import java.util.ArrayList;

public class MainController {
    DataConverter converter;
    Solver solver;

    MainController() {
        converter = new DataConverter();
        solver = new Solver();
    }

    public void loadImage(String fName, int width, int height, double minElevation, double maxElevation) throws IOException {
        converter.loadImage(fName, width, height, minElevation, maxElevation);
    }

    public void setDimensions(double X, double Y) {
        converter.setDimensions(X, Y);
    }
    public void setImageSize(int imageHeight, int imageWidth) {
        this.converter.setImageSize(imageHeight, imageWidth);
    }

    public void setMaxTan(double maxTan) {
        converter.setMaxTan(maxTan);
    }
    public void setMinTan(double minTan) {
        converter.setMinTan(minTan);
    }

    public void setMinElevation(double minElevation) {
        this.converter.setMinElevation(minElevation);
    }

    public void setMaxElevation(double maxElevation) {
        this.converter.setMaxElevation(maxElevation);
    }

    public void setHeigthMatrix(Matrix matrix) {
        this.converter.setHeightMatrix(matrix);
    }

    public boolean fillHeightMatrixFromImage() {
        return converter.fillHeightMatrixFromImage();
    }

    public boolean buildGraph(int xH, int yH, int xT, int yT) {
        return converter.buildGraph(xH, yH, xT, yT);
    }

    public boolean initSolve() {
        solver.setHead(converter.getHead());
        solver.setTarget(converter.getTarget());
        boolean result = solver.aStar();
        if (result) {
            solver.fillPath();
            solver.printPathLength();
        }
        return result;
    }

    public void printSolutionToImage() throws IOException {
        converter.printSolutionToImage(solver.getPathIds());
    }

    public PojoSolution getSolution() {
        PojoSolution solution = new PojoSolution();
        double energy = solver.getPathLength();
        ArrayList<Pair<Integer, Integer>> coordList = converter.getGrBuilder().getCoordinateListByPath(solver.getPathIds());
        int size = coordList.size();

        int[][] path = new int[size][2];

        for (int i = 0; i < size; i++) {
            path[i][0] = coordList.get(i).getR();
            path[i][1] = coordList.get(i).getL();
        }

        solution.setSize(size);
        solution.setEnergy(energy);
        solution.setPath(path);

        return solution;
    }

    /*
    {
        "n": 5,
        "m": 5,
        "x": 50,
        "y": 50,
        "minTan": -0.5,
        "maxTan": 0.7,
        "xH": 0,
        "yH": 0,
        "xT": 4,
        "yT": 4,
        "mx":
        [
          [1,1,1,2,2],
          [1,3,4,3,2],
          [1,5,8,5,1],
          [2,3,4,1,1],
          [2,2,1,1,1]
        ]
}
     */


}
