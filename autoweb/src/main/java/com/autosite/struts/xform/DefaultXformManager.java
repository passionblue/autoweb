package com.autosite.struts.xform;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autosite.struts.xform.impl.DefaultErrorXform;
import com.jtrend.util.FileUtil;
import com.seox.util.PropertiesLoader;

public class DefaultXformManager implements XformManager {
    private static Logger m_logger = LoggerFactory.getLogger(DefaultXformManager.class);

    private static DefaultXformManager m_instance = new DefaultXformManager();

    private Map<String, XformConfig> m_xformConfigs = new ConcurrentHashMap<String, XformConfig>();

    public static DefaultXformManager getInstance() {
        return m_instance;
    }

    private DefaultXformManager() {

        m_logger.debug("======= Initializing DefaultXformManager ==============================================");
        InputStream ins = null;
        String _fileName = PropertiesLoader.getInstance().getProperty("app.cfg.xformconfig");
        m_logger.debug(">>> " + _fileName);

        try {
            ins = PropertiesLoader.class.getResourceAsStream(_fileName);
            if (ins == null)
                ins = PropertiesLoader.class.getResourceAsStream("/" + _fileName);

            if (ins == null) {
                m_logger.error("## Could not load the file. file=" + _fileName, new Exception());
                return;
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(ins));
            while (true) {

                String line = in.readLine();

                if (line == null)
                    break;

                int pos = line.indexOf("=");

                if (pos < 0) {
                    m_logger.warn("Line corrupted = " + line);
                    continue;
                }

                if (line.trim().startsWith("#")) {
                    continue;
                }

                m_logger.debug(">>> " + line);
                
                String key = line.substring(0, pos);
                String value = line.substring(pos + 1);

                if (key.equalsIgnoreCase("class"))
                    continue;

                if (value == null || value.trim().isEmpty())
                    continue;

                String parts[] = key.split("\\."); // xform rqid "." key
                XformConfig xconfig = getXformConfig(parts[0]);

                if ( parts[1].equalsIgnoreCase("template")) {
                    try {
                        xconfig.m_templateLoad  = loadXformTemplate(value);
                        xconfig.m_template      = value;
                        m_logger.debug("Loaded template " + xconfig.m_templateLoad);
                    }
                    catch (Exception e) {
                        m_logger.error(e.getMessage(), e);
                        continue;
                    }
                } else if (parts[1].equalsIgnoreCase("class")) {

                    AutositeXform xform = (AutositeXform)Class.forName(value).newInstance();
                    xconfig.m_xformObject = xform;
                    
                }
            }
        }
        catch (Exception e) {
            m_logger.warn("Exception while reading propreties from file " + _fileName + " .Default values loaded", e);
        }
        finally {
            try {
                if (ins != null)
                    ins.close();
            }
            catch (Exception e) {
            }
        }
    }
    public AutositeXform getXform(String rqid) {
        return getXform(rqid, null);
    }
    public AutositeXform getXform(String rqid, String customTemplate) {
        
        if (m_xformConfigs.containsKey(rqid)){
            
            XformConfig config = m_xformConfigs.get(rqid);
            m_logger.debug("Found config " + rqid + " " + config.m_templateLoad);
            
            //TODO dont have to apply all the time. 
            if ( customTemplate != null )
                config.m_xformObject.applyTemplate(customTemplate);
            else    
                config.m_xformObject.applyTemplate(config.m_templateLoad);

            return config.m_xformObject;
        } else {
            return new DefaultErrorXform();
        }
    }

    private XformConfig getXformConfig(String rqid) {

        if (m_xformConfigs.containsKey(rqid))
            return m_xformConfigs.get(rqid);

        XformConfig c = new XformConfig();

        m_xformConfigs.put(rqid, c);

        return c;
    }

    private String loadXformTemplate(String template) throws Exception {
        m_logger.debug("Loading Xform template " + template);
        return FileUtil.loadFileToString(template);
    }

    static class XformConfig {

        private String m_template;
        private String m_templateLoad;
        private AutositeXform m_xformObject;
    }

    public static void main(String[] args) {
        DefaultXformManager.getInstance();
    }
}
