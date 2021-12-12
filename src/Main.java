/*
Написать программу, которая выводит на экран случайное число от 10 до 100, в виде
строки “Случайное число …. Попробуйте еще раз”, где строка формируется при помощи String.format.
 */

import javax.swing.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println(String.format("Случайное число %s. Попробуйте ещё раз", (int)(Math.random()*91+10)));

        //===================================================

        String minS = JOptionPane.showInputDialog(null, "Введите наименьшее желаемое число",
                "Запрос произвольного числа", JOptionPane.INFORMATION_MESSAGE);
        double min = Integer.parseInt(minS);
        String maxS = JOptionPane.showInputDialog(null, "Введите наибольшее желаемое число",
                "Запрос произвольного числа", JOptionPane.INFORMATION_MESSAGE);
        double max = Long.parseLong(maxS);
        int magnificator = (int) (max - min + 1);
        double timer = (double)(System.currentTimeMillis()/1000);

// Сортировка массива 1000000 случайных целых чисел из диапазона для проверки корректности вывода
        TreeSet<Integer> treesetX = new TreeSet<>();    //Массив для сортировки произвольных значений и чистки одинаковых
        for (int i = 0; treesetX.size() < magnificator; i++) {
            treesetX.add((int) (Math.random() * magnificator + min));
        }
        System.out.println("TreeSetted in " + String.format("%.2f", ((double)System.currentTimeMillis()/1000 - timer)) + " seconds");
        timer = (double)(System.currentTimeMillis())/1000;
        ArrayList<Integer> arraylistX = new ArrayList<>();  //Массив для хранения сортированных значений из TreeSet
        FileWriter writer = new FileWriter("Out.txt", false);   //Писатель для вывода значений в файл .txt
        for (int i : treesetX) {
            arraylistX.add(i);
            try {
                writer.write(i + "\n");
                writer.flush();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        System.out.println("Writed in " + String.format("%.2f", ((double)System.currentTimeMillis()/1000 - timer)) + " seconds");
        timer = (double)(System.currentTimeMillis())/1000;
        treesetX.clear();   //Очищаем память под массив treesetX
        writer.close();     //Закрываем поток писателя, освободив память

        ArrayList<Integer> arread = new ArrayList<>();          //Тестовый массив для проверки чтения файла
        FileReader reader = new FileReader("Out.txt");  //Инициализация читателя из файла Out.txt в папке проекта
        String strread = "";                                    //Переменная для хранения строки из файла
        while (reader.ready()) {                                //Цикл работает, пока остались непрочитанные символы
            char read = (char)reader.read();                    //Чтение идет побайтно, каждый байт переводим в символ
            if (read != '\n') {                                 //Если считанный символ не является переносом строки, то
                strread += read;                                //  добавляем его к уже считанным символам этой строки
            } else {                                            //В противном случае
                arread.add(Integer.parseInt(strread));          //Пишем в тестовый массив преобразованную в целое число строку
                strread = "";                                   //Сбрасываем значение строки
            }
        }
        System.out.println("Readed in " + String.format("%.2f", ((double)System.currentTimeMillis()/1000 - timer)) + " seconds");
        reader.close();                                         //Закрываем поток читателя, освободив память
        arread.clear();                                         //Очищаем тестовый массив
        strread = null;                                         //Очищаем переменную строки в файле
        System.gc();                                            //И вызываем сборщик мусора для очистки от них памяти

        JOptionPane.showMessageDialog(null,"В случайном массиве из " + arraylistX.size() +
                " элементов:\nНаименьшее значение: " + arraylistX.get(0) + "\nНаибольшее значение: " +
                arraylistX.get(arraylistX.size() - 1) + "\n\nВаше произвольное число: " +
                (int)(Math.random() * magnificator + min));
        maxS = null;
        minS = null;
        arraylistX.clear();
        System.gc();
    }
}
