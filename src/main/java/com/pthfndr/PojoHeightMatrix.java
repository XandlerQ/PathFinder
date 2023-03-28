package com.pthfndr;

public class PojoHeightMatrix {

    private int n;
    private int m;
    private double x;
    private double y;
    private double minTan;
    private double maxTan;
    private int xH;
    private int yH;
    private int xT;
    private int yT;


    private double[][] mx;

    PojoHeightMatrix() {
        this.n = 0;
        this.m = 0;
        this.x = 0;
        this.y = 0;
        this.minTan = 0;
        this.maxTan = 0;
        this.mx = null;
    }

    PojoHeightMatrix(int n, int m) {
        this();
        this.n = n;
        this.m = m;
        this.mx = new double[n][m];
    }

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getMinTan() {
        return minTan;
    }

    public double getMaxTan() {
        return maxTan;
    }

    public int getxH() {
        return xH;
    }

    public int getyH() {
        return yH;
    }

    public int getxT() {
        return xT;
    }

    public int getyT() {
        return yT;
    }

    public double[][] getMx() {
        return mx;
    }

    public void setN(int n) {
        this.n = n;
    }

    public void setM(int m) {
        this.m = m;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setMinTan(double minTan) {
        this.minTan = minTan;
    }

    public void setMaxTan(double maxTan) {
        this.maxTan = maxTan;
    }

    public void setxH(int xH) {
        this.xH = xH;
    }

    public void setyH(int yH) {
        this.yH = yH;
    }

    public void setxT(int xT) {
        this.xT = xT;
    }

    public void setyT(int yT) {
        this.yT = yT;
    }

    public void setMx(double[][] mx) {
        this.mx = mx;
    }
}
