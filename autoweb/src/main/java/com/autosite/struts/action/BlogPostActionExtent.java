package com.autosite.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.autosite.db.BaseAutositeDataObject;
import com.autosite.db.BlogPost;
import com.autosite.ds.BlogPostDS;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.jtrend.util.TimeNow;
import com.jtrend.util.WebUtil;

public class BlogPostActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#BlogPostAction#xtent.beforeAdd");		
        BlogPost _BlogPost = (BlogPost)baseDbOject;

        if ( WebUtil.isNull(_BlogPost.getSubject())){
            m_logger.debug("subject is missing in this requres " + BlogPostDS.objectToString(_BlogPost));
            throw new Exception("Subject is missing.");
        }
        if ( WebUtil.isNull(_BlogPost.getContent())){
            m_logger.debug("content is missing in this requres " + BlogPostDS.objectToString(_BlogPost));
            throw new Exception("Content is missing.");
        }

        TimeNow now = new TimeNow();
        
        _BlogPost.setPostYear(now.getYear());
        _BlogPost.setPostMonth(now.getMonth());
        _BlogPost.setPostDay(now.getDay());
        _BlogPost.setPostYearmonth(now.getYear()*100 + now.getMonth());
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#BlogPostAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        BlogPost _BlogPost = (BlogPost)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#BlogPostAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        BlogPost _BlogPost = (BlogPost)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#BlogPostAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        BlogPost _BlogPost = (BlogPost)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#BlogPostAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        BlogPost _BlogPost = (BlogPost)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#BlogPostAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        BlogPost _BlogPost = (BlogPost)baseDbOject;
    }

	private BlogPostDS m_ds = BlogPostDS.getInstance();
    private static Logger m_logger = Logger.getLogger( BlogPostActionExtent.class);
    
}
