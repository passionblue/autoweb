package com.intv;


public class StringBufferTest {

    
    
    
    public static void main(String[] args) {
        
        System.out.println(numBits(122389573));
        System.out.println(get("joshua", 100));
        System.out.println(get2("joshua", 100));
        System.out.println(get3("joshua", 100));
        System.out.println(get4("joshua", 100));
        
        System.out.println();
        
        long start = System.nanoTime();
        for (int i = 0; i < 10000;i++)
            get("abcdefgdjoshua", 1000);
        System.out.println("1=" + (System.nanoTime() - start));
        
        start = System.nanoTime();
        for (int i = 0; i < 10000;i++)
            get2("abcdefgdjoshua", 1000);
        System.out.println("1=" + (System.nanoTime() - start));

        start = System.nanoTime();
        for (int i = 0; i < 10000;i++)
            get3("abcdefgdjoshua", 1000);
        System.out.println("1=" + (System.nanoTime() - start));

        start = System.nanoTime();
        for (int i = 0; i < 10000;i++)
            get4("abcdefgdjoshua", 1000);
        System.out.println("1=" + (System.nanoTime() - start));
        
        
    }
    
    public static String get(String in, int num){
        StringBuffer b = new StringBuffer();
        for(int i = 0; i<num;i++){
            b.append(in);
        }
        return b.toString();
    }
    
    public static String get2(String in, int num){
        StringBuffer b = new StringBuffer(in.length()*num+10);
        for(int i = 0; i<num;i++){
            b.append(in);
        }
        return b.toString();
    }

    public static String get3(String in, int num){

        char ret[] = new char[in.length()*num];
        char ino[] = in.toCharArray();
        int idx = 0;
        for(int i = 0; i<num;i++){
            for(int j=0; j<ino.length;j++){
                ret[idx] = ino[j];
                idx++;
            }
        }
        
        return String.valueOf(ret);
    }

    public static String get4(String in, int num){
        StringBuilder b = new StringBuilder(in.length()*num+10);
        for(int i = 0; i<num;i++){
            b.append(in);
        }
        return b.toString();
    }
    
    
    public static int numBits(int val){
    
        int ret = 0;
        while(val > 0 ){
            val >>>= 1;;
            if ( (val & 1) == 1 )
                ret++;
        }
        return ret;
    }
    
}
