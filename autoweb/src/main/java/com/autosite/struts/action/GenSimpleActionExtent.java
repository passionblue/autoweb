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

import com.autosite.db.GenSimple;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.GenSimpleDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.GenSimpleForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;

import com.autosite.holder.GenSimpleDataHolder;
import com.autosite.holder.GenTableDataHolder;
import com.autosite.holder.DataHolderObject;


public class GenSimpleActionExtent extends AutositeActionExtent {

	//========================================================================================
	//========================================================================================


    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#GenSimpleAction#xtent.beforeAdd");		
        GenSimple _GenSimple = (GenSimple)baseDbOject;


        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#GenSimpleAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        GenSimple _GenSimple = (GenSimple)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#GenSimpleAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        GenSimple _GenSimple = (GenSimple)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#GenSimpleAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        GenSimple _GenSimple = (GenSimple)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#GenSimpleAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        GenSimple _GenSimple = (GenSimple)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#GenSimpleAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        GenSimple _GenSimple = (GenSimple)baseDbOject;
    }

	//========================================================================================
	//========================================================================================
    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject) throws Exception{
		m_logger.debug("#GenSimpleAction#xtent.beforeAdd");		
		beforeAdd(request, response, holderObject.getDataObject());
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject)  throws Exception {
		m_logger.debug("#GenSimpleAction#xtent.afterAdd. id=" + holderObject.getId());		
		afterAdd(request, response, holderObject.getDataObject());
    }

    public  void beforeUpdate(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject) throws Exception{
		m_logger.debug("#GenSimpleAction#xtent.beforeUpdate. id=" + holderObject.getId());		
		beforeUpdate(request, response, holderObject.getDataObject());
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject) throws Exception{
		m_logger.debug("#GenSimpleAction#xtent.afterUpdate. id=" + holderObject.getId());		
		afterUpdate(request, response, holderObject.getDataObject());
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject) throws Exception{
		m_logger.debug("#GenSimpleAction#xtent.beforeDelete. id=" + holderObject.getId());		
		beforeDelete(request, response, holderObject.getDataObject());
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject) throws Exception{
		m_logger.debug("#GenSimpleAction#xtent.afterDelete. id=" + holderObject.getId());		
		afterDelete(request, response, holderObject.getDataObject());
    }

	//========================================================================================
	// Propprietary Commands For This Action
	//========================================================================================

    public void processCommand(HttpServletRequest request, HttpServletResponse response, String cmd) throws Exception{
		if ( cmd == null || cmd.length() == 0) return;
		//-----------------------------------------------------------------------------------
		if (cmd.equalsIgnoreCase("command1")) {

		}
		//-----------------------------------------------------------------------------------
		if (cmd.equalsIgnoreCase("command2")) {

		}
		//-----------------------------------------------------------------------------------
		if (cmd.equalsIgnoreCase("command3")) {

		}
	}

	//========================================================================================
	// AJAX
	//========================================================================================
    public Map processAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map ret = new HashMap();
        return ret;
    }

	//========================================================================================
	// Configuration Option
	//========================================================================================

    public String getErrorPage(){return "gen_simple_home";}
    public String getWarningPage(){return "gen_simple_home";}
    public String getAfterAddPage(){return "gen_simple_home";}
    public String getAfterEditPage(){return "gen_simple_home";}
    public String getAfterEditFieldPage(){return "gen_simple_home";}
    public String getAfterDeletePage(){return "gen_simple_home";}
    public String getDefaultPage(){return "gen_simple_home";}

   private GenSimpleDS m_ds = GenSimpleDS.getInstance();
   private static Logger m_logger = Logger.getLogger( GenSimpleActionExtent.class);
    
}
