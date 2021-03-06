package com.autosite.struts.action;

import java.util.Map;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.BlockList;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.BlockListDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.BlockListForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;

public class BlockListActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
        m_logger.debug("#BlockListAction#xtent.beforeAdd");     
        BlockList _BlockList = (BlockList)baseDbOject;



        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
        m_logger.debug("#BlockListAction#xtent.afterAdd. id=" + baseDbOject.getId());       
        BlockList _BlockList = (BlockList)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
        m_logger.debug("#BlockListAction#xtent.beforeUpdate. id=" + baseDbOject.getId());       
        BlockList _BlockList = (BlockList)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
        m_logger.debug("#BlockListAction#xtent.afterUpdate. id=" + baseDbOject.getId());        
        BlockList _BlockList = (BlockList)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
        m_logger.debug("#BlockListAction#xtent.beforeDelete. id=" + baseDbOject.getId());       
        BlockList _BlockList = (BlockList)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
        m_logger.debug("#BlockListAction#xtent.afterDelete. id=" + baseDbOject.getId());        
        BlockList _BlockList = (BlockList)baseDbOject;
    }

    public Map processAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map ret = new HashMap();
        return ret;
    }

    //  // Configuration Option
    //

    public String getErrorPage(){return "block_list_home";}
    public String getWarningPage(){return "block_list_home";}
    public String getAfterAddPage(){return "block_list_home";}
    public String getAfterEditPage(){return "block_list_home";}
    public String getAfterEditFieldPage(){return "block_list_home";}
    public String getAfterDeletePage(){return "block_list_home";}
    public String getDefaultPage(){return "block_list_home";}
    
    

    private BlockListDS m_ds = BlockListDS.getInstance();
    private static Logger m_logger = Logger.getLogger( BlockListActionExtent.class);
    
}
