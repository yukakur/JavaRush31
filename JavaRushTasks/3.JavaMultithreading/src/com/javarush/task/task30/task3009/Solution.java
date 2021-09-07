package com.javarush.task.task30.task3009;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

/* 
Палиндром?
*/

public class Solution {
    public static void main(String[] args) {
        System.out.println(getRadix("112"));        //expected output: [3, 27, 13, 15]
        System.out.println(getRadix("123"));        //expected output: [6]
        System.out.println(getRadix("5321"));       //expected output: []
        System.out.println(getRadix("1A"));         //expected output: []
    }
    private static Set<Integer> getRadix(String number) {
        Set<Integer> palindromSet = new HashSet<>();
        try {
            for (int i = 2; i < 37; i++) {
                BigInteger digit = new BigInteger(number + "");
                String digitString = digit.toString(i);
                if(digitString.equals(new StringBuilder(digitString).reverse().toString())) palindromSet.add(i);
                }
        } catch (NumberFormatException e) {
        }
        return palindromSet;
    }
}