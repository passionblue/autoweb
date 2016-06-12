package com.jtrend.concur;

public interface WorkHandle {

    public static final int STATUS_PENDING = 1;
    public static final int STATUS_IN_PROGRESS = 2;
    public static final int STATUS_COMPLETED = 3;
    public static final int STATUS_ERROR = 4;

    
    
    String getHandleId();
    int    getStatus();
    String getErrorMessage();
}
