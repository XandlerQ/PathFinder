package com.pthfndr;

import java.lang.String;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.stream.Collectors;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

//API
public class API extends HttpServlet {

    MainController controller = new MainController();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.getOutputStream().println("PathFinder ready...");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        Gson gson = new Gson();
        String requestData = request.getReader().lines().collect(Collectors.joining());
        PojoHeightMatrix requestMatrix = new PojoHeightMatrix();
        requestMatrix = gson.fromJson(requestData, PojoHeightMatrix.class);
        //System.out.println(requestData);

        Matrix matrix = new Matrix(requestMatrix.getN(), requestMatrix.getM());
        matrix.setVal(requestMatrix.getMx());

        controller.setHeigthMatrix(matrix);
        controller.setMinTan(requestMatrix.getMinTan());
        System.out.println(requestMatrix.getMinTan());

        controller.setMaxTan(requestMatrix.getMaxTan());
        System.out.println(requestMatrix.getMaxTan());

        controller.setFriction(requestMatrix.getFriction());
        System.out.println(requestMatrix.getFriction());

        controller.setDimensions(requestMatrix.getX(), requestMatrix.getY());
        controller.setImageSize(requestMatrix.getN(), requestMatrix.getM());
        System.out.println(requestMatrix.getX() + " " + requestMatrix.getY());
        System.out.println(requestMatrix.getN() + " " + requestMatrix.getM());

        controller.buildGraph(requestMatrix.getxH(), requestMatrix.getyH(), requestMatrix.getxT(), requestMatrix.getyT());
        controller.initSolve();
        PojoSolution solution = controller.getSolution();

        String responseJsonString = gson.toJson(solution);
        //System.out.println(responseJsonString);


        response.setHeader("Access-Control-Allow-Origin", "*");

        response.getOutputStream().println(responseJsonString);
    }
    public static void main(String[] args) throws IOException {

    }
}