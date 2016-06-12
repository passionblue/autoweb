package com.jtrend.concur;

public interface TaskObserver {
    
    void onError(WorkBasket basket);
    
    void onComplete(WorkBasket basket);

}
