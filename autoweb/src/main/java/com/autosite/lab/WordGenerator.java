package com.autosite.lab;

import java.util.Random;

public class WordGenerator {

    private Random m_randomObj = null;   
    
    String m_templateList;
    
    public WordGenerator(){
        this(1);
    }
    
    public WordGenerator(int type){
        m_templateList = getTemplateList(type);
        m_randomObj = new Random(System.nanoTime() + System.currentTimeMillis()%1000000);
        
    }

    public String getRandom(int length){

        StringBuffer buf = new StringBuffer();
        int max = m_templateList.length();

        for(int i = 0; i <length;i++){
            m_randomObj.nextInt(max);
        }

        for(int i = 0; i <length;i++){
            int pos = m_randomObj.nextInt(max);
            buf.append(m_templateList.charAt(pos));
        }

        return buf.toString();
    }
    
    public String getEncoded(int val){
        
        StringBuffer buf = new StringBuffer();
        String valStr = String.valueOf(val);
        for(int i = 0;i < valStr.length();i++){
            char c = valStr.charAt(i);
            int pos = Integer.parseInt(String.valueOf(c));
            buf.append(m_templateList.charAt(pos));
        }
        return buf.toString();
    }
    
    public static String getTemplateList(int template){
        String list = null;
        switch(template){
            case 1 : list = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; break;
            case 2 : list = "abcdefghijklmnopqrstuvwxyz"; break;
            case 3 : list = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ"; break;
            case 4 : list = "abcdefghijklmnopqrstuvwxyz1234567890"; break;
            case 5 : list = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ1234567890"; break;
            case 6 : list = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ1234567890!@#$%^&*()-_=+.,><"; break;
            default : list = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; break;
        }
        
        return list;
    }
   
    public static void main(String[] args){

        String word = new WordGenerator(5).getRandom(30);
        System.out.println(word);
         word = new WordGenerator(1).getEncoded(20091201);
        System.out.println(word);
    }
}