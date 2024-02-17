package com.personal.demo.methods;

public class StringHashcode {
    public static String convertToStringHash(String str){
        return Integer.toString(str.hashCode());
    }
}
