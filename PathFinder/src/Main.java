import java.lang.String;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        MainController controller = new MainController();
        controller.loadImage("render.png", 225, 225, 0, 10);
        controller.setDimensions(225, 225);
        controller.setMaxTan(1);
        controller.fillHeightMatrixFromImage();
        controller.buildGraph(5, 20, 211, 220);
        controller.initSolve();
        controller.printSolutionToImage();
    }
}