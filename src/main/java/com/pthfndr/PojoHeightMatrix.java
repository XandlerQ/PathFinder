package com.pthfndr;
/*
{
        "n": 400,
        "m": 400,
        "x": 10000,
        "y": 10000,
        "minTan": -0.5,
        "maxTan": +0.7,
        "friction": 0.02,
        "wElevation": 20,
        "xH": 20,
        "yH": 20,
        "xT": 380,
        "yT": 380,
        "mx":
        [
        [1,1, ...... (400)],
        [1,2, ......],
        [2,3, ......],
        ... (400)
        ]
}
*/
public class PojoHeightMatrix {

    private int n;
    private int m;
    private double x;
    private double y;
    private double minTan;
    private double maxTan;
    private double friction;
    private double wElevation;
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
        this.friction = 0;
        this.wElevation = 0;
        this.xH = 0;
        this.yH = 0;
        this.xT = 0;
        this.yT = 0;
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

    public double getFriction() {
        return friction;
    }

    public double getwElevation() {
        return wElevation;
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

    public void setFriction(double friction) {
        this.friction = friction;
    }

    public void setwElevation(double wElevation) {
        this.wElevation = wElevation;
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
