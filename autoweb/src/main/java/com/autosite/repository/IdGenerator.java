package com.autosite.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.joda.time.LocalDateTime;

/*
 * class that generates long ID. Spring data sets UUID type of string ID, not the incremental type of ID, which i like to keep...shit
 * IdGenerator.getInstance().nextSequence(className); 
 */


public class IdGenerator {

    private static IdGenerator m_instance = new IdGenerator();

    private Map<String, AtomicLong> seqMap = new ConcurrentHashMap<>();
    private long masterSeed = 0;
    
    public static IdGenerator getInstance() {
        return m_instance;
    }

    private IdGenerator() {

    }
    
    public long nextSequence(Class clazz) {
        return nextSequence(clazz.getSimpleName());
    }    
    
    public long nextSequence(String name) {
        
        if ( !seqMap.containsKey(name)) {
            seqMap.put(name, new AtomicLong(getServerSeed(name)));
        }
        return seqMap.get(name).incrementAndGet();
    }
    
    public long getServerSeed(String name) {

        return Long.parseLong(LocalDateTime.now().toString("yyMMddHHmm")) * 1000000;
    }
}
