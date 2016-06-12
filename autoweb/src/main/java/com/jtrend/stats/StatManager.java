package com.jtrend.stats;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.autosite.db.RequestHistory;
import com.autosite.db.Site;
import com.autosite.ds.RequestHistoryDS;
import com.autosite.ds.SiteDS;
import com.jtrend.util.TimeNow;
import com.jtrend.util.WebUtil;
import com.seox.util.PropertiesLoader;
import com.seox.util.SeoxLogger;
/*
        "|ID=" + statData.getStatId() + 
        "|RP=" + statData.getRemoteAddress() +  
        "|HR=" + statData.getRequestUrl() +  
        "|HI=" + statData.getRequestUri() +  
        "|QR=" + statData.getRequestUri() +  
        "|LB=" + statData.isLoginBefore() +  
        "|AN=" + statData.getActionName() +  
        "|PA=" + statData.getPageAlias() +  
        "|TS=" + statData.getServerName() +
        "|PR=" + statData.getParamString() +
        "|US=" + statData.getParamString() +
        "|TT=" + statData.getTotalTime() +
        "|PCI=" + statData.getParamString() +
          
*/

public class StatManager {

    protected List m_newStats = new ArrayList();

    protected static StatManager m_instance;

    // Stats

    protected long m_statIdBase = 0;
    protected long m_StatIdIdx = 0;
    protected boolean m_enableRequestHistory;
    protected DateFormat m_dateFormatter;
    protected DateFormat m_dateFormatterForId;
    
    public synchronized static StatManager getInstance() {

        if (m_instance == null) {
            m_instance = new StatManager();
        }
        return m_instance;
    }

    StatManager() {
        new ProcessStatThread().start();

        m_statIdBase = System.currentTimeMillis() / 60000;
        m_statIdBase = m_statIdBase % 1440000; // unique for every minute for
                                                // 1000 days.
        m_statIdBase = m_statIdBase * 1000000;
        m_StatIdIdx = 1;
        System.out.println(m_statIdBase);

          SeoxLogger.initFileLog("stat_main", "%m%n");
          SeoxLogger.initFileLog("stat_header", "%m%n");
        
        
        m_dateFormatter = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS");
        m_dateFormatterForId = new SimpleDateFormat("yyyMMdd-HH:mm:ss:SSS");
        
        m_enableRequestHistory =  WebUtil.isTrue(PropertiesLoader.getInstance().getProperty("app.cfg.enable_request_history"));
        
        _Logger.info("StatManager Initialized " + " enableRequestHistory:" + m_enableRequestHistory);
    }

    public void dropStatData(StatData statData) {
        long statId = m_statIdBase + m_StatIdIdx++;
        statData.setStatId(statId);
        _Logger.info("STAT_ID="+statId);
        synchronized (m_newStats) {
            m_newStats.add(statData);
            m_newStats.notifyAll();
        }
    }

    public void processStatData(StatData statData) {

        checkRobotStat(statData);

        if (statData.isDropped()){
            logDropped(statData);
            return;
        }
        
        if (statData.isBlocked()){
            logBlocked(statData);
            return;
        }
        
        if (statData.isRobot()) {
            logRobotStatNew(statData);
        } else {
            logMainStat(statData);
            logHeaderStat(statData);
            logSiteStat(statData);
            logRemoteStat(statData);
        }

        if (statData.getLoggedinUser() != null)
            logUserStat(statData);
        
        if (statData.isAjax()){
            logAjax(statData);
        }
        
        if (statData.getExcpetion() != null){
            logException(statData);
        }

        if (m_enableRequestHistory && checkIfFilteredIn(statData))
            persistToHistoryTable(statData);
        
        StatsPool.getInstance().dropStatData(statData);
    }

    public String logMainStat(StatData statData) {
        String data = getFormatDate(statData.getStatDate()) +  
        "|ID=" + statData.getStatId() + 
        "|TS=" + statData.getServerName() +  
        "|RP=" + statData.getRemoteAddress() +  
        "|RF=" + normalizeDisplay((String)statData.getHeaderAttributes().get("referer"))+ 
        "|HI=" + normalizeDisplay(statData.getRequestUri()) +  
        "|US=" + normalizeDisplay(statData.getLoggedinUser()) +  
        "|AN=" + normalizeAction(statData.getActionName()) +  
        "|PA=" + normalizeDisplay(statData.getPageAlias()) +  
        "|TT=" + statData.getTotalTime() + 
        "|QR=" + normalizeDisplay(statData.getQueryString());  

        if (statData.getExcpetion() != null) {
            data += "|XX=" + statData.getExcpetion().getMessage();
        }
        else {
            data += "|XX=";
        }

        SeoxLogger.filelog("stat_main", data);
        return data;
    }

    public String logHeaderStat(StatData statData) {

        String statLog = getFormatDate(statData.getStatDate()) + 
        "|ID=" + statData.getStatId() + 
        "|RP=" + statData.getRemoteAddress() +  
        "|HR=" + statData.getRequestUrl() +  
        "|AG=" + statData.getHeaderAttributes().get("user-agent")+ 
        "|RF=" + statData.getHeaderAttributes().get("referer"); 

        SeoxLogger.filelog("stat_header", statLog);
        return statLog;
    }

    public String logSiteStat(StatData statData) {

        SeoxLogger.initFileLog("sites/" + statData.getServerName() , "%m%n");
        String statLog = getFormatDate(statData.getStatDate()) + 
        "|ID=" + statData.getStatId() + 
        "|RP=" + statData.getRemoteAddress() +  
        "|US=" + statData.getLoggedinUser() +  
        "|HR=" + statData.getRequestUri() +
        "|QR=" + statData.getQueryString();  

        SeoxLogger.filelog("sites/" + statData.getServerName() , statLog);
        return statLog;
    }

    public String logRemoteStat(StatData statData) {

        SeoxLogger.initFileLog("remote/" + statData.getRemoteAddress() , "%m%n");
        String statLog = getFormatDate(statData.getStatDate()) + 
        "|ID=" + statData.getStatId() + 
        "|TS=" + statData.getServerName() +  
        "|HR=" + statData.getRequestUri() + 
        "|QR=" + statData.getQueryString() +
        "|US=" + statData.getLoggedinUser() +  
        "|PCI=" + statData.getRpcid();  

        SeoxLogger.filelog("remote/" + statData.getRemoteAddress() , statLog);
        return statLog;
    }

    public String logUserStat(StatData statData) {
        SeoxLogger.initFileLog("users/" + statData.getLoggedinUser() , "%m%n");
        String statLog = getFormatDate(statData.getStatDate()) + 
        "|ID=" + statData.getStatId() + 
        "|RP=" + statData.getRemoteAddress() +  
        "|TS=" + statData.getServerName() +  
        "|HR=" + statData.getRequestUri() + 
        "|QR=" + statData.getQueryString() +
        "|PCI=" + statData.getRpcid();  

        SeoxLogger.filelog("users/" + statData.getLoggedinUser() , statLog);
        return statLog;
    }
    
    public String logDropped(StatData statData) {

        SeoxLogger.initFileLog("dropped" , "%m%n");
        String statLog = getFormatDate(statData.getStatDate()) + 
        "|ID=" + statData.getStatId() +
        "|RP=" + statData.getRemoteAddress() +  
        "|TS=" + statData.getServerName() +  
        "|HH=" + statData.getHeaderHost() +  
        "|DR=" + statData.getDropReason() +  
        "|HR=" + statData.getRequestUrl(); 

        SeoxLogger.filelog("dropped" , statLog);
        return statLog;
    }

    public String logBlocked(StatData statData) {

        SeoxLogger.initFileLog("block" , "%m%n");
        String statLog = getFormatDate(statData.getStatDate()) + 
        "|ID=" + statData.getStatId() +
        "|RP=" + statData.getRemoteAddress() +  
        "|TS=" + statData.getServerName() +  
        "|HH=" + statData.getHeaderHost() +  
        "|BR=" + statData.getBlockReason() +  
        "|HR=" + statData.getRequestUri(); 

        SeoxLogger.filelog("block" , statLog);
        return statLog;
    }
    
    
    
    public String logAjax(StatData statData) {

        SeoxLogger.initFileLog("ajax" , "%m%n");
        String statLog = getFormatDate(statData.getStatDate()) + 
        "|ID=" + statData.getStatId() + 
        "|TS=" + statData.getServerName() +  
        "|HR=" + statData.getRequestUrl() + 
        "|QR=" + statData.getQueryString();  

        SeoxLogger.filelog("ajax" , statLog);
        return statLog;
    }
    
    public String logRobotStatNew(StatData statData) {

        
        String statLog = getFormatDate(statData.getStatDate()) + 
        "|ID=" + statData.getStatId() + 
        "|RP=" + statData.getRemoteAddress() +  
        "|TS=" + statData.getServerName() +  
        "|HI=" + statData.getRequestUri() +   
        "|QR=" + statData.getQueryString();  
        
        // Test 
        SeoxLogger.initFileLog("robots/" + statData.getRobotBrand() , "%m%n");
        SeoxLogger.filelog("robots/" + statData.getRobotBrand(), statLog);
        
        return statLog;
    }    
    
    public String logRobotStat(StatData statData) {

        String userAgent = (String) statData.getHeaderAttributes().get("user-agent");
        
        System.out.println("####### USER-AGENT #### " + userAgent);
        if (userAgent == null) return "";
        
        String statLog = getFormatDate(statData.getStatDate()) + 
        "|ID=" + statData.getStatId() + 
        "|RP=" + statData.getRemoteAddress() +  
        "|TS=" + statData.getServerName() +  
        "|HI=" + statData.getRequestUri() +   
        "|PR=" + statData.getParamString();  
        
        // Test 
        SeoxLogger.initFileLog("robots/all" , "%m%n");
        SeoxLogger.filelog("robots/all", statLog + " ## " + userAgent);
        
        
        if (userAgent.indexOf("Googlebot") >= 0 ) {
            SeoxLogger.initFileLog("robots/google" , "%m%n");

            SeoxLogger.filelog("robots/google", statLog);
            return "";
        }
        
        if (userAgent.indexOf("msnbot") >= 0) {
            SeoxLogger.initFileLog("robots/msn" , "%m%n");
            SeoxLogger.filelog("robots/msn", statLog);
            return "";
        }
        if (userAgent.indexOf("Yahoo! Slurp") >= 0) {
            SeoxLogger.initFileLog("robots/yahoo" , "%m%n");
            SeoxLogger.filelog("robots/yahoo", statLog);
            return "";
        }
        if (userAgent.indexOf("Speedy Spider") >= 0) {
            SeoxLogger.initFileLog("robots/yahoo" , "%m%n");
            SeoxLogger.filelog("robots/yahoo", statLog);
            return "";
        }
        
        if (userAgent.indexOf("Robot") >= 0 || userAgent.indexOf("robot") >= 0) {
            SeoxLogger.initFileLog("robots/misc" , "%m%n");
            SeoxLogger.filelog("robots/misc", userAgent);
            return "";
        }
        
        return "";
    }    

    public String logException(StatData statData) {
        String data = getFormatDate(statData.getStatDate()) +  
        "|ID=" + statData.getStatId() + 
        "|TS=" + statData.getServerName() +  
        "|RP=" + statData.getRemoteAddress() +  
        "|HI=" + normalizeDisplay(statData.getRequestUri()) +  
        "|QR=" + normalizeDisplay(statData.getQueryString()) +  
        "|US=" + normalizeDisplay(statData.getLoggedinUser()) +  
        "|AN=" + normalizeAction(statData.getActionName()) +  
        "|PA=" + normalizeDisplay(statData.getPageAlias()) +  
        "|TT=" + statData.getTotalTime() + 
        "|XM=" + statData.getExcpetion().getMessage(); 
        
        SeoxLogger.filelog("stat_ex", data);
        
        return data;
    }
    
    
    private String getFormatDate(Date date) {
        return m_dateFormatter.format(date);
    }

    private String normalizeDisplay(String str){
        if (str == null) return "";
        return str;
    }
    private String normalizeAction(String str){
        if (str == null) return "";
        int pos = str.lastIndexOf(".");
        
        if ( pos >= 0)
            return str.substring(pos+1);
        else
            return str;
    }

    private void persistToHistoryTable(StatData statData) {
        
        try {
            RequestHistory history = new RequestHistory();
    
            Site siteSource = SiteDS.getInstance().registerSite(statData.getServerName());
            Site site       = SiteDS.getInstance().registerSite(statData.getServerName(), false);
            
            history.setSiteId(site.getId());
            history.setForwardSiteId(siteSource.getId());
            history.setIsDropped(WebUtil.toNumericaBoolean(statData.isDropped()));
            
            history.setIsPageless(WebUtil.toNumericaBoolean(statData.isPageLess()));
            history.setIsLogin(WebUtil.toNumericaBoolean(statData.isLogin()));
            history.setIsAjax(WebUtil.toNumericaBoolean(statData.isAjax()));
            history.setIsRobot(WebUtil.toNumericaBoolean(statData.isRobot()));
            history.setUserAgent( statData.getUserAgent());
            history.setRefer(statData.getReferer());
            history.setRobot(statData.getRobotBrand());
            history.setRemoteIp(statData.getRemoteAddress());
            history.setSiteUrl(site.getSiteUrl());
            history.setUri(statData.getRequestUri());
            if ( statData.getParamString() != null) 
                history.setQuery(statData.getParamString().length() > 1024? statData.getParamString().substring(0, 1024): statData.getParamString());
            history.setRpci(statData.getRpcid());
            history.setSessionId(statData.getSessionId());
            history.setTimeCreated(new TimeNow());
            history.setUserid(statData.getLoggedinUser());
        
            RequestHistoryDS.getInstance().put(history);
        }
        catch (Exception e) {
            _Logger.error(e.getMessage());
        }
    }

    private boolean checkIfFilteredIn(StatData statData){
        
        return true;
    }
    
    
    public void checkRobotStat(StatData statData) {

        String userAgent = (String) statData.getHeaderAttributes().get("user-agent");
        if (userAgent == null) return;

        String capitalized = userAgent.toUpperCase();
        
        if (userAgent.indexOf("Googlebot") >= 0||userAgent.indexOf("Mediapartners-Google") >= 0 ) {
            statData.setRobot(true);
            statData.setRobotBrand("Googlebot");
            return;
        }
        
        else if (userAgent.indexOf("msnbot") >= 0) {
            statData.setRobot(true);
            statData.setRobotBrand("msnbot");
            return;
        }
        else if (userAgent.indexOf("Yahoo! Slurp") >= 0 ||userAgent.indexOf("Yahoo-MMCrawler") >= 0) {
            statData.setRobot(true);
            statData.setRobotBrand("Yahoo");
            return;
        }
        
        else if (userAgent.indexOf("Ask Jeeves/Teoma") >= 0) {
            statData.setRobot(true);
            statData.setRobotBrand("AskJeeves");
            return;
        }
        else if (userAgent.indexOf("AboutUsBot") >= 0) {
            statData.setRobot(true);
            statData.setRobotBrand("AboutUsBot");
            return;
        }
        else if (userAgent.indexOf("Yeti") >= 0 ||userAgent.indexOf("NaverBot") >= 0) {
            statData.setRobot(true);
            statData.setRobotBrand("NaverBot");
            return;
        }

        else if (userAgent.indexOf("Baiduspider") >= 0) {
            statData.setRobot(true);
            statData.setRobotBrand("Baiduspider");
            return;
        }
        else if (userAgent.indexOf("MJ12bot") >= 0) {
            statData.setRobot(true);
            statData.setRobotBrand("MJ12bot");
            return;
        }

        else if (userAgent.indexOf("Baiduspider") >= 0) {
            statData.setRobot(true);
            statData.setRobotBrand("Baiduspider");
            return;
        }

        else if (userAgent.indexOf("Speedy Spider") >= 0) {
            statData.setRobot(true);
            statData.setRobotBrand("SpeedySpider");
            return;
        }
        else if (userAgent.indexOf("seoprofiler.com") >= 0) {
            statData.setRobot(true);
            statData.setRobotBrand("seoprofiler");
            return;
        }
        else if (userAgent.indexOf("swish-e") >= 0) {
            statData.setRobot(true);
            statData.setRobotBrand("swish-e");
            return;
        }
        else if (userAgent.indexOf("CJNetwork") >= 0) {
            statData.setRobot(true);
            statData.setRobotBrand("cj");
            return;
        }
        else if (userAgent.indexOf("Yandex") >= 0) {
            statData.setRobot(true);
            statData.setRobotBrand("Yandex");
            return;
        }
        else if (userAgent.indexOf("thunderstone") >= 0) {
            statData.setRobot(true);
            statData.setRobotBrand("thunderstone.com");
            return;
        }
        else if (userAgent.indexOf("Netcraft") >= 0) {
            statData.setRobot(true);
            statData.setRobotBrand("Netcraft");
            return;
        }
        else if (userAgent.indexOf("FunWebProducts") >= 0) {
            statData.setRobot(true);
            statData.setRobotBrand("FunWebProducts");
            return;
        }
        
        else if (userAgent.indexOf("bingbot") >= 0) {
            statData.setRobot(true);
            statData.setRobotBrand("BingBot");
            return;
        }
        
        else if (userAgent.indexOf("linkdex") >= 0) {
            statData.setRobot(true);
            statData.setRobotBrand("LinkDex");
            return;
        }
        
        
        else if (capitalized.indexOf("ROBOT") >= 0 ||
            capitalized.indexOf("BOT") >= 0 ||
            capitalized.indexOf("CRAWL") >= 0 ||
            capitalized.indexOf("SPIDER") >= 0 ||
            capitalized.indexOf("robot") >= 0) {
            statData.setRobot(true);
            statData.setRobotBrand("UNKNOWN");
            return;
        }
        
        return;
    }    
    
    
    //#####################################################################################
    // Thread
    //#####################################################################################
    
    class ProcessStatThread extends Thread {

        public void run() {

            List stats = null;
            _Logger.debug("ProcessStatThread --- Started");

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

    public static void main(String[] args) {
        StatManager sm = StatManager.getInstance();
        try {
            Thread.sleep(1000);
        }
        catch (Exception e) {
        }
        StatData statData = new StatData();
        sm.dropStatData(statData);

        try {
            Thread.sleep(7000);
        }
        catch (Exception e) {
        }
        sm.dropStatData(statData);
        sm.dropStatData(statData);
    }

    private static Logger _Logger = Logger.getLogger(StatManager.class);

}
