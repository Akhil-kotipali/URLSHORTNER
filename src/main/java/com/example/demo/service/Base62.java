package com.example.demo.service;

public class Base62 {

    private static final String BASE62 =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String encode(long number) {

        StringBuilder result = new StringBuilder();

        while (number > 0) {
            int index = (int) (number % 62);
            result.append(BASE62.charAt(index));
            number = number / 62;
        }

        return result.reverse().toString();
    }
}