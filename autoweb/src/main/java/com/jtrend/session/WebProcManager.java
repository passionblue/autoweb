package com.jtrend.session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.jtrend.session.webprocess.SingleWebProcess;
import com.jtrend.session.webprocess.WebProcess;

public class WebProcManager {

    public static String registerWebProcess(){
        return registerWebProcess(null);
    }   

    public static String registerWebProcess(String arg){
        return registerWebProcess(arg, false);
    }
    
    public static String registerWebProcess(String arg, boolean reprocessible){
    
        SingleWebProcess webProcess = new SingleWebProcess(arg);
        webProcess.setAllowReprocess(reprocessible);
        m_processMap.put(webProcess.getId(), webProcess);
        
        return webProcess.getId();
    }
    
    public static WebProcess getWebProcess(String id){
        if (id == null) return null;
        return (WebProcess) m_processMap.get(id);
    }
    
    protected static Map m_processMap  = new ConcurrentHashMap();
    
    
    public static void main(String[] args) {
        
        String id = WebProcManager.registerWebProcess();
        
        WebProcess wp = WebProcManager.getWebProcess(id);
        
        System.out.println(wp.isClosed());
        System.out.println(wp.isCompleted());
        System.out.println(wp.isExpired());
        
    }
}
