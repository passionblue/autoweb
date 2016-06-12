package com.jtrend.stats;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.jtrend.stats.StatManager.ProcessStatThread;

public class StatsPool {
    protected List m_newStats = new ArrayList();
    

    protected List m_allStats = new ArrayList(1000000);
    
    protected Map m_userPool = new ConcurrentHashMap(1000);
    protected Map m_rpciPool = new ConcurrentHashMap(1000);
    protected Map m_robotPool = new ConcurrentHashMap(10);
    protected Map m_sitePool = new ConcurrentHashMap(500);
    protected Map m_remoteAddrPool = new ConcurrentHashMap(10000);
    protected Map m_sessIdPool = new ConcurrentHashMap(10000);
    
    protected Map m_addressToUser = new ConcurrentHashMap();
    protected Map m_addressToSession = new ConcurrentHashMap();
    protected Map m_userToAddress = new ConcurrentHashMap();
    protected Map m_userToSession = new ConcurrentHashMap();
    protected Map m_siteToSession = new ConcurrentHashMap();
    protected Map m_siteToAddress = new ConcurrentHashMap();

    protected static StatsPool m_instance = new StatsPool();

    public  static StatsPool getInstance() {
        return m_instance;
    }
    
    private StatsPool(){
        new ProcessStatThread().start();
    }
    
    public void dropStatData(StatData statData) {
        synchronized (m_newStats) {
            m_newStats.add(statData);
            m_newStats.notifyAll();
        }
    }

    public void processStatData(StatData statData) {
        
        m_logger.debug("adding " + statData.getStatId() + " to pool");
    
        if (statData.getLoggedinUser() != null && !statData.isRobot()) {
            addToPool(statData.getLoggedinUser(), statData, m_userPool);

            if ( statData.getRemoteAddress()!= null )
                addToPool(statData.getLoggedinUser() ,statData.getRemoteAddress(), m_userToAddress, true); 

            if ( statData.getSessionId()!= null )
                addToPool(statData.getLoggedinUser() ,statData.getSessionId(), m_userToSession); 
        
        }
        
        if (statData.getRpcid() != null  && !statData.isRobot()){
            addToPool(statData.getRpcid(), statData, m_rpciPool);
        }
        
        if (statData.getServerName() != null  && !statData.isRobot()){
            addToPool(statData.getServerName(), statData, m_sitePool);
            
            if ( statData.getSessionId()!= null )
                addToPool(statData.getServerName() ,statData.getSessionId(), m_siteToSession); 

            if ( statData.getRemoteAddress()!= null )
                addToPool(statData.getServerName() ,statData.getRemoteAddress(), m_siteToAddress, true); 
        }

        if (statData.getRemoteAddress() != null  && !statData.isRobot()){
            addToPool(statData.getRemoteAddress(), statData, m_remoteAddrPool);
            
            if ( statData.getLoggedinUser()!= null )
                addToPool(statData.getRemoteAddress() ,statData.getLoggedinUser(), m_addressToUser, true); 

            if ( statData.getSessionId()!= null )
                addToPool(statData.getRemoteAddress() ,statData.getSessionId(), m_addressToSession); 
        }

        if (statData.getSessionId() != null){
            addToPool(statData.getSessionId(), statData, m_sessIdPool);
        }

        if (statData.isRobot()){
            addToPool(statData.getRobotBrand(), statData, m_robotPool);
        }
        
    }    
    
    public List getUsersInPool(){
        return getKeysFromPool(m_userPool);
    }    
    public List getSitesInPool(){
        return getKeysFromPool(m_sitePool);
    }    
    public List getRemoteAddrsInPool(){
        return getKeysFromPool(m_remoteAddrPool);
    }    
    public List getRobotsInPool(){
        return getKeysFromPool(m_robotPool);
    }    
    public List getRpciInPool(){
        return getKeysFromPool(m_rpciPool);
    }    
    public List getSessionsInPool(){
        return getKeysFromPool(m_sessIdPool);
    }    
    

    public List getKeysUserToAddr(){
        return getKeysFromPool(m_userToAddress);
    }    

    public List getKeysUserToSession(){
        return getKeysFromPool(m_userToSession);
    }    

    public List getKeysAddrToUser(){
        return getKeysFromPool(m_addressToUser);
    }    

    public List getKeysAddrToSession(){
        return getKeysFromPool(m_addressToSession);
    }    

    public List getKeysSiteToSession(){
        return getKeysFromPool(m_siteToSession);
    }    
    public List getKeysSiteToAddress(){
        return getKeysFromPool(m_siteToAddress);
    }    

    
    public List getAddrToUsers(String val){
        return getFromPool(val, m_addressToUser);
    }
    
    public List getAddrToSession(String val){
        return getFromPool(val, m_addressToSession);
    }

    public List getUserToAddrs(String val){
        return getFromPool(val, m_userToAddress);
    }

    public List getUsertoSession(String val){
        return getFromPool(val, m_userToSession);
    }

    public List getSiteToSessions(String val){
        return getFromPool(val, m_siteToSession);
    }

    public List getSiteToAddresses(String val){
        return getFromPool(val, m_siteToAddress);
    }
    
    public List getUserStats(String user){
        return getFromPool(user, m_userPool);
    }

    public List getSiteStats(String user){
        return getFromPool(user, m_sitePool);
    }

    public List getRemoteAddrStats(String user){
        return getFromPool(user, m_remoteAddrPool);
    }

    public List getRobotStats(String user){
        return getFromPool(user, m_robotPool);
    }

    public List getRpciStats(String user){
        return getFromPool(user, m_rpciPool);
    }

    public List getSessionStats(String sess){
        return getFromPool(sess, m_sessIdPool);
    }
    
    
    private List getFromPool(String key, Map pool){
        
        Collection list = (Collection) pool.get(key);

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

    private void addToPool(String key, Object data, Map pool){
        addToPool(key, data, pool, false);
    }
    
    private void addToPool(String key, Object data, Map pool, boolean uniqueData){
        Collection list = (Collection) pool.get(key);
        if (list == null) {
            list = uniqueData? new HashSet(): new ArrayList();
            pool.put(key, list);
        }
        synchronized (list) {
            list.add(data);
        }
    }
    
    
    class ProcessStatThread extends Thread {

        public void run() {

            List stats = null;
            m_logger.debug("ProcessStatThread --- Started");

            while (true) {

                stats = null;
                try {
                    synchronized (m_newStats) {
                        m_newStats.wait(5000);
                        if (m_newStats.size() > 0) {
                            stats = new ArrayList(m_newStats);
                            m_newStats.clear();
                        }
                    }
                }
                catch (InterruptedException e) {
                }
                if (stats == null)
                    continue;

                for (Iterator iter = stats.iterator(); iter.hasNext();) {
                    StatData statData = (StatData) iter.next();

                    processStatData(statData);

                }
            }
        }
    }
    private static Logger m_logger = Logger.getLogger(StatsPool.class);
}
