package com.pthfndr;

public class Matrix {

    int n, m;
    double[][] val;

    Matrix() {
        this.n = 0;
        this.m = 0;
        this.val = null;
    }

    Matrix(int n, int m) {
        this.n = n;
        this.m = m;
        this.val = new double[n][m];
    }

        public int getN() { return this.n; }
    public int getM() { return this.m; }
    public double getVal(int i, int j) {
        return this.val[i][j];
    }

    public void setVal(double val, int i, int j) {
        this.val[i][j] = val;
    }



}
