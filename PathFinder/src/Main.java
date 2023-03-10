import java.util.*;
import java.lang.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        DataConverter dataConv = new DataConverter();
        dataConv.loadImage("render.png", 500, 500, 0, 2500);
        dataConv.setDimensions(51037, 51037);
        dataConv.setMaxTan(1.8);
        dataConv.fillHeightMatrixFromImage();
        dataConv.buildGraph(20, 20, 480, 480);

        Solver slv = new Solver();
        slv.aStar(dataConv.getHead(), dataConv.getTarget());

        slv.fillPath(dataConv.getTarget());


        dataConv.printSolution(slv.getPathIds());

    }
}