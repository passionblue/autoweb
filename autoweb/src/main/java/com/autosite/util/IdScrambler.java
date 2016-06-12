package com.autosite.util;

public class IdScrambler {

    private final static long mask = 4357829356L;
    
    public static long scramble(long id){
        return mask + id*13;
    }
    
    public static long descramble(String val){
        if ( val == null) return 0;
        
        long raw = 0;
        try {
            raw = Long.parseLong(val);
            raw = (raw-mask)/13;
        }
        catch (NumberFormatException e) {
        }
        return raw;
    }
    
    public static void main(String[] args) {
        
        System.out.println(scramble(0));
        System.out.println(scramble(12345));
        System.out.println(scramble(123));
        System.out.println(scramble(9999999));
        
        System.out.println(descramble(""+4357829356L));
        System.out.println(descramble(""+4357989841L));
        System.out.println(descramble(""+4357830955L));
        System.out.println(descramble(""+4487829343L));
        
    }
}
