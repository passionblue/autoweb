package com.jtrend.struts.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jtrend.alert.AlertReport;
import com.seox.util.PropertiesLoader;

public class DefaultPartitionPageManager extends AbstractFileBasedConfigManager {

    private static Logger m_logger = LoggerFactory.getLogger(DefaultPartitionPageManager.class);
    
    private static DefaultPartitionPageManager m_instance = new DefaultPartitionPageManager();

    public static DefaultPartitionPageManager getInstance() {
        return m_instance;
    }

    private DefaultPartitionPageManager() {
        String file = PropertiesLoader.getInstance().getProperty("app.cfg.partition_page_manager");
        try {
            loadFile(file);
        }
        catch (Exception e) {
            AlertReport.sendAlert(e);
        }
    }
    
    private DefaultPartitionPageManager(String filename) {
        super(filename);
    }

    @Override
    protected void processLine(String namdespace, String key, String val) {
        m_logger.debug("Key-Value : " + key + "=" + val);
    }

    @Override
    protected void processLine(String namdespace, String[] keyParts, String val) {
        m_logger.debug("Key-Value : " + keyParts + "=" + val);
    }

    public String getPartitionPage(String partId){
        return (String) m_configs.get(partId);
    }
    
    
    public static void main(String[] args) {
        DefaultPartitionPageManager.getInstance();
        
        System.out.println(DefaultPartitionPageManager.getInstance().getAllKeys());
    }
    
}
