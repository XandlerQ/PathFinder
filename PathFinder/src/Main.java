import java.lang.String;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;

public class Main {
    public static void main(String[] args) throws IOException {
        /*
        MainController controller = new MainController();
        controller.loadImage("render.png", 214, 254, 0, 10);
        controller.setDimensions(214, 254);
        controller.setMaxTan(1);
        controller.setMinTan(-1);
        controller.fillHeightMatrixFromImage();
        controller.buildGraph(2, 147, 213, 163);
        controller.initSolve();
        controller.printSolutionToImage();*/

        Gson gson = new Gson();

        Solution sol = new Solution();

        int[][] arr = new int[2][2];

        arr[0][0] = 1;
        arr[0][1] = -1;
        arr[1][0] = 5;
        arr[1][1] = 3;

        sol.setPath(arr);

        System.out.println(gson.toJson(sol));

    }
}