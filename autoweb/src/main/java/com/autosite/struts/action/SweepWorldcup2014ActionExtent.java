package com.autosite.struts.action;

import java.util.Map;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.SweepWorldcup2014;
import com.autosite.db.AutositeDataObject;
import com.autosite.ds.SweepWorldcup2014DS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.struts.form.SweepWorldcup2014Form;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;
import com.jtrend.struts.core.DefaultPageForwardManager;



public class SweepWorldcup2014ActionExtent extends AutositeActionExtent {

	//========================================================================================
	//========================================================================================


    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, AutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#SweepWorldcup2014Action#xtent.beforeAdd");		
        SweepWorldcup2014 _SweepWorldcup2014 = (SweepWorldcup2014)baseDbOject;


        if ( StringUtils.isEmpty(_SweepWorldcup2014.getPlayer())){
            throw new ActionExtentException("Player name is required");
        }

    
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, AutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#SweepWorldcup2014Action#xtent.afterAdd. id=" + baseDbOject.getId());		
        SweepWorldcup2014 _SweepWorldcup2014 = (SweepWorldcup2014)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, AutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#SweepWorldcup2014Action#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        SweepWorldcup2014 _SweepWorldcup2014 = (SweepWorldcup2014)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, AutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#SweepWorldcup2014Action#xtent.afterUpdate. id=" + baseDbOject.getId());		
        SweepWorldcup2014 _SweepWorldcup2014 = (SweepWorldcup2014)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, AutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#SweepWorldcup2014Action#xtent.beforeDelete. id=" + baseDbOject.getId());		
        SweepWorldcup2014 _SweepWorldcup2014 = (SweepWorldcup2014)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, AutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#SweepWorldcup2014Action#xtent.afterDelete. id=" + baseDbOject.getId());		
        SweepWorldcup2014 _SweepWorldcup2014 = (SweepWorldcup2014)baseDbOject;
    }

	//========================================================================================
	//========================================================================================

	//========================================================================================
	// Propprietary Commands For This Action
	//========================================================================================

	public void processCommand(HttpServletRequest request, HttpServletResponse response, String cmd) throws Exception{
		if ( cmd == null || cmd.length() == 0) return;
		//-----------------------------------------------------------------------------------
		if (cmd.equalsIgnoreCase("verify")) {
			processCommand_verify(request, response, cmd, null);
			return;
		}
	}

	public void processCommand(HttpServletRequest request, HttpServletResponse response, String cmd, AutositeDataObject _SweepWorldcup2014) throws Exception{
		if ( cmd == null || cmd.length() == 0) return;
		//-----------------------------------------------------------------------------------
		if (cmd.equalsIgnoreCase("verify")) {
			processCommand_verify(request, response, cmd, (SweepWorldcup2014) _SweepWorldcup2014);
			return;
		}
	}



	private void processCommand_verify(HttpServletRequest request, HttpServletResponse response, String cmd,  SweepWorldcup2014 _SweepWorldcup2014) throws Exception{
        HttpSession session = request.getSession();
        m_logger.debug("Running command  processCommand_verify " );
        
        String player = request.getParameter("code");
        if (StringUtils.isNotEmpty(player) && player.equalsIgnoreCase("buffalo")) {
            m_logger.info("Code verified from " + request.getRemoteAddr());
            return;
        }
        
        throw new ActionExtentException("Verification Failed");
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

//   public  String getErrorPage()			{return "sweep_worldcup2014_home";}
//   public  ected String getWarningPage()		{return "sweep_worldcup2014_home";}
//   public  String getAfterAddPage()		{return "sweep_worldcup2014_form";}
//   public  String getAfterEditPage()		{return "sweep_worldcup2014_form";}
//   public  String getAfterEditFieldPage()	{return "sweep_worldcup2014_form";}
//   public  String getAfterDeletePage()	{return "sweep_worldcup2014_home";}
//   public  String getDefaultPage()		{return "sweep_worldcup2014_home";}


//   public  String getErrorPage()			{return DefaultPageForwardManager.getInstance().getPageForwardTo("SweepWorldcup2014", "action_error_forward_page", getDefaultPage());}
//   public  String getWarningPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("SweepWorldcup2014", "action_warning_forward_page", getDefaultPage());}
//   public  String getAfterAddPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("SweepWorldcup2014", "action_normal_add_forward_page", getDefaultPage());}
//   public  String getAfterEditPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("SweepWorldcup2014", "action_normal_edit_forward_page", getDefaultPage());}
//   public  String getAfterEditFieldPage()	{return DefaultPageForwardManager.getInstance().getPageForwardTo("SweepWorldcup2014", "action_normal_editfield_forward_page", getDefaultPage());}
//   public  String getAfterDeletePage()	{return DefaultPageForwardManager.getInstance().getPageForwardTo("SweepWorldcup2014", "action_normal_delete_forward_page", getDefaultPage());}

   public  String getDefaultPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("SweepWorldcup2014", "action_default_forward_page", "sweep_worldcup2014_home");}
   public  String getConfirmPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo(((ChurMemberAction)m_action).getActionGroupName(), "confirm_page" , "confirm_required");}


   private SweepWorldcup2014DS m_ds = SweepWorldcup2014DS.getInstance();
   private static Logger m_logger = Logger.getLogger( SweepWorldcup2014ActionExtent.class);
    
}
