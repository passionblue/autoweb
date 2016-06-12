/*
 * Created on Dec 8, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.seox.tools;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.seox.db.Keyword;

public class RankTableMgrPool {

    private static Map m_keywordToTableMgr = new HashMap();
    
    public static synchronized RankTableMgr getRankTableMgr(Keyword keyword, int searchEngine, boolean weekly, int maxAge) {
        RankTableMgr ret = (RankTableMgr)m_keywordToTableMgr.get(keyword.getKeyword());
        if (ret == null ) {
            ret =  new RankTableMgr(keyword, searchEngine, weekly, maxAge);
            m_keywordToTableMgr.put(keyword.getKeyword(), ret);
            try {
                ret.loadDB();
            }
            catch (Exception e) {
                m_logger.error("Error occured while creating TableMgr " + keyword.getKeyword(), e);
                return null;
            }
            m_logger.info("Adding RankTableMgr for " + keyword.getKeyword());
        } else {
            m_logger.debug("RankTableMgr found for " + keyword.getKeyword());
        }
        return ret;
    }
    
    public static synchronized boolean resetTableMgr(Keyword keyword, int searchEngine, boolean weekly, int maxAge) {

        RankTableMgr ret = (RankTableMgr)m_keywordToTableMgr.get(keyword.getKeyword());
        
        if ( ret != null ) {
            int lastestNum = (ret.getLatestTable()==null? 0:ret.getLatestTable().getTimeNum()); 
            m_logger.debug("TableMgr exist. lastest in the mgr : " + lastestNum);
        } else {
            m_logger.debug("TableMgr not existed. will create new one");
        }
        
        RankTableMgr newMgr = new RankTableMgr(keyword, searchEngine, weekly, maxAge);
        m_keywordToTableMgr.put(keyword.getKeyword(), newMgr);
        return true;
    }
    private static Logger m_logger = Logger.getLogger(RankTableMgrPool.class);
    
}
