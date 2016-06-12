package com.autosite.util;

public class RandomUtil {

    
    public static int randomInt(int max){
        double ret = Math.random()*max;
        return (int) ret;
    }
    
}
