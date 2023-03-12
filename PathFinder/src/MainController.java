import java.io.IOException;

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

    public void setMaxTan(double maxTan) {
        converter.setMaxTan(maxTan);
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
        }
        return result;
    }

    public void printSolutionToImage() throws IOException {
        converter.printSolutionToImage(solver.getPathIds());
    }


}
