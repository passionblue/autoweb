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
import com.seox.db.UserDomain;
import com.seox.db.UserKeywordMap;
import com.seox.tools.RankTable;
import com.seox.tools.RankTableMgr;
import com.seox.tools.RankTableMgrPool;
import com.seox.util.DateFormatUtil;
import com.seox.view.core.ViewDataTools;
import com.seox.view.viewhelper.KeywordDetailView;
import com.seox.view.viewhelper.KeywordView;
import com.seox.view.viewhelper.RankView;
import com.seox.view.viewhelper.RanksView;
import com.seox.work.KeywordDS;
import com.seox.work.UserBO;

public class KeywordDetailViewProc implements ViewProc {

    private static Logger m_logger = Logger.getLogger(KeywordDetailViewProc.class);
    
    public String getName() {
        return "KeywordDetailViewProc";
    }
    
    public void process(HttpServletRequest request, HttpSession session, boolean nocache) throws Exception{
        
        UserBO userBO = (UserBO) session.getAttribute("k_userbo");
        if (userBO == null ) {
            m_logger.warn("User object was not found in this session. aborting view proc");
            return;
        }

        Map params = (Map) session.getAttribute("kr_params");
        int numRanks = WebUtil.covert((String)params.get("num"), 20);        

        String keywordReq = (String) params.get("keyword");
        if(keywordReq == null) keywordReq = "";
        m_logger.info("Keyword [" + keywordReq + "] is in the request for detail view");
        m_logger.debug("ViewProc started for " + userBO.getUserObj().getUsername());
        
        List retKeyView = new ArrayList();
        KeywordDetailView kdv = new KeywordDetailView();
        
        for(Iterator iter = userBO.getUserObj().getUserKeywordMaps().iterator(); iter.hasNext();) {
            
            UserKeywordMap kwmap = (UserKeywordMap) iter.next();
            Keyword kw =  KeywordDS.getInstance().getKeyword(new Long(kwmap.getKeywordId()));
            
            KeywordView kv = new KeywordView(kw.getKeyword());
            kv.setCategory(null);
            kv.setEnteredDate(DateFormatUtil.getDateStr(kwmap.getEnteredT())); 
            kv.setKeyword(kw.getKeyword());
            kv.setKeywordId(String.valueOf(kw.getKeywordId()));

            // Keyword found 
            if (kv.getKeyword().equals(keywordReq)) {
                kdv.setKeyword(kv.getKeyword());
                kdv.setRanks(null);
                m_logger.debug("Keyword found " + keywordReq);
                
                RankTableMgr rankTableMgr = RankTableMgrPool.getRankTableMgr(kw, 1, true, 26);
                
                RankTable table = rankTableMgr.getLatestTable();
                List ranks = new ArrayList();

                if (table == null ) {
                    session.setAttribute("k_top_error_text", "Keyword -" + kw.getKeyword() + " does not have tracked ranks yet in db. please check back again");
                } else {
                    if (table.getSize() < numRanks) numRanks = table.getSize();

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
                
                
                // For vs. Domains view
                List userDomains = userBO.getDomains();
                for(Iterator iterDm = userDomains.iterator(); iterDm.hasNext();) {
                    UserDomain dm = (UserDomain) iterDm.next();
                    RanksView ranksView = ViewDataTools.createRanksView(dm.getDomain(), kw);
                    kv.addRanksView(dm.getDomain(), ranksView);
                }                
                
                kdv.setKeywordView(kv);
                
                break;
            }
            retKeyView.add(kv);
        }
        
        session.setAttribute("kv_keyword_detail", kdv);
        userBO.setSessionSave("kv_keyword_detail", kdv);
    }
    public void process(HttpServletRequest request, HttpSession session, PageView viewPage, boolean nocache) throws Exception {
    }
}
