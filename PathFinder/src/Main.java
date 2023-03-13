import java.lang.String;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        MainController controller = new MainController();
        controller.loadImage("render.png", 214, 254, 0, 10);
        controller.setDimensions(214, 254);
        controller.setMaxTan(1);
        controller.setMinTan(-1);
        controller.fillHeightMatrixFromImage();
        controller.buildGraph(2, 147, 213, 163);
        controller.initSolve();
        controller.printSolutionToImage();
    }
}