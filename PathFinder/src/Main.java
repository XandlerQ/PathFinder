import java.util.*;
import java.lang.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws java.io.FileNotFoundException {
        Solver slv = new Solver();


        GraphBuilder builder = new GraphBuilder(45, 91, 500*45, 500*91);
        builder.setMaxTan(10);

        Scanner sc = new Scanner(new BufferedReader(new FileReader("output_SRTMGL3.txt")));
        int rows = 45;
        int columns = 91;
        double [][] hm = new double[rows][columns];
        while(sc.hasNextLine()) {
            for (int i=0; i<hm.length; i++) {
                String[] line = sc.nextLine().trim().split(" ");
                for (int j=0; j<line.length; j++) {
                    hm[i][j] = Integer.parseInt(line[j]);
                }
            }
        }
        System.out.println(Arrays.deepToString(hm));


        builder.setHeightMatrix(hm);
        builder.buildGraph(0, 0, 44, 90);

        if(slv.aStar(builder.head, builder.target)) {
            slv.fillPath(builder.target);
            slv.printPath();
        }



    }
}