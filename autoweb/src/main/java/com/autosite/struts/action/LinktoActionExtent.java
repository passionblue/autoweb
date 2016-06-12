package com.autosite.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.Linkto;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.LinktoDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.LinktoForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;

public class LinktoActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#LinktoAction#xtent.beforeAdd");		
        Linkto _Linkto = (Linkto)baseDbOject;

        if ( WebUtil.isNull(_Linkto.getLinkKey())){
            m_logger.debug("linkKey is missing in this requres " + LinktoDS.objectToString(_Linkto));
            throw new ActionExtentException("Link Key is missing.", "");
        }
        if ( WebUtil.isNull(_Linkto.getLinkTarget())){
            m_logger.debug("linkTarget is missing in this requres " + LinktoDS.objectToString(_Linkto));
            throw new ActionExtentException("Link Target is missing.", "");
        }
        
        if (LinktoDS.getInstance().getObjectByLinkKey(_Linkto.getLinkKey()) != null) {
            m_logger.debug("The same value already exists." + _Linkto.getLinkKey());
            throw new ActionExtentException("The same value already exists." + _Linkto.getLinkKey(), "linkto_add");
        }
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#LinktoAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        Linkto _Linkto = (Linkto)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#LinktoAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        Linkto _Linkto = (Linkto)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#LinktoAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        Linkto _Linkto = (Linkto)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#LinktoAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        Linkto _Linkto = (Linkto)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#LinktoAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        Linkto _Linkto = (Linkto)baseDbOject;
    }

	private LinktoDS m_ds = LinktoDS.getInstance();
    private static Logger m_logger = Logger.getLogger( LinktoActionExtent.class);
    
}
