/*
 * Created on Dec 1, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.seox.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.seox.db.Keyword;
import com.seox.db.User;
import com.seox.db.UserKeywordMap;
import com.seox.work.KeywordDS;
import com.seox.work.UserDS;

public class UserAnalysis {

    protected User m_user;
    protected Map<Long,Map> m_timeNumToTables = new TreeMap<Long,Map>();    

    protected Map m_domainsToBook = new HashMap();
    protected Map m_scoresToDomainsList = new TreeMap();

    protected Set m_allDomainNamesForTime = new HashSet();
    protected int m_timeNum;

    
    public UserAnalysis(User user) {
        this(user, CalendarUtil.getWeekNum());
    }
    
    
    public UserAnalysis(User user, int timeNum) {
        m_user = user;
        m_timeNum = timeNum;
    }
    
    public ScoreBook getScoreBookByDomain(String domainName) {
        return (ScoreBook) m_domainsToBook.get(domainName);
    }

    public void initAllData() throws Exception {

        UserDS userDS = UserDS.getInstance();
        User user = userDS.getUserByUsername(m_user.getUsername());

        Set ukMaps      = user.getUserKeywordMaps();
        Set userDomains = user.getUserDomains();
        
        // ###########################################################################
        // First get all keywords for the user
        // ###########################################################################

        List allKeywordsForUser = new ArrayList();
        for (Iterator iter = ukMaps.iterator(); iter.hasNext();) {

            UserKeywordMap ukm = (UserKeywordMap) iter.next();
            Long id = new Long(ukm.getKeywordId());
            Keyword k = KeywordDS.getInstance().getKeyword(id);
            allKeywordsForUser.add(k);
        }        
        
        // ###########################################################################
        // Re-arrange tables by order of time-num.
        // The structure is map of map. 
        
        // Top map will be timeNum to Map
        // Sub map will be keywordStr to RankTable.
        
        // Map<Long,Map> timeNumToTables = new TreeMap<Long,Map>();
        // 
        // Post condition : m_timeNumToTables
        // ###########################################################################
        
        m_timeNumToTables.clear();
        for (Iterator iter = allKeywordsForUser.iterator(); iter.hasNext();) {

            Keyword keyword = (Keyword) iter.next();

            // In this example, I am getting RankTableMgr in real time but 
            // in produciton, they should come from cache. 
            RankTableMgr mgr = RankTableMgrPool.getRankTableMgr(keyword, 1, true, 52);
            mgr.loadAndStore(false);
            
            List tables = mgr.getTables();
            for (Iterator iterT = tables.iterator(); iterT.hasNext();){
                RankTable table = (RankTable) iterT.next();

                m_logger.debug(">> TABLE " + table.getTimeNum() + " " + keyword.getKeyword());

                Long idKey  = new Long(table.getTimeNum());
                
                Map kwToTables = m_timeNumToTables.get(idKey);
                
                if (kwToTables == null) {
                    kwToTables = new TreeMap();
                    m_timeNumToTables.put(idKey,kwToTables);
                }
                kwToTables.put( keyword.getKeyword(), table);
            }
        }
        
        // Run anal for the week num 1926. To do this job, I need tables of all keyword for the week number in question.
        Map tables = m_timeNumToTables.get((long)m_timeNum);
        
        // ###########################################################################
        // Collect all domains on this week found in search ranks of all keywords
        // Post condition : m_allDomainNamesForTime
        // ###########################################################################
        m_allDomainNamesForTime.clear();
        if (tables != null) {
        for(Iterator iter=tables.values().iterator();iter.hasNext();) {
            RankTable table = (RankTable) iter.next();
            for(Iterator iterD=table.getDomains().iterator();iterD.hasNext();) {
                m_allDomainNamesForTime.add(iterD.next());
            }
        }
        }
        m_logger.debug("###### Domains for time " + m_timeNum + " " + m_allDomainNamesForTime.size());

        
        
        // ###########################################################################
        // Analyze all domains
        // 1. get a domain C-score.
        // Post Condition : m_domainsToBook
        //                  m_scoresToDomainsList
        // ###########################################################################
        m_domainsToBook.clear();
        m_scoresToDomainsList.clear();
        for(Iterator iter=m_allDomainNamesForTime.iterator();iter.hasNext();) {
            
            String domain = (String) iter.next();
            
            ScoreBook scoreBook = new ScoreBook(domain);
            m_domainsToBook.put(domain, scoreBook);
            
            for(Iterator iterT=tables.values().iterator();iterT.hasNext();) {
                RankTable table = (RankTable) iterT.next();
                
                List ranks = table.getLinksForDomain(domain);
                
                for(Iterator iterI=ranks.iterator();iterI.hasNext();) {
                    RankEntry entry = (RankEntry) iterI.next();
                    System.out.println(table.getTimeNum() + " DOMAIN " + domain + " " + entry.getRankObj().getRank() + " for " + table.getKeyword() + " # " + entry.getLink());
                    scoreBook.addRank(table.getKeyword(), entry);
                }
            }
            
            //score has been calculated for the domain, now order by score.

            List domainsOfSameScores = (List) m_scoresToDomainsList.get(new Integer(scoreBook.getScore()));
            if (domainsOfSameScores == null ) {
                domainsOfSameScores = new ArrayList();
                m_scoresToDomainsList.put(new Integer(scoreBook.getScore()), domainsOfSameScores);
            }
            domainsOfSameScores.add(domain);
        }

//        System.out.println(m_scoresToDomainsList); // list of all domains order by competetive score
    }

    public static void main(String[] args) throws Exception {
        KeywordDS.getInstance().loadFromDB();

        UserDS userDS = UserDS.getInstance();
        userDS.loadFromDB();

        User user = userDS.getUserByUsername("user@joshua.com");
        UserAnalysis analysis = new UserAnalysis(user);
        analysis.initAllData();
        
        System.out.println(analysis.getScoreBookByDomain("nyc.com"));
    }

    
    private static Logger m_logger = Logger.getLogger(UserAnalysis.class);
    
    public static class ScoreBook {
        
        int m_score;
        Set m_keywordSet = new HashSet();
        String m_domain;
        
        public ScoreBook(String domain) {
            m_domain = domain;
        }
        
        public int getScore() {
            return m_score + m_keywordSet.size()*3;
        }

        
        public void addRank(String keyword, RankEntry entry) {
            
            m_keywordSet.add(keyword);
            
            if ( entry.getRankObj().getRank() < 10 ) {
                m_score += 10; //* shoudlbe kw weighed
            } else if  ( entry.getRankObj().getRank() < 40 ) {
                m_score += 5;
            }
        }
        
        public String getDomain() {
            return m_domain;
        }
        
        public String toString() {
            return m_domain + " score=" + getScore();
        }
    }


}

