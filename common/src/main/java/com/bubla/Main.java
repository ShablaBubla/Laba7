package org.example;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        byte[] b = new byte[4];
        for(byte i = 0;i<4;i++){
            b[i] = i;
        }
        System.out.println(Arrays.toString(Arrays.copyOfRange(b, 0, 4)));
    }
}