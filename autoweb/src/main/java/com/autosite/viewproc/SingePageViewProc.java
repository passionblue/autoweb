package com.autosite.viewproc;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.autosite.db.Content;
import com.autosite.db.MetaHeader;
import com.autosite.db.Page;
import com.autosite.db.Site;
import com.autosite.ds.ContentDS;
import com.autosite.ds.MetaHeaderDS;
import com.autosite.ds.PageDS;
import com.autosite.ds.SiteDS;
import com.jtrend.session.PageView;
import com.jtrend.struts.core.viewproc.ViewProc;
import com.jtrend.util.WebUtil;

public class SingePageViewProc implements ViewProc{

    public String getName() {
        return "SingePageViewProc";
    }

    
    public void process(HttpServletRequest request, HttpSession session, boolean nocache) throws Exception{
        m_logger.debug("################# " + SingePageViewProc.class.getName());
        PageView pageView = (PageView) session.getAttribute("k_view_pageview");
        
        if ( pageView.getAlias().equals("dyn_content_single")) {
            
            // Content Id Set
            if( WebUtil.isNull(request.getParameter("cid")) || WebUtil.isNull(request.getParameter("pid"))) {
                return;
            }
            
            Site site = SiteDS.getInstance().registerSite(request.getServerName());
            
            Content ct = (Content) ContentDS.getInstance().getById(request.getParameter("cid"));
            
            if ( ct == null) {
                m_logger.info("Aborting. content not found for id=" + request.getParameter("cid"));
            }

            Page page = (Page)PageDS.getInstance().getById(ct.getPageId());
            
            if (page.getSiteId() != site.getId()) {
                throw new Exception("INVALID SITE");
            }
            
            
            session.setAttribute("k_content_id", request.getParameter("cid"));
            session.setAttribute("k_page_content", ct);

            // Meta Data set for the content
            // Meta header values stored in db in NVP format. 
            // load them and pass them in session keys. 
            // there are two kinds, regular name value and http-equiv/value. 
            
                      
            List metaHeaders = MetaHeaderDS.getInstance().getByDetailIdSource(ct.getId(), "content");

            if (metaHeaders != null) {
                Map metaHeaderMap = new HashMap();
                Map metaHeaderHttpEquivMap = new HashMap();
                
                for (Iterator iterator = metaHeaders.iterator(); iterator.hasNext();) {
                    MetaHeader mh = (MetaHeader) iterator.next();
                    
                    if (mh.getName() != null && mh.getValue() != null) {
                     
                        if (WebUtil.isTrue(mh.getHttpEquiv()))
                            metaHeaderHttpEquivMap.put(mh.getName(), mh.getValue());
                        else
                            metaHeaderMap.put(mh.getName(), mh.getValue());
                    }
                }
                
                if (metaHeaderMap.size() > 0 )
                    session.setAttribute("k_meta_headers", metaHeaderMap);
                if (metaHeaderHttpEquivMap.size() > 0 )
                    session.setAttribute("k_meta_headers_http_equiv", metaHeaderHttpEquivMap);
            }
        }
    }

    public void process(HttpServletRequest request, HttpSession session, PageView viewPage, boolean nocache) throws Exception {
        // TODO Auto-generated method stub
        
    }
    
    
    private static Logger m_logger = Logger.getLogger(SingePageViewProc.class);
}
