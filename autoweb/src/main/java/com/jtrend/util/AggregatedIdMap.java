package com.jtrend.util;

import java.util.concurrent.ConcurrentHashMap;

import redis.clients.jedis.ZParams.Aggregate;

/*
 *  This map is used in DS classes to ignore SiteID mapping. This will aggregate all regardless of sites.
 *  @see ThemeAggregatorDS
 *   
 *   
 */

public class AggregatedIdMap extends ConcurrentHashMap<Long, Object>{

    private Long m_aggregatedKey = new Long(0);
    
    public AggregatedIdMap(){
        this(new Long(0));
    }    
    public AggregatedIdMap(Long key){
        m_aggregatedKey = key;
    }
    
    @Override
    public Object get(Object key) {
        // TODO Auto-generated method stub
        return super.get(m_aggregatedKey);
    }

    public boolean containsKey(Object key){
        return super.containsKey(m_aggregatedKey);
    }
    
    @Override
    public Object put(Long key, Object value) {
        // TODO Auto-generated method stub
        return super.put(m_aggregatedKey, value);
    }

    @Override
    public Object remove(Object key) {
        return super.remove(m_aggregatedKey);
    }
}
