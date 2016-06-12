package com.jtrend.service; 

public interface DomainStore { 

    void loadFromDB() throws Exception; 
    void reset() throws Exception;
} 