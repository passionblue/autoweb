package com.seox.util;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class Alerter
{
    private static Logger m_logger = Logger.getLogger(Alerter.class);
    
    
    public static final int ALERT_UNKNOWN   = 0;
    public static final int ALERT_INFO      = 1;
    public static final int ALERT_WARNING   = 2;
    public static final int ALERT_CRITICAL  = 3;
    public static final int ALERT_DETAIL    = 4; // For Maintainer only
    
    
    public static Map alertWorkersMap = new java.util.Hashtable();
    
    public static synchronized void sendDetail(String detailId, String subject,  String message)
    {
        sendAlert(detailId, Alerter.ALERT_DETAIL, subject, message);
    }
    
    public static synchronized void sendAlert(String alertId, Object alertObj)
    {
        sendAlert(alertId, Alerter.ALERT_INFO, "", alertObj);
    }
    public static synchronized void sendWarning(String alertId, Object alertObj)
    {
        sendAlert(alertId, Alerter.ALERT_WARNING, "", alertObj);
    }
    public static synchronized void sendCritical(String alertId, Object alertObj)
    {
        sendAlert(alertId, Alerter.ALERT_CRITICAL, "", alertObj);
    }
    
    public static synchronized void sendAlert(String alertId, String subject,  Object alertObj)
    {
        sendAlert(alertId, Alerter.ALERT_INFO, subject, alertObj);
    }
    public static synchronized void sendWarning(String alertId, String subject,  Object alertObj)
    {
        sendAlert(alertId, Alerter.ALERT_WARNING, subject, alertObj);
    }
    public static synchronized void sendCritical(String alertId, String subject,  Object alertObj)
    {
        sendAlert(alertId, Alerter.ALERT_CRITICAL, subject, alertObj);
    }
    
    public static synchronized void sendAlert(String alertId, int level, String subject,  Object alertObj)
    {
        sendAlert(alertId, level, subject, alertObj, false, 0, 0, false, true);
    }
    
    public static synchronized void sendAlert(String alertId, int level, String subject,  Object alertObj, boolean recursive, long duration, long interval)
    {
        sendAlert(alertId, level, subject, alertObj, recursive, duration, interval, false, false);
    }
    
    public static synchronized void sendAlert(String alertId, int level, String subject,  Object alertObj, boolean recursive, long duration, long interval, boolean reset, boolean forceDeliver)
    {
        String prop ="true"; //LocateProxyProperties.getInstance().getProperty("locateproxy.alert.send." + alertId, null);
        String groupForThis = null;//LocateProxyProperties.getInstance().getProperty("locateproxy.alert.group." + alertId, null);
        
        if ( prop != null )
        {
            if ( prop.equalsIgnoreCase("false"))
            {
                return;
            }
        }

        String groupId = getLevel(level);
        if ( groupForThis != null)
        {
            groupId = groupForThis;
        }

        if ( level == Alerter.ALERT_DETAIL ||  forceDeliver )
        {
            AlertWorker saved = (AlertWorker) alertWorkersMap.get(alertId);
            if ( saved == null )
            {
                saved = new AlertWorker(alertId, groupId, getLevel(level) + " : " + subject);
                alertWorkersMap.put(alertId, saved);
            }

            saved.push(alertObj);
            saved.doIt(false);

        }
        else
        {
            AlertWorker saved = (AlertWorker) alertWorkersMap.get(alertId);
            if ( saved == null )
            {
                saved = new AlertWorker(alertId, getLevel(level), getLevel(level) + " : " + subject, alertObj.toString(), recursive, duration, interval);
                alertWorkersMap.put(alertId, saved);
            }

            saved.doIt(reset);
        }
    }
    
    public static String getLevel(int level )
    {
        switch(level)
        {
        case Alerter.ALERT_UNKNOWN: return "UNKNOWN";
        case Alerter.ALERT_INFO: return "INFO";
        case Alerter.ALERT_WARNING: return "WARNING";
        case Alerter.ALERT_CRITICAL: return "CRITICAL";
        case Alerter.ALERT_DETAIL: return "DETAIL";
        }
        return "";
    }
    
    public static void main(String args[])
    {
        Alerter.sendAlert("AlertTestId", Alerter.ALERT_INFO, "TEST", "AlertTest", true, 6000, 2000);
    }
}

class AlertWorker extends Thread
{
    static Logger m_logger = Logger.getLogger(AlertWorker.class);
    
    String  m_alertId;
    String  m_subject;
    Object  m_alertObject;
    String  m_alertGroup;
    boolean m_recursive;
    long    m_duration;
    long    m_interval;
    
    boolean m_running;

    long    m_start;
    
    private Object m_waiting = new Object();
    
    private List m_alertsQueue = new LinkedList(); 
    
    AlertWorker ( String alertId, String alertGroup, String subject, Object alertObj, boolean recursive, long duration, long interval )
    {
        super("ALERT THREAD (RECURSIVE) " + alertId);
        m_alertId = alertId;
        m_subject = subject;
        m_alertObject = alertObj;
        m_recursive = recursive;
        m_duration = duration;
        m_interval = interval;
        m_alertGroup = alertGroup;
        m_logger.debug("Alert Thread (RECURSIVE) created for " + alertId);
    }

    AlertWorker ( String alertId, String alertGroup, String subject)
    {
        super("ALERT THREAD (QUEUE) " + alertId);
        m_subject = subject;
        m_alertId = alertId;
        m_alertGroup = alertGroup;
        
        m_logger.debug("Alert Thread created for " + alertId);
    }
    
    
    public void push(Object alertObject)
    {
        if ( ! m_recursive )
        {
            synchronized(m_alertsQueue)
            {
                m_alertsQueue.add(alertObject);
            }
        }
        else
        {
            if ( alertObject != null)
            {
                m_alertObject =  alertObject;
            }
        }
    }
    
    public void doIt(boolean reset)
    {
        if (m_running )
        {
            synchronized(m_waiting)
            {
                m_waiting.notifyAll();
            }
        }
        else
        {
            m_running = true;
            start();
        }
    }
    
    public void run()
    {
        m_start = System.currentTimeMillis();
        
        while(true)
        {
            if ( m_alertsQueue.size() == 0 )
            {
                synchronized(m_waiting)
                {
                    try {
                        m_waiting.wait(5000);
                    } catch (InterruptedException e) {

                    }
                }
            }
            
            while(true)
            {
                try
                {
                    Object sendingObject = null;
                    
                    if ( m_recursive )
                    {
                        sendingObject = ( m_alertObject != null ? m_alertObject : "") ;
                    }
                    else
                    {
                        synchronized( m_alertsQueue )
                        {
                            if ( m_alertsQueue.size() > 0 )
                            {
                                sendingObject = m_alertsQueue.remove(0);
                            }
                            else
                            {
                                break;
                            }
                        }
                    }
                    
                    
                    String prop = "true";//$$$LocateProxyProperties.getInstance().getProperty("i aproxy.alert.send." + m_alertId, null);
                    String groupForThis = null;//$$$LocateProxyProperties.getInstance().getProperty("locateproxy.alert.group." + m_alertId, null);
                    
                    if ( prop != null )
                    {
                        if ( prop.equalsIgnoreCase("false"))
                        {
                            continue;
                        }
                    }

                    String groupId = m_alertGroup;
                    if ( groupForThis != null)
                    {
                        groupId = groupForThis;
                    }
                    
                    Mail.sendMail(sendingObject.toString(), m_subject, groupId);
                    m_logger.info("Alert Sent at " + new Date() + " alert=" + m_alertId + " alertObj=" + sendingObject);
                }
                catch(Exception e)
                {
                    m_logger.error("ALERT FAILED. alert = " + m_alertObject);
                    break;
                }
                
                long current = System.currentTimeMillis();
                
                if ( m_recursive )
                {
                    if ( current > m_start + m_duration) break;
                    try{ Thread.sleep(m_interval);}catch(Exception e){}
                }
                else
                {
                    synchronized( m_alertsQueue )
                    {
                        if ( m_alertsQueue.size() == 0 ) 
                        {
                            break;
                        }
                    }
                }
            }
        }
    }
}