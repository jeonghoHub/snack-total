package jpabook.jpashop.controller;

import java.util.Arrays;

public class test {
    public static void main(String[] args) {
        int[] origin =  {5};

        int[] clone1 = Arrays.copyOf(origin, origin.length);

        System.out.println("배열 번지 비교 : "  + origin.equals(clone1));
    }
}