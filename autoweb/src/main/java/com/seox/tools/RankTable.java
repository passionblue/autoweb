package com.seox.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import com.seox.db.Rank;



// Rank is 1 based number outside
// but array is 0 based

// This table should be capable of reconstructing from DB
// 2. compare from previous table and mark entry that needs to be persisted

public class RankTable {
    
    private int m_timeNum;
    private int m_size;
    private boolean m_isWeekly;
    private RankEntry m_entryArray[];
    private String m_keyword;
    
    // Utility maps
    private Map m_domainMap; // Domain map to list of ranks.
    
    private static Logger m_logger = Logger.getLogger(RankTable.class);

    public RankTable(String keyword, int size, int timeNum) {
        this(keyword, size, timeNum, true);
    }
    
    public RankTable(String keyword, int size, int timeNum, boolean weekly) {
        m_size = size;
        m_entryArray = new RankEntry[size];
        m_domainMap = new TreeMap();
        m_timeNum = timeNum;
        m_isWeekly = weekly;
        m_keyword = keyword;
        m_logger.debug("RankTable for " + timeNum + " was craeted. isWeekly = " + m_isWeekly);
    }
    
    public int getTimeNum() {
        return m_timeNum;
    }
    public int getSize() {
        return m_size;
    }
    public String getKeyword(){
        return m_keyword;
    }
    
    public List getDomains() {
        return new ArrayList(m_domainMap.keySet());
    }
    
    public boolean addRank(Rank rankObj) {

        int rank = rankObj.getRank();        
        if (rank > m_size ) {
            // maybe log
            return false;
        }
        
        if (m_entryArray[rank-1] != null) {
            // Should log this situation . avoid this situation happend in any case
            return false;
        }
        
        
        RankEntry rankEntry = new RankEntry(m_timeNum, rankObj.getDomain(), rankObj.getLink(), rankObj);
        m_entryArray[rank-1] = rankEntry;
        enterToUtilMaps(rankEntry);
        return true;
    }

    public Rank getRank(int rank) {
        if (rank > m_size ) return null; 
        RankEntry entry =  m_entryArray[rank-1];
        if (entry == null) return null;
        return entry.getRankObj();
    }
    
    // Used while reconstructure rank table from db
    // fills empty which should be carried from previous. 
    public void carryOver(RankTable table) {
        for ( int i = 0; i< m_size;i++) {
            if (m_entryArray[i] == null) {
                Rank rankObj = table.getRank(i+1);
                addRank(rankObj);
            }
        }
    }
    
    public boolean checkFullness() {
        boolean foundEmpty = false;
        for ( int i = 0; i< m_size;i++) {
            if (m_entryArray[i] == null) {
                foundEmpty = true;
                //log
            }
        }
        if (foundEmpty) return false;
        return true;
    }
    
    // used to id rank entries that are different from ref table
    // marked ones will be persisted by outside
    // in case it is null, save all to db
    public void checkUpdates( RankTable table) {
        if ( table == null ) {
            m_logger.warn("Passed table is null for checkUpdate(). operation aborted");
            return;
        }
        
        for ( int i = 0; i< m_size;i++) {
            RankEntry entry = m_entryArray[i];
            
            Rank rank = table.getRank(i+1);
            RankEntry comp = new RankEntry(m_timeNum, rank.getDomain(), rank.getLink(), rank);
            
            if (!entry.equals(comp)) {
                entry.setNeedUpdate(true);
            }
        }
    }
    
    public void markAllUpdates() {
        for ( int i = 0; i< m_size;i++) {
            RankEntry entry = m_entryArray[i];
            entry.setNeedUpdate(true);
        }
    }
    
    // Returns ranks that needed to be saved to database. 
    // 
    public List getTransientRanks() {
        List ret = new ArrayList(m_size);
        for ( int i = 0; i< m_size;i++) {
            RankEntry entry = m_entryArray[i];
            if ( entry != null && entry.isNeedUpdate() )
                ret.add(entry.getRankObj());
        }
        return ret;
    }
    
    public List getLinksForDomain(String domain) {
     
        if (m_domainMap.containsKey(domain)) {
            return new ArrayList(((Map) m_domainMap.get(domain)).values());
        }
        return new ArrayList();
        
    }
    
    
    // add this entry to various maps. Maps are used for
    // other convenient functions with the data in this table.
    // list better be in rank order. 
    private void enterToUtilMaps(RankEntry entry) {
        if ( m_domainMap.containsKey(entry.getDomain()) ){
            Map ranks = (Map)m_domainMap.get(entry.getDomain());
            ranks.put(new Integer(entry.getRankObj().getRank()), entry);
        }
        else {
            Map ranks = new TreeMap();
            ranks.put(new Integer(entry.getRankObj().getRank()), entry);
            m_domainMap.put(entry.getDomain(), ranks);
        }
    }
    
    
    public String toString() {
        
        StringBuffer buffer = new StringBuffer();

        buffer.append("\n");
        buffer.append("#RankTable Dump")
              .append(" isWeekly=").append(m_isWeekly)
              .append(" timeNum=").append(m_timeNum).append("\n");
        for ( int i = 0; i< m_size;i++) {
            RankEntry entry = m_entryArray[i];

            if (entry != null ) {
                buffer.append("r=").append(i+1).append("|").append(entry.getDomain()).append("|").append(entry.getLink()).append("\n");
            }
        }        
        return buffer.toString();
        
    }
}

