package com.jtrend.struts.core;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jtrend.alert.AlertReport;
import com.jtrend.util.FileUtil;

abstract public class AbstractFileBasedConfigManager {

    private static Logger m_logger = LoggerFactory.getLogger(AbstractFileBasedConfigManager.class);
    
    protected boolean skipIfNoEqual = true;
    
    protected Map m_configs = new ConcurrentHashMap();
    
    protected void loadFile(String file) throws Exception {
        loadFile(null, file);
    }
    
    protected void loadFile(String namespace, String file) throws Exception {

        BufferedReader in = FileUtil.getBufferedReader(file);
        
            while (true) {

                String line = in.readLine();

                if (stopNow(line))
                    break;
                
                if ( StringUtils.isBlank(line))
                    continue;
                
                try {
                    processLine(line);
                }
                catch (Exception e) {
                    m_logger.error(e.getMessage(),e);
                }

                if (isSkip(line))
                    continue;                
                
                int pos = line.indexOf("=");
                
                String key   = line.substring(0, pos);
                String value = line.substring(pos + 1);
                String parts[] = key.split("\\.");
                
                try {
                    processLine(namespace, key, value);
                }
                catch (Exception e) {
                    m_logger.error(e.getMessage(),e);
                    continue;
                }

                
                try {
                    processLine(namespace, parts, value);
                }
                catch (Exception e) {
                    m_logger.error(e.getMessage(),e);
                    continue;
                }

                m_configs.put(key, value);
        }
    }

    public List getAllKeys(){
        return new ArrayList(m_configs.keySet());
    }
    
    public List getAllValues(){
        return new ArrayList(m_configs.values());
    }
    
    public AbstractFileBasedConfigManager(){
    }    
    public AbstractFileBasedConfigManager(String filename){
        try {
            loadFile(filename);
        }
        catch (Exception e) {
            AlertReport.sendAlert(e);
        }
    }
    
    
    protected boolean stopNow(String line){
        return line == null;
    }

    protected boolean isSkip(String line){

        if (line == null) return true;

        if (line.trim().isEmpty()) {
            return true;
        }
        
        if (line.trim().startsWith("#")) {
            return true;
        }
        
        int pos = line.indexOf("=");
        if (pos < 0) {
            m_logger.warn("Line corrupted = " + line);
            return true;
        }
        
        return false;
    } 
    
    protected void processLine(String line){}; 

    abstract protected void processLine(String namdespace, String key, String val); 
    abstract protected void processLine(String namdespace, String[] keyParts, String val); 
    
}
