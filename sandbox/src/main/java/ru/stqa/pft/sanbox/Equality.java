package ru.stqa.pft.sanbox;

public class Equality {

    public static void main(String[] args) {
        String s1 = "string";
        String s2 = new String(s1);

        System.out.println(s1 == s2);
        System.out.println(s1.equals(s2));
    }
}
