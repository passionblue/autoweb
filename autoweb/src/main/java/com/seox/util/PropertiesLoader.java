package com.seox.util;



//import org.apache.log4j.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

public class PropertiesLoader extends Properties
{
    public final static String KEY_PROPERTY_FILE = "app.configuration";
    public final static String DEFAULT_PROPERTY_FILE = "/app.properties";
    public final static String KEY_UPDATE_INTERVAL = "app.properties.update.interval";
    public final static String DEFAULT_PROPERTY_FILE_UPDATE_MONITOR_INTERVAL = "30000";
    private static Object fileLock  = new Object();

    private static Map m_instanceMap = new java.util.HashMap();
    private static Map m_saveLastUpdates = new java.util.HashMap();

    private static Logger m_logger = Logger.getLogger(PropertiesLoader.class);

    /* PROPERTY VALES */

    public PropertiesLoader()
    {
        this(DEFAULT_PROPERTY_FILE);
        
    }
    public PropertiesLoader(String file)
    {
        if ( file == null ) file = DEFAULT_PROPERTY_FILE;
        m_logger.debug("## Loading properties from " + file);
        InputStream ins = null;
        try
        {
            synchronized(fileLock) {
                ins =  PropertiesLoader.class.getResourceAsStream( file );
                if ( ins == null )
                    ins =  PropertiesLoader.class.getResourceAsStream( "/" + file );
            }
            load(ins);
        }
        catch (Exception e)
        {
            //Logger.getLogger(getClass()).warn("Exception while reading propreties from file " + file +" .Default values loaded", e);
            try {
                FileInputStream fin = new FileInputStream(new File(file));
                load(fin);
            }
            catch (Exception e1) {
                m_logger.error("",e1);
            }
        }
        
        try{
                if ( ins != null ) ins.close();
            }catch(Exception e) {}
    }

    public static synchronized PropertiesLoader getInstance()
    {
        String propertyFile = System.getProperty(KEY_PROPERTY_FILE, DEFAULT_PROPERTY_FILE);
        return getInstance(propertyFile);
    }
    public static synchronized PropertiesLoader getInstance(String filename)
    {
        PropertiesLoader ret = (PropertiesLoader)m_instanceMap.get(filename);

        if ( ret == null )
        {
            int    interval = 30000;
            String intervalStr = System.getProperty(KEY_UPDATE_INTERVAL, DEFAULT_PROPERTY_FILE_UPDATE_MONITOR_INTERVAL);

            try{
                interval = Integer.parseInt(intervalStr);
            }catch(Exception e){}

            ret = new PropertiesLoader(filename);
            m_instanceMap.put(filename, ret);
            //addChecker(filename, 500, interval);
        }
        return ret;
    }

    public static synchronized void reloadInstance ( String filename)
    {
        PropertiesLoader newInstance = new PropertiesLoader(filename);
        m_instanceMap.put(filename, newInstance);
    }

    private static void addChecker(final String fileName, long initDelay, long delay)
    {
        final Timer timer = new Timer();
        TimerTask checker = new TimerTask() {

            public void run() {

                long actualLastModifiedTime = 0;
                long lastModifiedSaved = 0;
                File fileToCheck = null;

                try {

                    URL url =  PropertiesLoader.class.getResource(fileName);
                    if (url == null ) {
                        url =  PropertiesLoader.class.getResource(File.pathSeparator + fileName);
                        if (url == null) {
                            Logger.getLogger(PropertiesLoader.class).warn("Could not open file " + fileName + " while chekcing if it is modified. This will terminate automcated timer job");
                            timer.cancel();
                            return;
                        }
                    }

                    fileToCheck = new File(url.getFile());

                    if ( ! fileToCheck.exists() ) {
                        Logger.getLogger(PropertiesLoader.class).warn("Could not open file " + fileToCheck.getAbsolutePath() + " while chekcing if it is modified. This will terminate automcated timer job");
                        timer.cancel();
                        return;
                    }

//                    System.out.println("FILE " + fileToCheck.getAbsolutePath());
                    actualLastModifiedTime     = fileToCheck.lastModified();

                    if ( m_saveLastUpdates.get(fileName) == null )
                    {
                        lastModifiedSaved = actualLastModifiedTime;
                        m_saveLastUpdates.put(fileName, new Long(lastModifiedSaved));
                    }
                    else
                    {
                        lastModifiedSaved =((Long)m_saveLastUpdates.get(fileName) ).longValue();
                    }

                } catch (Exception ex) {
                    Logger.getLogger(PropertiesLoader.class).warn("While checking LocateProxy properties file " + fileName, ex);
                }

                if (lastModifiedSaved != actualLastModifiedTime) {
                    Logger.getLogger(PropertiesLoader.class).info("LocateProxy properties file " + fileName + " has been changed at " + new java.util.Date(actualLastModifiedTime));
                    Logger.getLogger(PropertiesLoader.class).info("Currently loaded properties were read at " + new java.util.Date(lastModifiedSaved) + ". Properties will be reloaded");
                    PropertiesLoader.reloadInstance(fileName);
                    m_saveLastUpdates.put(fileName, new Long(actualLastModifiedTime));
                }
            }
        };

        timer.schedule(checker, initDelay, delay);
        Logger.getLogger(PropertiesLoader.class).info("Installed a file-checker on proeprty file " + fileName);
    }

    public String getProperty(String key)
    {
        String systemValue = System.getProperty(key);
        if ( systemValue != null ) return systemValue;

        return super.getProperty(key);
    }

    public String getProperty(String key, String defaultValue)
    {
        String systemValue = System.getProperty(key);
        if ( systemValue != null ) return systemValue;

        return super.getProperty(key, defaultValue);
    }

    public boolean getPropertyBoolean(String key, boolean defaultValue) {

        if ( key == null ) return defaultValue;

        String retValue = getProperty(key, String.valueOf(defaultValue));

        if (retValue.equals(Boolean.TRUE.toString())) return true;
        return false;
    }

    public boolean getPropertyBoolean(String key) {
        return getPropertyBoolean(key, false);
    }

    public int getPropertyInt(String key, int defaultValue) {

        String str = getProperty(key, "" + defaultValue);
        int  retVal = defaultValue;
        try {
            retVal = Integer.parseInt(str);
        }catch(Exception e){}

        return retVal;
    }

    public int getPropertyInt(String key) {
        return getPropertyInt(key, 0);
    }
    public long getPropertyLong(String key, long defaultValue) {

        String str = getProperty(key, "" + defaultValue);
        long  retVal = defaultValue;
        try {
            retVal = Long.parseLong(str);
        }catch(Exception e){}

        return retVal;
    }

    public long getPropertyLong(String key) {
        return getPropertyLong(key, 0);
    }

    public void printAllProperties()
    {
        Iterator iter = this.keySet().iterator();

        while(iter.hasNext())
        {
            String key = (String)iter.next();
            String value = getProperty(key);
            //m_logger.debug(key + " [" + value + "]");
        }
    }
    
    public static void printProperties(Properties properties) {
        Iterator iter = properties.keySet().iterator();

        while(iter.hasNext())
        {
            String key = (String)iter.next();
            String value = properties.getProperty(key);
            //m_logger.debug(key + ":" + value);
        }
    }

    public static Properties getSubProperties(Properties properties, String root) {
        Properties ret = new Properties();
        String rootStr = root+".";
        Enumeration enumerator = properties.propertyNames();
        
        while(enumerator.hasMoreElements()) {
            String key = (String) enumerator.nextElement();
            
            if (key.startsWith(rootStr)) {
                String newKey = key.substring(rootStr.length());
                String val = properties.getProperty(key);
                
                ret.put(newKey, val);
            }
        }
        
        return ret;
    }
    
    public static void main(String args[])
    {

        PropertiesLoader entprops = new PropertiesLoader();
        entprops.printAllProperties();
    }
}
