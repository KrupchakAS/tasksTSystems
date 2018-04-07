package com.tsystems.javaschool.tasks.subsequence;

import java.util.Iterator;
import java.util.List;

public class Subsequence {

    /**
     * Checks if it is possible to get a sequence which is equal to the first
     * one by removing some elements from the second one.
     *
     * @param x first sequence
     * @param y second sequence
     * @return <code>true</code> if possible, otherwise <code>false</code>
     */
    @SuppressWarnings("rawtypes")
    public boolean find(List x, List y) {
        boolean z = false;
        if (x == null) {
            throw new IllegalArgumentException();
        }
        if (y == null) {
            throw new IllegalArgumentException();
        }
        if (x.size() > 0 && y.size() > 0) {
            if (y.size() >= x.size())
                return checkLists(x, y);
        }
        if (y.size() == x.size()) {
            z = true;
        }
        else if (x.isEmpty()) {
            z = true;
        }
        return z;
    }

    /**
     * Метод проверки совпадений последовательностей
     * @return Возвращаем <code>true<code/> если количество совпадений равно длине последовательности "x", иначе <code>false<code/>
     */
    static boolean checkLists(List x, List y) {
        // Счетчик совпадений
        int coincidence = 0;
        //Создаем итератор для последовательности "y"
        Iterator iteratorY = y.iterator();
        // Пробегаем по последовательности "x"
        for (Object elementX : x) {
            // Для последовательности "y" используем цикл по итератору.
            // Полученное значение не будет присутствовать в списке
            // (для формирования последовательности важен порядок значений,
            // если нашли элемент из "x", предыдущие элементы из "y" проверять не надо),
            // таким образом удается сократить кол-во итераций
            while (iteratorY.hasNext()) {
                // Если значение из последовательности "x" совпадает со значением из "y",
                // увеличиваем счетчик на единицу и прерываем проход цикла
                if (elementX.equals(iteratorY.next())) {
                    coincidence++;
                    break;
                }
            }
        }
        // Если счетчик равен длине последовательности "x",
        // значит из последовательности "y" удастся сформировать последовательность "x"
        return coincidence == x.size();
    }
}