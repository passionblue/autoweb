/*
 * Created on Dec 1, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.seox.util;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;
import org.apache.log4j.WriterAppender;

/**
 * This class satisfies needs in my server to put speciall events into separate
 * logs. This logger may not be used for general cases. just use it to save
 * special events into separate log files.
 * 
 * simple method filelog("name", "log"); will save "log" into a file named in
 * "name".log under specified directory.
 * 
 * All historical log contents may be view vy getString()
 * 
 * 
 * 
 * @author jy03190
 * 
 */

public class SeoxLogger {

    private static String m_directory = ".";
    private static Map m_loggerMap = new ConcurrentHashMap();
    private static Map m_writerMap = new ConcurrentHashMap();
    
    private static QueueWriter m_stringWriter = new QueueWriter(1000);

    private static boolean m_enableStringWriter = true;

    private static Logger m_logger = Logger.getLogger(SeoxLogger.class);
    
    public static boolean setLogDirectory(String logDir) {

        m_logger.debug("## Setting directory for SeoxLogger " + logDir);
        if (logDir == null || logDir.equals(m_directory))
            return true;

        m_directory = logDir;
        
        boolean ok = makeSurePath(logDir, false);
        File dir = new File(logDir);
        if (!dir.exists()) {
            return dir.mkdirs();
        }
        m_logger.debug("## Directory has been created " + logDir);
        return true;
    }

    public static String getAllLogs() {
        return m_stringWriter.getBuffer().toString();
    }

    public static String getLogs( String name) {

        if (m_writerMap.containsKey(name)) {
            QueueWriter w = (QueueWriter) m_writerMap.get(name);
            return w.getBuffer().toString();
        }
        return "";
    }
    
    
    public static List getLogsList() {
        return m_stringWriter.getLogList();
    }

    public static List getLogsList(String name) {
        if (m_writerMap.containsKey(name)) {
            QueueWriter w = (QueueWriter) m_writerMap.get(name);
            return w.getLogList();
        }
        return new ArrayList();
    }
    
    public static void initFileLog(String name, String pattern) {
        getLogger(null, name, pattern);
    }
    public static void initFileLog(String logDir, String name, String pattern) {
        getLogger(logDir, name, pattern);
    }    
    public static void filelog(String name, Object log) {
        if ( name == null ) {
            SeoxLogger.getLogger("NULL").debug(log);
            name = "NULL";
        }
        
        Logger logger = SeoxLogger.getLogger(name);
        
        if (logger != null) logger.debug(log);
    }

    /**
     * Caller can use this instead of convienient methods. e.g.
     * SeoxLogger.getLogger("111").debug(i + " test test");
     * 
     * @param name
     * @return
     */
    public static Logger getLogger(String name) {
        return getLogger(name, "%d [%t] %c - %m%n");
    }
    
    public static Logger getLogger(String name, String pattern) {
        return getLogger(null, name, pattern);
    }    
    public static Logger getLogger(String logDir, String name, String pattern) {

        Logger logger = (Logger) m_loggerMap.get(name);
        if (logger == null) {
            PatternLayout layout = new PatternLayout(pattern);
            RollingFileAppender appender = null;
            
            if (logDir == null || logDir.trim().equals("")) {
                logDir = m_directory;
            }
            
            try {
                String filename = logDir + File.separator + name + ".log";
                if (! makeSurePath(filename, true)) {
                    m_logger.warn("Failed to create directory for " + filename);
                }
                appender = new RollingFileAppender(layout, filename, true);
            }
            catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            logger = Logger.getLogger(name);

            appender.setBufferedIO(false);
            appender.setMaxFileSize("10000000");
            logger.addAppender(appender);

            if (m_enableStringWriter) {

                PatternLayout wlayout = new PatternLayout("[%d] %c - %m%n");
                WriterAppender app = new WriterAppender(wlayout, m_stringWriter);
                logger.addAppender(app);
                
                
                QueueWriter writer = new QueueWriter(1000);
                WriterAppender app2 = new WriterAppender(wlayout, writer);
                logger.addAppender(app2);
                m_writerMap.put(name, writer);
                
            }

            logger.setLevel(Level.DEBUG);
            m_loggerMap.put(name, logger);
        }

        return logger;
    }

    private static boolean makeSurePath(String name, boolean file) {
        
        File f = null;
        try {
            f = new File(name);
            if (f.exists()) return true;
        }
        catch (RuntimeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        if (file) {
            String path = f.getParent();
            System.out.println(path);
            File dir = new File(path);
            if (dir.exists()) return true;
            return dir.mkdirs();
        } else {
            return f.mkdirs();
        }
    }
    
    public static void main(String[] args) {

        SeoxLogger.setLogDirectory("./logs");

        System.out.println(System.currentTimeMillis());
        for (int i = 0; i < 10; i++) {
            SeoxLogger.filelog("111/111", "logging contents " + i);
        }
        for (int i = 0; i < 10; i++) {
            SeoxLogger.filelog("222/222", "logging contents " + i);
        }
        System.out.println(System.currentTimeMillis());

        System.out.println(m_stringWriter.getBuffer());
        System.out.println(System.currentTimeMillis());
        System.out.println(SeoxLogger.getLogs("111"));
        System.out.println(SeoxLogger.getLogsList("222"));
    }

}

class QueueWriter extends Writer {

    String buffer[];
    int m_next = 0;
    int m_end = -1;
    int m_totalNum = 0;

    public QueueWriter() {
        this(1000);
    }

    public QueueWriter(int size) {
        buffer = new String[size];
    }

    StringBuffer getBuffer(int numContents) {
        StringBuffer ret = new StringBuffer(buffer.length);
        String temp[] = new String[buffer.length];
        int numCopied = 0;

        if (m_end >= 0) {
            for (int i = m_end; i <= buffer.length - 1; i++) {
                // ret.append(buffer[i]);
                temp[numCopied++] = buffer[i];
            }
        }

        for (int i = 0; i < m_next; i++) {
            // ret.append(buffer[i]);
            temp[numCopied++] = buffer[i];
        }

        int end = numCopied - 1;
        int start = 0;

        if (numCopied > numContents) {
            start = numCopied - numContents;
        }

        for (int i = start; i <= end; i++) {
            ret.append(buffer[i]);
        }

        return ret;
    }

    StringBuffer getBuffer() {

        StringBuffer ret = new StringBuffer(buffer.length);

        int start = m_next - 1;

        if (m_end >= 0) {
            for (int i = m_end; i <= buffer.length - 1; i++) {
                ret.append(buffer[i]);
            }
        }

        for (int i = 0; i < m_next; i++) {
            ret.append(buffer[i]);
        }

        return ret;
    }
    
    List getLogList() {
    
        List ret = new ArrayList();

        int start = m_next - 1;

        if (m_end >= 0) {
            for (int i = m_end; i <= buffer.length - 1; i++) {
                ret.add(buffer[i]);
            }
        }

        for (int i = 0; i < m_next; i++) {
            ret.add(buffer[i]);
        }

        return ret;
        
    }

    public void close() throws IOException {
    }

    public void flush() throws IOException {

    }

    public void write(char[] cbuf, int off, int len) throws IOException {

        String str = new String(cbuf, off, len);
        m_totalNum++;

        buffer[m_next++] = str;
        if (m_end >= 0)
            m_end++;

        if (m_next == buffer.length) {
            m_next = 0;
            m_end = 0;
        }
        if (m_end == buffer.length) {
            m_end = 0;
        }

    }
}
