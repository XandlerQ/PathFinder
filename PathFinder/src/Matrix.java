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

    public void setVal(double val, int i, int j) {
        this.val[i][j] = val;
    }

    public double getVal(int i, int j) {
        return this.val[i][j];
    }

}
