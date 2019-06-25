package ru.stqa.pft.sanbox;

public class MyFirstProgram {
    public static void main(String[] args){
        hello("world");
        hello("user");
        hello("www");

        Square s = new Square(5);
        System.out.println("Прощадь квадрата со стороной " + s.m + " = " + s.area());

        Rectangle r = new Rectangle(3, 4);
        System.out.println("Прощадь прямоугольника со сторонами " + r.a + " и " + r.b + " = " + r.area());

    }

	public static void hello(String somebody){
        System.out.println("Hello, " + somebody + "!");
    }
}
