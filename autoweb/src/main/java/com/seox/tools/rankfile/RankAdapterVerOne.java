/*
 * Created on Dec 3, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.seox.tools.rankfile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.seox.tools.scan.ScanResultLink;

public class RankAdapterVerOne implements RankFileAdater {

    private static Logger m_logger = Logger.getLogger(RankAdapterVerOne.class);
    
    public String getVersionStr() {
        return "1";
    }

    public List loadRanksFromFile(String kw, int scanCalendarNum, String engine, boolean weekly, int maxLoads) throws Exception {

        String fileName = RankFileUtil.getFileName(kw, scanCalendarNum, engine, weekly);

        BufferedReader in = null;
        List ret = new ArrayList();
        try {
            in = new BufferedReader(new FileReader(fileName));

            String keyword = null;
            String datetime = null;
            int numResults = 0;
            int timeNum = 0;
            int curRank = 0;

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
                    m_logger.warn("Skipping corrupted line " + line);
                    continue;
                }

                String name = line.substring(0, pos);
                String value = line.substring(pos + 1);

                m_logger.debug(name + "->" + value);

                boolean rankStarted = false;
                if (!rankStarted) {
                    if (name.equals("keyword")) {
                        keyword = value;
                        continue;
                    }
                    else if (name.equals("scan-datetime")) {
                        datetime = value;
                        continue;
                    }
                    else if (name.equals("num-results")) {
                        numResults = Integer.parseInt(value);
                        continue;
                    }
                    else if (name.equals("weeknum")) {
                        timeNum = Integer.parseInt(value);
                        continue;
                    }
                }

                if (rankStarted || name.startsWith("rank")) {
                    curRank++;
                    String rankStr = name.substring(5); // fater "rank-"
                    int readRank = Integer.parseInt(rankStr);

                    if (curRank != readRank) {
                        m_logger.warn("####################");
                    }

                    ScanResultLink link = new ScanResultLink();
                    // URL url = new URL(value);

                    link.setKeywordStr(kw);
                    link.setLinkString(value);
                    link.setPage(null);
                    link.setRank(curRank);
                    link.setEngine(1);

                    ret.add(link);
                    m_logger.debug("reading rank " + value);
                    rankStarted = true;
                    
                    if (ret.size() >= maxLoads) 
                        break;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        finally {
            try {
                in.close();
            }
            catch (IOException e) {
            }
        }

        return ret;
    }

    public boolean saveToFile(String kw, int scanCalendarNum, boolean weekly, List ranks) throws Exception {
        
        String resultsFile = RankFileUtil.getFileName(kw, scanCalendarNum, "google", weekly); 
        System.out.println("### results from google in file " + resultsFile);

        File file = new File(resultsFile);
        
        if (file.exists() ) {
            File backupFile = new File(resultsFile + ".bak");
            if (backupFile.exists()){
                backupFile.delete();
            }
            file.renameTo(backupFile);
        }

        PrintWriter out = null;
        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter(resultsFile)));

            out.println("keyword:"+kw);
            out.println("scan-datetime:"+ new Date());
            out.println("num-results:"+ranks.size());
            out.println("weeknum:" + scanCalendarNum);
            out.println("weeknum:" + scanCalendarNum);
            out.println("file-ver:" + getVersionStr());

            boolean printedTotal = false;
            for (Iterator iter = ranks.iterator();iter.hasNext();){
                ScanResultLink link = (ScanResultLink) iter.next();
                m_logger.debug("(" + link.getRank() + ")" + link.getLinkString());
                
                if (!printedTotal) {
                    out.println("totalreturn:" + link.getTotalReturn()); // total number of returns
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
            try {out.close();}
            catch (RuntimeException e) {e.printStackTrace();}
        }
        return true;
    }
}
