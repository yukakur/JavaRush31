package com.javarush.task.task30.task3010;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.regex.Pattern;

/* 
Минимальное допустимое основание системы счисления
*/

public class Solution {
    public static void main(String[] args) {
        //напишите тут ваш код
        String incomeString = args[0];
        BigInteger bi;
        for (int i = 2; i < 37; i++) {
            try {
                bi = new BigInteger(args[0], i);
                System.out.println(i);
                break;
            } catch (Exception e) {
                if(i == 36) System.out.println("incorrect");
            }
        }
    }
}