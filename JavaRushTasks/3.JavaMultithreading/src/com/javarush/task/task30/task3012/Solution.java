package com.javarush.task.task30.task3012;

/* 
Получи заданное число
*/

import java.math.BigInteger;

public class Solution {
    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.createExpression(74);
    }

    public void createExpression(int number) {
        //напишите тут ваш код
        StringBuilder stb = new StringBuilder(number + " =");
        int count = 0;
        do {
            int a = number % 3;
            number = number / 3;
            if(a == 1) stb.append(" + " + (int)Math.pow(3, count));
            else if( a == 2) {
                number += 1;
                stb.append(" - " + (int)Math.pow(3, (count)));
            }
            else if(a == 0) stb.append("");
            count++;

        } while(number > 0);
        System.out.println(stb);

    }
}