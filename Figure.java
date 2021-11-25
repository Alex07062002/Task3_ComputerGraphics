package ru.vsu.cs.course1;

import java.util.ArrayList;
import java.util.List;

public class Figure {

    private final List<Point> coordinates;

    public Figure (List<Point> coordinates) {
        this.coordinates = coordinates;
    }

    public List<Point> getCoordinates () {
        return coordinates;
    }

   public Figure rotateMatrix(double[][]matrix){
        if (coordinates.size() == 0 || coordinates.size() == 1) {
            return null;
        } else {
            List<Point> newList = new ArrayList<>();
            for (Point value: coordinates) {
                newList.add(new Point((value.getX()*matrix[0][0]+value.getY()*matrix[0][1]+ value.getZ()*matrix[0][2]),
                        (value.getX()*matrix[1][0]+ value.getY()*matrix[1][1]+value.getZ()*matrix[1][2]),
                        value.getX()*matrix[2][0]+ value.getY()*matrix[2][1]+ value.getZ()*matrix[2][2]));
            }
            return new Figure(newList);
        }
    }

    public Figure translateMatrix(double[][] matrix){
        if (coordinates.size() == 0 || coordinates.size() == 1) {
            return null;
        } else {
            List<Point> newList = new ArrayList<>();
            for (Point value: coordinates) {
                newList.add(new Point((value.getX()+matrix[0][3]), (value.getY()-matrix[1][3]),value.getZ()+matrix[2][3]));
            }
            return new Figure(newList);
        }
    }

    public Figure scaleMatrix(double[][] matrix){
        if (coordinates.size() == 0 || coordinates.size() == 1) {
            return null;
        } else {
            List<Point> newList = new ArrayList<>();
            for (Point value: coordinates) {
                newList.add(new Point((value.getX() * matrix[0][0] + value.getY() * matrix[0][1] + value.getZ() * matrix[0][2]),
                        (value.getX() * matrix[1][0] + value.getY() * matrix[1][1] + value.getZ() * matrix[1][2]),
                        value.getX() * matrix[2][0] + value.getY() * matrix[2][1] + value.getZ() * matrix[2][2]));
            }
            return new Figure(newList);
        }
    }
}
