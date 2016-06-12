/*
 * Created on Dec 9, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.seox.view.core;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.seox.db.Keyword;
import com.seox.tools.CalendarUtil;
import com.seox.tools.RankEntry;
import com.seox.tools.RankTable;
import com.seox.tools.RankTableMgr;
import com.seox.tools.RankTableMgrPool;
import com.seox.view.viewhelper.RanksView;
import com.seox.work.KeywordDS;

public class ViewDataTools {
    public static RanksView createRanksView(String domain, Keyword keyword) {
        return createRanksView(domain, keyword, 1, true, 26);
    }

    public static RanksView createRanksView(String domain, Keyword keyword, int searchEngine, boolean weekly, int maxAge) {
        
        RankTableMgr rankTableMgr = RankTableMgrPool.getRankTableMgr(keyword, searchEngine, weekly, maxAge);
        
        RanksView ret = new RanksView(domain, keyword.getKeyword());
        
        Map histRanks = rankTableMgr.getHistoryByDomain(domain, false);
        
        int curTime = CalendarUtil.getCurTimeNum(weekly);
        
        RankTable latest = rankTableMgr.getLatestTable();
        
        if (latest == null) return ret;
        
        int latestTimeNum = latest.getTimeNum();

        // Incase keywords were not tracked. Use the latest one as a top
        curTime = latestTimeNum;
        
        for(Iterator iter=histRanks.values().iterator();iter.hasNext();) {
            
            List list = (List) iter.next();
            if (list.size() == 0 ) continue;
            
            RankEntry rankEntry = (RankEntry) list.get(0);

            int diff  = curTime - rankEntry.getTimeNum();
            if (diff <0 ) continue;
            
            int ranks[] =  ret.getRanks(searchEngine, weekly);
            ranks[diff] = rankEntry.getRankObj().getRank();
            
            //m_logger.debug("Created RanksView: dom=" + domain + " key=" + keyword.getKeyword() + "|"+rankEntry.getRankObj().getRank() +" rank -> " + diff  + " th history");
        }
        return ret;
    }
    
    public static void main(String[] args) throws Exception{
        KeywordDS.getInstance().loadFromDB();
        
        Keyword keyword = KeywordDS.getInstance().findKeyword("new york");
        
        RanksView ranksView = createRanksView("nyc.com", keyword);
    }
    
    private static Logger m_logger = Logger.getLogger(ViewDataTools.class);
}
