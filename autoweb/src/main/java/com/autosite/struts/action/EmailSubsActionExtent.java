package com.autosite.struts.action;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.autosite.db.BaseAutositeDataObject;
import com.autosite.db.EmailSubs;
import com.autosite.ds.EmailSubsDS;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.jtrend.util.WebUtil;

public class EmailSubsActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
        m_logger.debug("#EmailSubsAction#xtent.beforeAdd");     
        EmailSubs _EmailSubs = (EmailSubs)baseDbOject;
        
        if ( WebUtil.isNull(_EmailSubs.getEmail())){
            m_logger.debug("Email is missing in this requres " + m_ds.objectToString(_EmailSubs));
            throw new Exception("Email is missing.");
        }

        List emails = m_ds.getBySiteIdSubject(_EmailSubs.getSiteId(), _EmailSubs.getSubject());
        for (Iterator iterator = emails.iterator(); iterator.hasNext();) {
            EmailSubs es = (EmailSubs) iterator.next();
            
            if (es.getEmail().equals(_EmailSubs.getEmail())){
                m_logger.debug("Email is missing in this requres " + m_ds.objectToString(_EmailSubs));
                throw new Exception("This email arelady exists.");
            }
        }
        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
        m_logger.debug("#EmailSubsAction#xtent.afterAdd. id=" + baseDbOject.getId());       
        EmailSubs _EmailSubs = (EmailSubs)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
        m_logger.debug("#EmailSubsAction#xtent.beforeUpdate. id=" + baseDbOject.getId());       
        EmailSubs _EmailSubs = (EmailSubs)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
        m_logger.debug("#EmailSubsAction#xtent.afterUpdate. id=" + baseDbOject.getId());        
        EmailSubs _EmailSubs = (EmailSubs)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
        m_logger.debug("#EmailSubsAction#xtent.beforeDelete. id=" + baseDbOject.getId());       
        EmailSubs _EmailSubs = (EmailSubs)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
        m_logger.debug("#EmailSubsAction#xtent.afterDelete. id=" + baseDbOject.getId());        
        EmailSubs _EmailSubs = (EmailSubs)baseDbOject;
    }

    private EmailSubsDS m_ds = EmailSubsDS.getInstance();
    private static Logger m_logger = Logger.getLogger( EmailSubsActionExtent.class);
    
}
