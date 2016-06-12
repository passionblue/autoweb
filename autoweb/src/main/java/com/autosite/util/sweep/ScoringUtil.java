package com.autosite.util.sweep;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

public class ScoringUtil {

    
    public static int calculateWinLosePoints(int hitPoint, int missPoint, int guess, int actual){
        if ( guess == actual) return hitPoint;
        return missPoint;
    }

    public static int calculateScorePoints(int hitPoint, int stepDeduction, int guess, int actual){
        
        int diff = actual - guess;
        if (diff < 0 ) diff = diff * -1;

        int ret = hitPoint;
        
        ret = ret - diff*stepDeduction;
        
        if (ret <0) ret = 0;

        return ret;
        
    }
    
    public static int calculateTeamsPoints(int hitPoint, String line, String actual){
        
        if (actual == null || line == null) return 0;
        
        m_logger.debug("Computing points: " + line + ", ACTUTAL=" + actual);
        String teams[] = actual.split(",");
        
        Set teamSet = new HashSet();
        teamSet.addAll(Arrays.asList(teams));
        
        String pairs[] = line.split("\\|");
        
        int points = 0;
        
        for(int i = 0; i < pairs.length; i++){
            String pair[] = pairs[i].split("=");
            
            if (pair.length != 2) continue;
            
            if ( teamSet.contains(pair[1])){
                points += hitPoint;
            }else{
                m_logger.debug("Not advacing team " + pair[1]);
            }
        }
        
        return points;
    }
    
    
    
    public static String pointDisplay(int point){
        if (point >0) return "+" + point;
        else return ""+point;
    }
    
    public static void main(String[] args) {
        System.out.println(calculateTeamsPoints(3 , "q1=FRA|q2=ENG|q3=PAR|q4=ARG|q5=BRA|q6=GER|q7=ITA|q8=ESP|", "ESP,ITA"));
    }
    
    private static Logger m_logger = Logger.getLogger(ScoringUtil.class);
}
