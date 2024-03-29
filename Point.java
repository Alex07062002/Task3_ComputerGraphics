package ru.vsu.cs.course1;

public class Point {

    private double x;
    private double y;
    private double z;

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }


    public Point(double x, double y,double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX () {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX (double x) {
        this.x = x;
    }

    public void setY (double y) {
        this.y = y;
    }

    public String toString(){
        return(getX()+"|"+getY()+"|"+getZ());
    }
}
