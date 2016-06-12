package com.autosite.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.autosite.db.Linkto;
import com.autosite.ds.LinktoDS;
import com.jtrend.util.FileUtil;
import com.jtrend.util.WebUtil;


public class LinkToUtil {

    private static Map m_linkToMap = new ConcurrentHashMap(); 
    private static Logger m_logger = Logger.getLogger(LinkToUtil.class);
    
    static{
        LinkToUtil.loadFromFile();
    }
    
    private static void loadFromFile(){

        BufferedReader in = FileUtil.getBufferedReader("/linkto.properties");
        
        m_logger.debug("#########################  Loading LinkTo Properties ##########################################");
        while (true) {

            String line = null;
            try {
                line = in.readLine();
            }
            catch (IOException e) {
                m_logger.error(e.getMessage(),e);
                break;
            }

            if (line == null )
                break;
            
            if (line.trim().startsWith("#") || line.trim().equals("") || line.trim().startsWith("_")) {
                continue;
            }
            
            int pos = line.indexOf("=");
            if (pos < 0) {
                m_logger.warn("Line corrupted = " + line);
                continue;
            }

            String key = line.substring(0, pos);
            String linkTo = line.substring(pos + 1);

            m_linkToMap.put(key, linkTo);
            m_logger.debug("# " + key + " ## " + linkTo);
            
        }        
        m_logger.debug("# loaded props = " + m_linkToMap.size());
        m_logger.debug("###############################################################################################");
        
    }
    
    public static String findLinkTo(String dest){
        
        if (dest  == null ) return null;

        String linkTo = "";

        // Check filebased config first
        if ( m_linkToMap.containsKey(dest)){
            linkTo = (String) m_linkToMap.get(dest);
        }

        // Then check db entry.
        Linkto linkToObj = LinktoDS.getInstance().getObjectByLinkKey(dest);
        if ( linkToObj != null &&  WebUtil.isNotTrue(linkToObj.getDisable())) {
            linkTo = linkToObj.getLinkTarget();
        }
        
        if (linkTo != null && !linkTo.startsWith("http://")){
            linkTo = "http://" + linkTo;
        }
        
        return linkTo;
    }
    
}
