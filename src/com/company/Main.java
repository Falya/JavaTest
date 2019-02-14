package com.company;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main {

    // Проверка на возможность деления суммы массива без остатка
    static boolean canSplit(int[] array) {
        int sum = 0;

        for (int n : array) sum += n;
        if (sum % 2 == 1) {
            System.out.println("Cannot be divided equally...");
            return false;
        } else {
            return true;
        }
    }

    // Метод деления массива по сумме
    static boolean split(int[] array) {
        int sum = 0;

        for (int n : array) sum += n;
        List<Integer> firstPart = new ArrayList<Integer>();
        List<Integer> secondPart = new ArrayList<Integer>();

        if (!dfs(0, sum / 2, array, firstPart, secondPart)) {
            return false;
        }

        System.out.print("Left weight is " + firstPart.toString());

        int sum1 = 0;
        for (Integer val : firstPart) {
            sum1 += val;
        }
        System.out.println(" = " + sum1 + " ft");

        System.out.print("Right weight is " + secondPart.toString());
        int sum2 = 0;
        for (Integer val : secondPart) {
            sum2 += val;
        }
        System.out.println(" = " + sum2 + " ft");

        int totalSum = sum1 + sum2;

        System.out.println("Summary weight is " + totalSum + " ft");

        return true;
    }

    // Вспомогательная функция для деления массива
    static boolean dfs(int i, int limit, int[] array, List<Integer> firstPart, List<Integer> secondPart) {
        if (limit == 0) {
            for (int j = i; j < array.length; j++) {
                secondPart.add(array[j]);
            }
            return true;
        }
        if (limit < 0 || i == array.length) {

            return false;
        }
        firstPart.add(array[i]);

        if (dfs(i + 1, limit - array[i], array, firstPart, secondPart)) return true;
        firstPart.remove(firstPart.size() - 1);

        secondPart.add(array[i]);

        if (dfs(i + 1, limit, array, firstPart, secondPart)) return true;
        secondPart.remove(secondPart.size() - 1);

        return false;
    }

    // Функция динамического добавления элемента в массив
    static int[] addElement(int[] a, int e) {
        a = Arrays.copyOf(a, a.length + 1);
        a[a.length - 1] = e;
        return a;
    }

    // Логическая функция соответствия условиям
    static boolean isSatisfy(int[] array, int maxSum, int maxWeight, int maxLength) {

        int sum = 0;
        int weight = 0;

        for (int i = 0; i < array.length; i++) {

            sum += array[i];
            if (weight < array[i])
                weight = array[i];
        }

        if (sum <= maxSum && weight <= maxWeight && array.length <= maxLength) {
            return true;
        } else if (sum > maxSum) {
            System.out.println("The sum of the entered array is more than " + maxSum + " ft");
            return false;
        } else if (weight > maxWeight) {
            System.out.println("The weight of the entered element of array is more than " + maxWeight + " ft");
            return false;
        } else if (array.length > maxLength) {
            System.out.println("The length of the entered  array is more than " + maxLength + " elements");
            return false;
        } else {
            return false;
        }

    }

    //Функция для рандомного заполнения массива по условию
    private static int[] randomArray(int minLength, int maxLength, int minWeight, int maxWeight, int maxSum) {

        boolean totalWeightIsOk = false;
        int[] array = {};

        Random random = new Random();

        int difLength = maxLength - minLength;
        int difWeight = maxWeight - minWeight;


        while (!totalWeightIsOk) {
            int a = random.nextInt(difLength + 1);
            a += minLength;

            int[] arrRand;
            arrRand = new int[a];

            int sum = 0;

            for (int i = 0; i < a; i++) {
                arrRand[i] = random.nextInt(difWeight + 1) + minWeight;
                sum += arrRand[i];
            }

            if (sum <= maxSum) {
                array = arrRand;
                totalWeightIsOk = true;
            }
        }

        return array;
    }

    public static void main(String[] args) throws Exception {

        int maxWeight = 20; // Максимальный вес блина
        int maxSum = 10000; // Максимальная сумма набора блинов
        int maxLength = 10; // Максимальное колтчество блинов

        int[] arr;

        arr = randomArray(1, maxLength, 1, maxWeight, maxSum);


        System.out.println("Entered array is " + Arrays.toString(arr));

        if (isSatisfy(arr, maxSum, maxWeight, maxLength)) {
            for (int i = arr.length - 1; i > 0; i--) {
                for (int j = 0; j < i; j++) {
                    if (arr[j] > arr[j + 1]) {

                        int tmp = arr[j];
                        arr[j] = arr[j + 1];
                        arr[j + 1] = tmp;
                    }

                }
            }


            if (canSplit(arr)) {
                if (!split(arr)) {
                    System.out.println("Cannot be used " + Arrays.toString(arr));
                    System.out.println("Summary weight is 0 ft");
                }
            } else {
                int[] newArr = {};
                boolean minOdd = false;
                int unUsed = 0;
                for (int i = 0; i < arr.length; i++) {
                    if (arr[i] % 2 != 0 && !minOdd) {
                        minOdd = true;
                        unUsed = arr[i];
                    } else {
                        newArr = addElement(newArr, arr[i]);
                    }
                }
                if (!split(newArr)) {
                    System.out.println("Cannot be used " + Arrays.toString(arr));
                    System.out.println("Summary weight is 0 ft");
                } else {
                    if (unUsed > 0)
                        System.out.println("Unused weight is " + unUsed + " ft");
                }
            }
        } else {
            return;
        }


    }
}
