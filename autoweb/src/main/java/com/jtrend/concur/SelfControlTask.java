package com.jtrend.concur;

public interface SelfControlTask extends ComTask{

    long getTimeLimit();
    
    int getRepeatOnError();
}
