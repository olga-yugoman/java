package ru.stqa.pft.sanbox;

public class MyFirstProgram {
    public static void main(String[] args){
        hello("world");
        hello("user");
        hello("www");

        double a = 5;
        System.out.println("Прощадь квадрата со стороной " + a + " = " + area(a));

        double c = 5;
        double d = 3;
        System.out.println("Прощадь прямоугольника со сторонами " + c + " и " + d + " = " + area(c, d));

    }

	public static void hello(String somebody){
        System.out.println("Hello, " + somebody + "!");
    }

    public static double area(double a){
        return a * a;
    }

    public static double area(double a, double b){
        return a * b;
    }
}
