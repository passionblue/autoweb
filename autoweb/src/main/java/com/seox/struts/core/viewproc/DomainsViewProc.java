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
import com.seox.db.Keyword;
import com.seox.db.UserDomain;
import com.seox.util.DateFormatUtil;
import com.seox.view.core.ViewDataTools;
import com.seox.view.viewhelper.DomainView;
import com.seox.view.viewhelper.RanksView;
import com.seox.work.UserBO;

public class DomainsViewProc implements ViewProc {
    
    private static Logger m_logger = Logger.getLogger(DomainsViewProc.class);
    public String getName() {
        return "DomainsViewProc";
    }

    public void process(HttpServletRequest request, HttpSession session, boolean nocache) {
        UserBO userBO = (UserBO) session.getAttribute("k_userbo");
        if (userBO == null ) {
            m_logger.warn("User object was not found in this session. aborting view proc");
            return;
        }

        if (!nocache && session.getAttribute("kv_domains") != null ) {
            m_logger.debug("Domain views already exists");
            return;
        }
/*
        Remove this until we hit the performance issue. 
        if (!nocache && userBO.getSessionSave("kv_domains") != null) {
            m_logger.debug("Copying existing view from userBO");
            session.setAttribute("kv_domains", userBO.getSessionSave("kv_domains"));
            return;
        }
*/        
        m_logger.debug("ViewProc started for " + userBO.getUserObj().getUsername());
        
        List userDomains = userBO.getDomains();
        List keywords = userBO.getKeywords();
        
        List damainViews = new ArrayList();
        for(Iterator iter = userDomains.iterator(); iter.hasNext();) {
            
            UserDomain dm = (UserDomain) iter.next();
            
            DomainView dv = new DomainView(dm.getDomain());
            dv.setCategory(null);
            dv.setEnteredDate(DateFormatUtil.getDateStr(dm.getEnteredT())); 
            dv.setDomain(dm.getDomain());
            dv.setFullLink("FULL");
            dv.setUserDomainId(String.valueOf(dm.getUserDomainId()));

            for(Iterator iterKw=keywords.iterator();iterKw.hasNext();) {
                Keyword keyword = (Keyword) iterKw.next();
                RanksView ranksView = ViewDataTools.createRanksView(dm.getDomain(), keyword);
                dv.addRanksView(keyword.getKeyword(), ranksView);
                
                m_logger.debug("Add ranksView to " + userBO.getUserObj().getUsername() + " Dom=" + dm.getDomain() + " kw=" + keyword.getKeyword());
            }
            
            damainViews.add(dv);
        }
        
        session.setAttribute("kv_domains", damainViews);
        userBO.setSessionSave("kv_domains", damainViews);

    }
    public void process(HttpServletRequest request, HttpSession session, PageView viewPage, boolean nocache) throws Exception {
    }
}
