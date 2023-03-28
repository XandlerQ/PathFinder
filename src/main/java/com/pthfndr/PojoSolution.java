package com.pthfndr;/*
solution JSON format
{
    "energy": 486,
    "size": 20,
    "path":
    [
        [1,1],
        [1,2],
        [2,3],
        ... (20)
    ]
}
*/

public class PojoSolution {
    private double energy;
    private int size;
    private int[][] path;

    PojoSolution() {
        this.energy = 0;
        this.size = 0;
        this.path = null;
    }

    public double getEnergy() { return this.energy; }

    public int getSize() { return this.size; }

    public int[][] getPath() { return this.path; }

    public void setEnergy(double energy) {
        this.energy = energy;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setPath(int[][] path) {
        this.path = path;
    }
}
