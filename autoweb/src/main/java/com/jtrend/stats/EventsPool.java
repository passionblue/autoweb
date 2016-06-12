package com.jtrend.stats;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

public class EventsPool {

    protected static EventsPool m_instance = new EventsPool();
    protected List m_newEvents = new ArrayList();

    protected Map m_eventsMap = new ConcurrentHashMap();
    protected Map m_siteToEvents = new ConcurrentHashMap();
    
    
    public static EventsPool getInstance() {
        return m_instance;
    }

    public void notifyEvent(String site, String event) {
        EventEntry e = new EventEntry();
        e.site = site;
        e.event = event;
        synchronized (m_newEvents) {
            m_newEvents.add(e);
            m_newEvents.notifyAll();
        }
    }

    private EventsPool(){
        new ProcessStatThread().run();
    }
    
    public void processEvent(EventEntry event) {
        addToPool(event.site, event, m_eventsMap);
    }

    public List getEventKeysInPool(){
        return getKeysFromPool(m_eventsMap);
    }    

    public List getEvents(String val){
        return getFromPool(val, m_eventsMap);
    }

    private List getFromPool(String key, Map pool){
        
        List list = (List) pool.get(key);

        if (list == null) {
            return new ArrayList();
        }
        synchronized (list) {
            return new ArrayList(list);
        }
    }
    private List getKeysFromPool(Map pool){
        return new ArrayList(pool.keySet());
    }    
    
    
    private void addToPool(String key, EventEntry event, Map pool){
        List list = (List) pool.get(key);
        if (list == null) {
            list = new ArrayList();
            pool.put(key, list);
        }
        list.add(event);
    }
    
    class ProcessStatThread extends Thread {

        public void run() {

            List stats = null;
            m_logger.debug("ProcessStatThread --- Started");

            while (true) {

                stats = null;
                try {
                    synchronized (m_newEvents) {
                        m_newEvents.wait(5000);
                        if (m_newEvents.size() > 0) {
                            stats = new ArrayList(m_newEvents);
                            m_newEvents.clear();
                        }
                    }
                }
                catch (InterruptedException e) {
                }
                if (stats == null)
                    continue;

                for (Iterator iter = stats.iterator(); iter.hasNext();) {
                    EventEntry event = (EventEntry) iter.next();

                    processEvent(event);

                }
            }
        }
    }
    private static Logger m_logger = Logger.getLogger(StatsPool.class);

    
    static class EventEntry{
        String site;
        String event;
    }
}
