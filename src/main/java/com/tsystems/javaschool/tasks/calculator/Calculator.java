package com.tsystems.javaschool.tasks.calculator;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Stack;
import java.util.StringTokenizer;

public class Calculator {

    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */
    public String evaluate(String statement) {
        try {
            statement = replace(statement);
            if (isCorrectStatement(statement)) return calculate(getPostfixNotation(statement));
            else return null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Метод проверки математического выражение
     *
     * @return Возвращаем <code>true<code/> если все хорошо, иначе <code>false<code/>
     * Выражение не должно быть пустым, скобки должны быть расставлены верно, не должно быть лишних символов
     */
    static boolean isCorrectStatement(String statement) {
        return statement.length() != 0 &&
                isCorrectBrackets(statement) &&
                statement.matches("^[\\+\\-\\*\\/\\(\\)\\.\\d]*$");
    }

    /**
     * Метод, проверяющий расстановку скобок
     *
     * @return <code>true<code/> все хорошо, <code>false<code/> скобки расставленны не верно
     */
    static boolean isCorrectBrackets(String statement) {
        byte bracketsCounter = 0;
        for (int i = 0; i < statement.length(); i++) {
            if (statement.charAt(i) == '(') bracketsCounter++;
            else if (statement.charAt(i) == ')') bracketsCounter--;
        }
        return bracketsCounter == 0;
    }

    /**
     * Метод подстановки 0 перед операторами, если с них начинается выражение,
     * или они идут после открывающей строки, а так же удаление пробелов в выражении
     * @return преобразованное математическое выражение
     */
    static String replace(String statement) {
        statement = statement.replaceAll("\\(\\+", "(0+");
        statement = statement.replaceAll("\\(\\-", "(0-");
        statement = statement.replaceAll("^\\+", "0+");
        statement = statement.replaceAll("^\\-", "0-");
        statement = statement.replaceAll(" ", "");
        return statement;
    }

    /**
     * Метод формирования постфиксной записи выражения
     *
     * @return список элементов постфиксоной записи математического выражения
     */
    static ArrayList<String> getPostfixNotation(String statement) {
        ArrayList<String> exampleList = new ArrayList<>();
        Stack<String> subStack = new Stack<>();
        StringTokenizer example = new StringTokenizer(statement, "*,/,+,-,(,)", true);
        while (example.hasMoreElements()) processor((String) example.nextElement(), exampleList, subStack);
        while (!subStack.isEmpty()) exampleList.add(subStack.pop());
        return exampleList;
    }

    /**
     * Метод - процессор позволяющий отсортировать операторы и операнды в требуемом порядке
     *
     * @param el          элемент математического выражения
     * @param exampleList список элементов постфиксоной записи математического выражения
     * @param subStack    вспомогательный стек, для записи операторов
     */
    static void processor(String el, ArrayList<String> exampleList, Stack<String> subStack) {
        switch (el) {
            case "+":
            case "-":
            case "*":
            case "/":
                setEl(el, exampleList, subStack);
                break;
            case "(":
                subStack.push(el);
                break;
            case ")":
                getExpInBrackets(exampleList, subStack);
                break;
            default:
                exampleList.add(el);
        }
    }

    /**
     * Метод определяющий, записывать оператор в стек или в список
     */
    static void setEl(String el, ArrayList<String> exampleList, Stack<String> subStack) {
        while (!subStack.isEmpty() && !subStack.peek().equals("(") && getPriority(subStack.peek()) >= getPriority(el)) {
            exampleList.add(subStack.pop());
        }
        subStack.push(el);
    }

    /**
     * Метод определения приоритета оператора
     *
     * @return приоритет математического оператора
     */
    static byte getPriority(Object el) {
        if (el.equals("+") || el.equals("-")) return 0;
        return 1;
    }

    /**
     * Метод, переносящий оперераторы между скобок из стека в список
     */
    static void getExpInBrackets(ArrayList<String> exampleList, Stack<String> subStack) {
        while (!subStack.isEmpty()) {
            if (subStack.peek().equals("(")) {
                subStack.pop();
                break;
            } else exampleList.add(subStack.pop());
        }
    }

    /**
     * Метод вычисления постфиксной записи выражения
     *
     * @return результат вычисления математического выражения
     */
    static String calculate(ArrayList<String> exampleList) {
        Stack<BigDecimal> calculate = new Stack<>();
        for (String el : exampleList) {
            exampleList.get(0);
            switch (el) {
                case "+":
                case "-":
                case "*":
                case "/":
                    if (!isCompute(el, calculate.pop(), calculate.pop(), calculate)) return null;
                    break;
                default:
                    calculate.push(new BigDecimal(el));
            }
        }
        return Objects.toString(calculate.pop().setScale(4, BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros().toPlainString());
    }

    /**
     * Метод вычисления операции с операндами
     *
     * @param operator  математический оператор
     * @param a         операнд 1
     * @param b         операнд 2
     * @param calculate вспомогательный стек для записи промежуточных значений
     */
    static boolean isCompute(String operator, BigDecimal a, BigDecimal b, Stack<BigDecimal> calculate) {
        switch (operator) {
            // Выполняем оперцию, результат запиываем в стек
            case "+":
                calculate.push(a.add(b));
                break;
            case "-":
                calculate.push(b.subtract(a));
                break;
            case "*":
                calculate.push(a.multiply(b));
                break;
            case "/":
                // Проверяем делитель на ноль
                if (a.compareTo(new BigDecimal("0")) == 0) return false;
                else calculate.push(b.divide(a, new MathContext(20)));
                break;
        }
        return true;
    }
}
