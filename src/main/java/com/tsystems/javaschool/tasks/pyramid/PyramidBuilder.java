package com.tsystems.javaschool.tasks.pyramid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PyramidBuilder {

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */

    public static List<Integer> inputNumbers;           //Поле хранения ссылки на список

    static {
        inputNumbers = new ArrayList<>();               //Статический блок инициализации списка
    }

    public int[][] buildPyramid(List<Integer> inputNumbers) {
        // TODO : Implement your solution here
        for (Integer inputNumber : inputNumbers) {      //Проверка на содержание корректного значения элементов
            if (inputNumber == null) {
                throw new CannotBuildPyramidException();
            }
            if (inputNumbers.size() == Integer.MAX_VALUE - 1) {  //Проверка максимального значения количества эл-ов списка
                throw new CannotBuildPyramidException();
            }
        }
        Collections.sort(inputNumbers);             //Необходимая сортировка элментов списка для будущей пирамиды
        int p = 0;
        int t = 0;
        int b = 1;
        int row = 0;
        for (int r = 1; r <= inputNumbers.size(); r++) {
            t += r;
            b += 2;
            if (t == inputNumbers.size()) {
                row = r;
                break;
            }
        }
        int exp = t;
        int column = b - 2;
        int[][] pyramid = new int[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if ((j > row - 2 - i) && (j < row + i)) {
                    pyramid[i][j] = inputNumbers.get(p);
                    j++;
                    p++;
                }
            }
        }
        if (exp != inputNumbers.size()) throw new CannotBuildPyramidException();//Проверка соответствия размера списка
        return pyramid;
    }
}