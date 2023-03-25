import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
public class DataConverter {
    private GraphBuilder grBuilder;
    private Matrix heightMatrix;

    public String fileName;
    private File inputFile;
    private BufferedImage bImage;
    public int imageWidth, imageHeight;
    double minElevation, maxElevation;

    double maxTan;
    double minTan;

    double X, Y;



    DataConverter() {
        this.grBuilder = new GraphBuilder();
        this.heightMatrix = null;
        this.fileName = null;
        this.inputFile = null;
        this.bImage = null;
        this.imageWidth = 0;
        this.imageHeight = 0;
        this.minElevation = 0;
        this.maxElevation = 0;
        this.maxTan = 1;
        this.minTan = 0;
        this.X = 0;
        this.Y = 0;
    }

    public void setDimensions(double X, double Y) { this.X = X; this.Y = Y; }

    public void setMaxTan(double maxTan) {
        this.maxTan = maxTan;
    }
    public void setMinTan(double minTan) {
        this.minTan = minTan;
    }
    public void setHeightMatrix(Matrix heightMatrix) { this.heightMatrix = heightMatrix; }

    public void loadImage(String fName, int width, int height, double minElevation, double maxElevation) throws IOException {
        this.fileName = fName;
        this.imageWidth = width;
        this.imageHeight = height;
        this.minElevation = minElevation;
        this.maxElevation = maxElevation;

        try {
            inputFile = new File(fileName);
            bImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            bImage = ImageIO.read(inputFile);
        }
        catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    public boolean fillHeightMatrixFromImage() {
        if (bImage == null) {
            return false;
        }

        heightMatrix = new Matrix(imageHeight, imageWidth);

        for (int i = 0; i < imageHeight; i++){
            for (int j = 0; j < imageWidth; j++){
                Color pColor = new Color(bImage.getRGB(j,i));
                int red = pColor.getRed();
                double beta = red * 2 / 255.;
                heightMatrix.setVal(beta * (maxElevation - minElevation) + minElevation, i, j);
            }
        }

        return true;
    }

    public boolean buildGraph(int xH, int yH, int xT, int yT) {
        grBuilder.setDimensions(X, Y);
        grBuilder.setSize(imageHeight, imageWidth);
        grBuilder.setMaxTan(maxTan);
        grBuilder.setMinTan(minTan);
        return grBuilder.buildGraph(xH, yH, xT, yT, this.heightMatrix);
    }

    public Node getHead() {
        return grBuilder.head;
    }

    public Node getTarget() {
        return grBuilder.target;
    }

    public void printSolutionToImage(ArrayList<Integer> path) throws IOException {

        ArrayList<Pair<Integer, Integer>> coordinatesList = grBuilder.getCoordinateListByPath(path);

        if (bImage == null) {
            return;
        }

        String solFileName = "Solution.png";

        for(Pair<Integer, Integer> coordinates : coordinatesList) {
            Color red = Color.RED;
            bImage.setRGB(coordinates.getL(), coordinates.getR(), red.getRGB());
        }

        try {
            // Output file path
            File output_file = new File(solFileName);

            // Writing to file taking type and path as
            ImageIO.write(bImage, "png", output_file);

            System.out.println("Writing complete.");
        }
        catch (IOException e) {
            System.out.println("Error: " + e);
        }

    }

}
