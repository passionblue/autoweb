package com.jtrend.concur;

public interface TaskServicer {

    WorkHandle dropAndWait( ComTask task);
    WorkHandle dropAndWait( ComTask task, long wait);
    
    WorkHandle dropAndGo( ComTask task);
    WorkHandle dropAndGo( ComTask task, TaskObserver observer);

    
    WorkHandle update(WorkHandle handle);
    
    WorkBasket getNow(WorkHandle handle);
    WorkBasket waitNow(WorkHandle handle, long wait);

    void interrup(WorkHandle handle);
    
}
