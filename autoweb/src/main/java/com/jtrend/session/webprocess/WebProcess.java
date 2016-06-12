package com.jtrend.session.webprocess;

import java.util.Map;

/*
 * init stage 0 which mean is at pre-stage. 
 * say there are 4 stages. 
 * 
 * 0 -> 1 -> 2-> 3-> 4 -> close. 
 * 
 */


public interface WebProcess {

    boolean isCompleted();
    boolean isStarted();
    boolean isExpired();
    boolean isClosed();
    
    int getTotalStage();
    int getCurrentStage();
    void setCurrentStage(int stage) throws Exception;
    void close();
    void complete();
    boolean hasNextStage(); 
    
    Map getCommonResource();
    Map getStageResource(int stage);
    
    long getCreatedTimestamp();
    long getTimeToExpire();
    
    void addCommonResource(String name, String value);
    String getCommonResource(String name);
   
    String getId();
    String getArg();
    
}
