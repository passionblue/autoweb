package com.autosite.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.EcPageProductRel;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.EcPageProductRelDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.EcPageProductRelForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;

public class EcPageProductRelActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcPageProductRelAction#xtent.beforeAdd");		
        EcPageProductRel _EcPageProductRel = (EcPageProductRel)baseDbOject;

        if ( WebUtil.isNull(_EcPageProductRel.getCategoryId())){
            m_logger.debug("pageId is missing in this requres " + EcPageProductRelDS.objectToString(_EcPageProductRel));
            throw new Exception("pageId is missing.");
        }
        if ( WebUtil.isNull(_EcPageProductRel.getProductId())){
            m_logger.debug("productId is missing in this requres " + EcPageProductRelDS.objectToString(_EcPageProductRel));
            throw new Exception("productId is missing.");
        }
        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#EcPageProductRelAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        EcPageProductRel _EcPageProductRel = (EcPageProductRel)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcPageProductRelAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        EcPageProductRel _EcPageProductRel = (EcPageProductRel)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcPageProductRelAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        EcPageProductRel _EcPageProductRel = (EcPageProductRel)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcPageProductRelAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        EcPageProductRel _EcPageProductRel = (EcPageProductRel)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcPageProductRelAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        EcPageProductRel _EcPageProductRel = (EcPageProductRel)baseDbOject;
    }

	private EcPageProductRelDS m_ds = EcPageProductRelDS.getInstance();
    private static Logger m_logger = Logger.getLogger( EcPageProductRelActionExtent.class);
    
}
