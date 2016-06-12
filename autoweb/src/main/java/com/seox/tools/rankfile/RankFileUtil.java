/*
 * Created on Nov 13, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.seox.tools.rankfile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.seox.tools.scan.ScanResultLink;
import com.seox.util.KeywordUtil;

public class RankFileUtil {

    private static Logger m_logger = Logger.getLogger(RankFileUtil.class);
    private static String m_dir = "c:\\rankfiles";
    private static String m_currentVer = "1";

    private static Map m_fileAdapter = new HashMap();

    static {
        m_fileAdapter.put("1", new RankAdapterVerOne());
    }

    public static List getHistoricalFileList(String kw, boolean weekly, int engine) {
        
        File file =new File(m_dir);
        
        if (!file.exists()) {
            return new ArrayList();
        }
        
        if (!file.isDirectory()) {
            return new ArrayList();
        }

        String timeToken = (weekly? "weekly":"daily");
        
        String all[] = file.list();
        
        List ret = new ArrayList();
        for (int i = 0; i < all.length;i++){
            File f = new File(all[i]);
            
            if (! all[i].startsWith("scan") ) continue;
            if (! all[i].endsWith(".txt")) continue;
            
            if (all[i].indexOf(timeToken) <0 ) continue;
            
            if (all[i].indexOf("-" + KeywordUtil.addUnderscore(kw, true) + ".txt") <0 ) continue;
            
            ret.add(all[i]);
            
            m_logger.debug("Ret file " + all[i]);
        }
        return ret;
    }
    
    public static boolean saveToFile(String kw, int scanCalendarNum, boolean weekly, List ranks) {

        RankFileAdater adapter = (RankFileAdater) m_fileAdapter.get(m_currentVer);
        if (adapter != null) {
            try {
                return adapter.saveToFile(kw, scanCalendarNum, weekly, ranks);
            }
            catch (Exception e) {
                m_logger.error(e);
            }
        }

        String resultsFile = getFileName(kw, scanCalendarNum, "google", weekly);
        System.out.println("### results from google in file " + resultsFile);

        File file = new File(resultsFile);

        if (file.exists()) {
            File backupFile = new File(resultsFile + ".bak");
            if (backupFile.exists()) {
                backupFile.delete();
            }
            file.renameTo(backupFile);
        }

        PrintWriter out = null;
        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter(resultsFile)));

            out.println("keyword:" + kw);
            out.println("scan-datetime:" + new Date());
            out.println("num-results:" + ranks.size());
            out.println("weeknum:" + scanCalendarNum);

            boolean printedTotal = false;
            for (Iterator iter = ranks.iterator(); iter.hasNext();) {
                ScanResultLink link = (ScanResultLink) iter.next();
                m_logger.debug("(" + link.getRank() + ")" + link.getLinkString());

                if (!printedTotal) {
                    out.println("totalreturn:" + link.getTotalReturn()); // total
                                                                            // number
                                                                            // of
                                                                            // returns
                    printedTotal = true;
                }
                out.println("rank-" + link.getRank() + ":" + link.getLinkString());
            }
            out.flush();
        }
        catch (IOException e) {
            m_logger.error(e);
            return false;
        }
        finally {
            try {
                out.close();
            }
            catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
    
    public static List loadLinksFromFile(String kw, int scanCalendarNum, String engine, boolean weekly, int maxLoad)
            throws Exception {

        String fileName = getFileName(kw, scanCalendarNum, engine, weekly);
        m_logger.debug("FILE=" + fileName);
        BufferedReader in = null;
        List ret = new ArrayList();

        File file = new File(fileName);
        if (!file.exists()) {
            m_logger.warn("File not found " + fileName);
            throw new FileNotFoundException("File " + fileName + " not found. maybe not scanned yet.");
        }

        String version = getVersionFromFile(fileName);

        RankFileAdater adapter = null;
        if ( version!= null) {
            adapter = (RankFileAdater) m_fileAdapter.get(version);
        }
        
        if (adapter == null ) {
            adapter = (RankFileAdater) m_fileAdapter.get(m_currentVer);
        }
        
        if (adapter != null) {
            try {
                return adapter.loadRanksFromFile(kw, scanCalendarNum, engine, weekly, maxLoad);
            }
            catch (Exception e) {
                m_logger.error(e);
                return new ArrayList();
            }
        } else {
            m_logger.error("Adapter " + version + " not found" );
            return new ArrayList();
        }
    }

    public static String getFileName(String kw, int scanCalendarNum, String engine, boolean weekly) {

        String resultsFile = "scan-weekly-" + engine + "-" + scanCalendarNum + "-"
                + KeywordUtil.addUnderscore(kw, true) + ".txt";
        if (!weekly) {
            resultsFile = "scan-daily-" + engine + "-" + scanCalendarNum + "-" + KeywordUtil.addUnderscore(kw, true)
                    + ".txt";
        }
        return m_dir + File.separator + resultsFile;
    }

    public static String getVersionFromFile(String fileName) {

        File file = new File(fileName);
        BufferedReader in = null;
        
        try {
            in = new BufferedReader(new FileReader(fileName));
            while (true) {
                String line = in.readLine();
                if (line == null)
                    break;

                if (line.startsWith("#"))
                    continue;
                if (line.startsWith("//"))
                    continue;

                int pos = line.indexOf(":");
                if (pos < 0) {
                    continue;
                }

                String name = line.substring(0, pos);
                if (name.endsWith("file-ver")) {
                    return name;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                in.close();
            }
            catch (IOException e) {
            }
        }
        return null;
    }

    public static void main(String args[]) throws Exception {
//        loadLinksFromFile("new york", 1923, "google", true);
        getHistoricalFileList("new york", true, 1);
    }
}
