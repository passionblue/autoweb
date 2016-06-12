package com.autosite.struts.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.autosite.db.BaseAutositeDataObject;
import com.autosite.db.ContentAd;
import com.autosite.ds.ContentAdDS;
import com.autosite.struts.action.core.AutositeActionExtent;

public class ContentAdActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ContentAdAction#xtent.beforeAdd");		
        ContentAd _ContentAd = (ContentAd)baseDbOject;



        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#ContentAdAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        ContentAd _ContentAd = (ContentAd)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ContentAdAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        ContentAd _ContentAd = (ContentAd)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ContentAdAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        ContentAd _ContentAd = (ContentAd)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ContentAdAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        ContentAd _ContentAd = (ContentAd)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ContentAdAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        ContentAd _ContentAd = (ContentAd)baseDbOject;
    }

    public Map processAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new HashMap();
    }

	private ContentAdDS m_ds = ContentAdDS.getInstance();
    private static Logger m_logger = Logger.getLogger( ContentAdActionExtent.class);
    
}
