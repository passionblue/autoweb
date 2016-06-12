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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.jtrend.session.PageView;
import com.jtrend.struts.core.viewproc.ViewProc;
import com.jtrend.struts.core.viewproc.ViewProcFactory;
import com.seox.db.Keyword;
import com.seox.db.UserKeywordMap;
import com.seox.util.DateFormatUtil;
import com.seox.view.viewhelper.KeywordView;
import com.seox.work.KeywordDS;
import com.seox.work.UserBO;

public class KeywordViewProc implements ViewProc {

    private static Logger m_logger = Logger.getLogger(KeywordViewProc.class);
    
    public String getName() {
        return "KeywordViewProc";
    }
    
    public void process(HttpServletRequest request, HttpSession session, boolean nocache) throws Exception{
        
        UserBO userBO = (UserBO) session.getAttribute("k_userbo");
        if (userBO == null ) {
            m_logger.warn("User object was not found in this session. aborting view proc");
            return;
        }

        if (!nocache && session.getAttribute("kv_keywords") != null ) {
            m_logger.debug("Keyword views already exists");
            return;
        }
        
/*
        Disable this until we hit the performance issue
        if (!nocache && userBO.getSessionSave("kv_keywords") != null) {
            m_logger.debug("Copying existing view from userBO");
            session.setAttribute("kv_keywords", userBO.getSessionSave("kv_keywords"));
            return;
        }
*/        
        m_logger.debug("ViewProc started for " + userBO.getUserObj().getUsername());
        
        List keywords = userBO.getKeywords();
        List retKeyView = new ArrayList();
        
        for(Iterator iter = userBO.getUserObj().getUserKeywordMaps().iterator(); iter.hasNext();) {
            
            UserKeywordMap kwmap = (UserKeywordMap) iter.next();
            Keyword kw =  KeywordDS.getInstance().getKeyword(new Long(kwmap.getKeywordId()));
            
            KeywordView kv = new KeywordView(kw.getKeyword());
            kv.setCategory(null);
            kv.setEnteredDate(DateFormatUtil.getDateStr(kwmap.getEnteredT())); 
            kv.setKeyword(kw.getKeyword());
            kv.setKeywordId(String.valueOf(kw.getKeywordId()));

            retKeyView.add(kv);
        }
        
        
/*        
        Iterating with keywords list. Could not get date for add keyword for the user. 
        for(Iterator iter = keywords.iterator(); iter.hasNext();) {
            
            Keyword kw = (Keyword) iter.next();
            
            KeywordView kv = new KeywordView(kw.getKeyword());
            kv.setCategory(null);
            kv.setEnteredDate(DateFormatUtil.getDateStr(kw.getEnteredT())); 
            kv.setKeyword(kw.getKeyword());
            kv.setKeywordId(String.valueOf(kw.getKeywordId()));

            retKeyView.add(kv);
        }
*/        
        ViewProcFactory.getInstance().getViewProc("domain_manage").process( request, session, false);
        
        session.setAttribute("kv_keywords", retKeyView);
        userBO.setSessionSave("kv_keywords", retKeyView);
    }
    public void process(HttpServletRequest request, HttpSession session, PageView viewPage, boolean nocache) throws Exception {
    }
}
