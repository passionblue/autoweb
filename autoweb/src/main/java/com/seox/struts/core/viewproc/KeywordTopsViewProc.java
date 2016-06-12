/*
 * Created on Nov 26, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.seox.struts.core.viewproc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.jtrend.session.PageView;
import com.jtrend.struts.core.viewproc.ViewProc;
import com.jtrend.util.WebUtil;
import com.seox.db.Keyword;
import com.seox.db.Rank;
import com.seox.db.UserKeywordMap;
import com.seox.tools.RankTable;
import com.seox.tools.RankTableMgr;
import com.seox.tools.RankTableMgrPool;
import com.seox.util.DateFormatUtil;
import com.seox.view.viewhelper.KeywordDetailView;
import com.seox.view.viewhelper.KeywordView;
import com.seox.view.viewhelper.RankView;
import com.seox.work.KeywordDS;
import com.seox.work.UserBO;

public class KeywordTopsViewProc implements ViewProc {

    private static Logger m_logger = Logger.getLogger(KeywordTopsViewProc.class);
    
    public String getName() {
        return "KeywordTopsViewProc";
    }
    
    public void process(HttpServletRequest request, HttpSession session, boolean nocache) throws Exception{
        
        UserBO userBO = (UserBO) session.getAttribute("k_userbo");
        if (userBO == null ) {
            m_logger.warn("User object was not found in this session. aborting view proc");
            return;
        }

        Map params = (Map) session.getAttribute("kr_params");
        
        
        int numToDisplay = WebUtil.covert((String)params.get("num"), 20);        
        
        
        String keywordReq = (String) params.get("keyword");
        m_logger.info("Keyword [" + keywordReq + "] is in the request for detail view");
        m_logger.debug("ViewProc started for " + userBO.getUserObj().getUsername());
        
        List retTopsView = new ArrayList();
        
        for(Iterator iter = userBO.getUserObj().getUserKeywordMaps().iterator(); iter.hasNext();) {
            
            KeywordDetailView kdv = new KeywordDetailView();
                UserKeywordMap kwmap = (UserKeywordMap) iter.next();
            Keyword kw =  KeywordDS.getInstance().getKeyword(new Long(kwmap.getKeywordId()));
            
            KeywordView kv = new KeywordView(kw.getKeyword());
            kv.setCategory(null);
            kv.setEnteredDate(DateFormatUtil.getDateStr(kwmap.getEnteredT())); 
            kv.setKeyword(kw.getKeyword());
            kv.setKeywordId(String.valueOf(kw.getKeywordId()));

            kdv.setKeyword(kv.getKeyword());
            kdv.setRanks(null);
            m_logger.debug("Keyword found " + keywordReq);
            
            RankTableMgr rankTableMgr = RankTableMgrPool.getRankTableMgr(kw, 1, true, 26);
            
            RankTable table = rankTableMgr.getLatestTable();
            List ranks = new ArrayList();

            if (table == null ) {
            } else {
                int numRanks = numToDisplay;
                if (table.getSize() < numRanks) numRanks = table.getSize();
                
                m_logger.debug("number of ranks to display " + numRanks);
                for (int i = 1; i <= numRanks; i++) {
                    Rank rank = table.getRank(i);
                    RankView rankView = new RankView();
                    rankView.setDomain(rank.getDomain());
                    rankView.setKeyword(kw.getKeyword());
                    rankView.setLink(rank.getLink());
                    rankView.setTimeNum(table.getTimeNum());
                    ranks.add(rankView);
                }
            }
            kdv.setRanks(ranks);
            retTopsView.add(kdv);
        }

        String hiliteDomain = (String) params.get("hilite"); 
        if (hiliteDomain != null ) {
            session.setAttribute("kv_keyword_tops_hilite", hiliteDomain);
            m_logger.debug("Domain hilite requested = "  +  hiliteDomain);
        }
        
        session.setAttribute("kv_keyword_tops", retTopsView);
        userBO.setSessionSave("kv_keyword_tops", retTopsView);
    }
    public void process(HttpServletRequest request, HttpSession session, PageView viewPage, boolean nocache) throws Exception {
    }
}
