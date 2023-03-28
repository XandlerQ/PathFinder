package com.pthfndr;

import java.lang.String;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;


public class API extends HttpServlet {

    MainController controller = new MainController();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        Gson gson = new Gson();
        String requestData = request.getReader().lines().collect(Collectors.joining());
        PojoHeightMatrix requestMatrix = new PojoHeightMatrix();
        requestMatrix = gson.fromJson(requestData, (Type)(requestMatrix));

        Matrix matrix = new Matrix(requestMatrix.getN(), requestMatrix.getM());
        matrix.setVal(requestMatrix.getMx());

        controller.setHeigthMatrix(matrix);
        controller.setMinTan(requestMatrix.getMinTan());
        controller.setMaxTan(requestMatrix.getMaxTan());
        controller.setDimensions(requestMatrix.getX(), requestMatrix.getY());
        controller.setImageSize(requestMatrix.getN(), requestMatrix.getM());
        controller.buildGraph(requestMatrix.getxH(), requestMatrix.getyH(), requestMatrix.getxT(), requestMatrix.getyT());
        controller.initSolve();
        PojoSolution solution = controller.getSolution();

        String responseJsonString = gson.toJson(solution);

        response.getOutputStream().println(responseJsonString);
    }
    public static void main(String[] args) throws IOException {

    }
}