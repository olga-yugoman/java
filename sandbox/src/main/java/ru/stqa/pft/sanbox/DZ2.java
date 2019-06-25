package ru.stqa.pft.sanbox;

public class DZ2 {

    public static void main(String[] args) {
        Point p1 = new Point(5 ,0);
        Point p2 = new Point(3,0);
        System.out.println("Расстояние между точкой p1 с координатами " + p1.x + " и " + p1.y + " и точкой p2 с координатами " + p2.x + " и " + p2.y + " = " + p1.distance(p2));
    }
}