package com.seox.tools;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.seox.db.Keyword;
import com.seox.db.KeywordDAO;
import com.seox.db.Rank;
import com.seox.db.RankDAO;
import com.seox.tools.rankfile.RankFileUtil;
import com.seox.tools.scan.ScanResultLink;

// Has 
public class RankTableMgr {

    private static int MAX_RANKS_LOADS = 100;
    
    KeywordDAO m_keywordDao;
    RankDAO m_rankDao;
    private Keyword m_keyword;
    private Map m_tables;
    private int m_searchEngine;
    private boolean m_weekly;
    private int m_ageLimit;
    private int m_timeNum;
    private RankTable m_latest;
    
    private boolean m_historicalDataLoaded;
    
    private static Logger m_logger = Logger.getLogger(RankTableMgr.class);
    
    public RankTableMgr(Keyword keyword, int searchEngine, boolean weekly, int ageLimit) {
    
        m_keyword = keyword;
        m_keywordDao = new KeywordDAO();
        m_rankDao = new RankDAO();
        m_tables = new TreeMap();
        m_searchEngine = searchEngine;
        m_weekly = weekly;
        m_ageLimit = ageLimit;

        if (m_weekly) 
            m_timeNum = CalendarUtil.getWeekNum();
        else
            m_timeNum = CalendarUtil.getDayNum();
    }

    // Needed to run on DBfree mode. Runs on data loaded from keyword files only

    public void loadHistoricalFiles() {
        List scanfiles = RankFileUtil.getHistoricalFileList(m_keyword.getKeyword(), m_weekly, 1);
        
    }
    
    public void loadDB() {
        
        m_logger.info("Loading ranks table for keyword [" + m_keyword.getKeyword() + "] " + m_keyword.getKeywordId());

        Keyword k = m_keywordDao.findById(new Long(m_keyword.getKeywordId()));
        long start = System.currentTimeMillis();
        // TODO This will be time consuming later when we add more ranks over time. Should be optimized
        Set set = k.getRanks();
        long end = System.currentTimeMillis();
        
        m_logger.debug("Number of ranks loaded " + set.size() + " time=" + (end-start));
        
        List orphanedRanks = new ArrayList(); // save ranks that could not be entered into table.
        List malformedRanks = new ArrayList();
        List tooOldRanks = new ArrayList();
        for (Iterator iter = set.iterator(); iter.hasNext();) {
            
            Rank rank = (Rank) iter.next();
            // filter out TODO 
            // 1. should match serch engine
            
            // get table 
            RankTable table = null;
            int rankTimeNum = m_weekly? rank.getWeekNum(): rank.getDayNum();
            
            if (rankTimeNum <= 0 ) {
                malformedRanks.add(rank);
                continue;
            }

            if (m_ageLimit > 0 && m_timeNum - rankTimeNum > m_ageLimit) {
                tooOldRanks.add(rank);
                continue;
            }
            
            Integer tableKey = new Integer(rankTimeNum);
            
            if ( m_tables.containsKey(tableKey)) {
                table = (RankTable) m_tables.get(tableKey);
                table.addRank(rank);
            } else {
                if (rank.getRank() == 0 ) { // table title rank
                    int sizeOfTable = Integer.parseInt(rank.getDomain());
                    table = new RankTable(k.getKeyword(), sizeOfTable, rankTimeNum);
                    m_logger.debug("Added rank table for week " + rankTimeNum);
                    m_tables.put(tableKey, table);
                } else {
                    // index rank has not been found yet. put this aside for a while.
                    orphanedRanks.add(rank);
                }
            }
        }

        //Re-try with orphaned.
        m_logger.info("There are orphaned ranks " + orphanedRanks.size() + " will be put into tables.");
        for(Iterator iter=orphanedRanks.iterator();iter.hasNext();) {
            Rank rank = (Rank) iter.next();

            RankTable table = null;
            int rankTimeNum = m_weekly? rank.getWeekNum(): rank.getDayNum();
            Integer tableKey = new Integer(rankTimeNum);

            if ( m_tables.containsKey(tableKey)) {
                table = (RankTable) m_tables.get(tableKey);
                table.addRank(rank);
            } else {
                m_logger.warn("Could not find index rank for " + rankTimeNum + " rank " + rank.getRank());
            }
        }
        
        // Carry over previous ranks to next table
        RankTable prevTable = null;
        for (Iterator iter = m_tables.values().iterator(); iter.hasNext();) {

            RankTable table = (RankTable) iter.next();
            m_latest = table;
            if (prevTable == null) {
                prevTable = table;
                continue;
            }
            
            m_logger.debug("arrying ranks to the next table " + table.getTimeNum());
            table.carryOver(prevTable);
            prevTable = table;
        }
        
        m_historicalDataLoaded = true;
    }
    
    public void loadAndStore() throws Exception {
        loadAndStore(true);
    }
    public void loadAndStore(boolean persist) throws Exception {
        loadAndStore(persist, true);
    }
    public void loadAndStore(boolean persist, boolean loadNewRanksFile) throws Exception {

        if ( !m_historicalDataLoaded) 
            loadDB();
        
        int compNum = 0;
        if ( m_latest != null ) compNum = m_latest.getTimeNum();
        if ( compNum >= m_timeNum ) {
            m_logger.warn("Aborting loading rank file for the current. Last db time indicates " + m_timeNum + " was already processed");
            return;
        }
        
        m_logger.info("Loading result links from file for " + m_timeNum);
        
        RankTable latestTable =null;
        
        for (Iterator iter = m_tables.values().iterator(); iter.hasNext();) {
            latestTable = (RankTable) iter.next();
        }
        
        if (latestTable != null && latestTable.getTimeNum() >= m_timeNum ) {
            m_logger.info("Results file for " + m_timeNum + " must have been processed. found on db. aborted");
            return; 
        }
        
        List list = null;
        try {
            list = RankFileUtil.loadLinksFromFile(m_keyword.getKeyword(), m_timeNum, "google", m_weekly, MAX_RANKS_LOADS);
        }
        catch (FileNotFoundException fe) {
            fe.printStackTrace();
            return;
        }
        
        if ( list.size() ==   0 ) {
            m_logger.warn("Ranks not loaded from the file. aborting.");
            return;
        }
        
        RankTable newRanktable = new RankTable(m_keyword.getKeyword(), list.size(), m_timeNum, m_weekly);

        for (Iterator iter = list.iterator(); iter.hasNext();) {
            
            ScanResultLink resLink = (ScanResultLink) iter.next();
            
            Rank rankObj = new Rank();
            rankObj.setKeyword(m_keyword);
            rankObj.setRank(resLink.getRank());
            rankObj.setDomain(resLink.getDomain());
            rankObj.setLink(resLink.getLinkString());
            rankObj.setSearchEngine(m_searchEngine);
            rankObj.setWeekNum(m_timeNum);
            
            newRanktable.addRank(rankObj);
        }
        
        m_tables.put(new Integer(m_timeNum), newRanktable);
        m_latest = newRanktable;
        
        if (!persist) {
            return;
        }
        
        if (latestTable != null  )
            newRanktable.checkUpdates(latestTable);
        else
            newRanktable.markAllUpdates();
        
        List trans = newRanktable.getTransientRanks();

        m_logger.debug("There are " + trans.size() + " transient objs in this file. start saving to db");
        
        // Table title rank that contains how many ranks on this table
        // size is useful when reconstructing from db to memory.
        Rank titleRank = new Rank();
        titleRank.setWeekNum(m_timeNum);
        titleRank.setKeyword(m_keyword); 
        titleRank.setDomain("" + newRanktable.getSize());
        titleRank.setLink("");
        titleRank.setRank(0);

        // Start transaction to save new changes in ranks
        // TODO transaction
        m_rankDao.save(titleRank);
        for (Iterator iter = trans.iterator();iter.hasNext();) {
            Rank r = (Rank) iter.next();
            m_rankDao.save(r);
        }
        // commit
    }
    
    public RankTable getLatestTable() {
        return m_latest;
    }
    
    public List getTables() {
        return new ArrayList(m_tables.values());
    }
    
    // Map contains domain to List of RankEntry
    public Map getHistoryByDomain(String domain, boolean highestOnly) {

        Map ret  = new TreeMap();
        
        for(Iterator iter=m_tables.values().iterator();iter.hasNext();) {
            
            RankTable table = (RankTable) iter.next();
            
            List ranks = table.getLinksForDomain(domain);
            List retRanks = new ArrayList();
            
            if (highestOnly) {
                if ( ranks.size() > 0)
                    retRanks.add(ranks.get(0));
            } else {
                if ( ranks.size() > 0)
                    retRanks.addAll(ranks);
            }
            m_logger.debug("Domain rank History = " + retRanks);
            ret.put(new Integer(table.getTimeNum()), retRanks);
        }
        
        return ret;
    }
    
    public static void main(String args[]) throws Exception{

        KeywordDAO keywordDao = new KeywordDAO();
        List kewords = keywordDao.findAll();

        Keyword keyword = (Keyword) kewords.get(0);

        RankTableMgr mgr = new RankTableMgr(keyword, 1, true, 26); 
        //mgr.loadDB();
        mgr.loadAndStore(); 

        Map histories = (Map) mgr.getHistoryByDomain("ny.us", true);
        
    }
    
    // This is main
    public static void runThroughDB() throws Exception {
        
        KeywordDAO keywordDao = new KeywordDAO();
        List kewords = keywordDao.findAll();
        
        for ( Iterator iter = kewords.iterator(); iter.hasNext();) {
            Keyword kw = (Keyword) iter.next();
            
            if ( kw.getKeywordId() > 0 ) {
                RankTableMgr mgr = new RankTableMgr(kw, 1, true, 26); 
                //mgr.loadDB();
                mgr.loadAndStore(); 
            }
        }
    }
}
