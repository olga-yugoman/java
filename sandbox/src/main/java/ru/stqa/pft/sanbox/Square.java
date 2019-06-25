package ru.stqa.pft.sanbox;

public class Square {

    public double m;

    public Square(double m) {
        this.m = m;
    }

    public double area(){
        return this.m * this.m;
    }
}
