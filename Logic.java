package ru.vsu.cs.course1;

import java.util.ArrayList;
import java.util.List;

public class Logic {

    public static Figure saveFromArrayToFigure (int [][] array) {
        if (array.length == 0) {
            return null;
        } else {
            List<Point> list = new ArrayList<>();
            for (int j = 0; j < array[0].length; j++) {
                list.add(new Point(array[0][j], array[1][j], array[2][j]));
            }
            return new Figure(list);
        }
    }
}