package ru.stqa.pft.sanbox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Collections {

    public static void main(String[] args) {
        String[] langs = {"Java", "c#", "python", "php"};

        //обычный способ добавления элементов в список
        List<String> languages = new ArrayList<>();
        languages.add("Java");
        languages.add("C#");

        //способ заполнить список значениями в одну строку без метода add()
        List<String> languages1 = Arrays.asList("Java", "c#", "python", "php");

        //классический цикл с счетчиком для массива
        for (int i = 0; i < langs.length; i++) {
            System.out.println("Язык " + langs[i]);
        }

        //классический цикл с счетчиком для списка
        for (int i = 0; i < languages.size(); i++) {
            System.out.println("Язык " + languages.get(i));
        }

        //специальный цикл для коллекций, работает и для массивов, и для списков
        for (String l : langs) {
            System.out.println("Язык " + l);
        }

        for (String l : languages1) {
            System.out.println("Язык " + l);
        }
    }
}
