package com.autosite.struts.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.autosite.db.BaseAutositeDataObject;
import com.autosite.db.SiteSuggest;
import com.autosite.ds.SiteSuggestDS;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.jtrend.util.WebUtil;

public class SiteSuggestActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#SiteSuggestAction#xtent.beforeAdd");		
        SiteSuggest _SiteSuggest = (SiteSuggest)baseDbOject;

        if ( WebUtil.isNull(_SiteSuggest.getCategory())){
            m_logger.debug("category is missing in this requres " + SiteSuggestDS.objectToString(_SiteSuggest));
            throw new ActionExtentException("Category is missing.", "");
        }
        if ( WebUtil.isNull(_SiteSuggest.getSuggest())){
            m_logger.debug("suggest is missing in this requres " + SiteSuggestDS.objectToString(_SiteSuggest));
            throw new ActionExtentException("Suggest is missing.", "");
        }


        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#SiteSuggestAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        SiteSuggest _SiteSuggest = (SiteSuggest)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#SiteSuggestAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        SiteSuggest _SiteSuggest = (SiteSuggest)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#SiteSuggestAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        SiteSuggest _SiteSuggest = (SiteSuggest)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#SiteSuggestAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        SiteSuggest _SiteSuggest = (SiteSuggest)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#SiteSuggestAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        SiteSuggest _SiteSuggest = (SiteSuggest)baseDbOject;
    }

    public Map processAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new HashMap();
    }

	private SiteSuggestDS m_ds = SiteSuggestDS.getInstance();
    private static Logger m_logger = Logger.getLogger( SiteSuggestActionExtent.class);
    
}
