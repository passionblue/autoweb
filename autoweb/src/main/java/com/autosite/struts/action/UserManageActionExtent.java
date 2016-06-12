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

import com.autosite.ds.UserManageDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.UserManageForm;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;
import com.jtrend.struts.core.DefaultPageForwardManager;

import com.autosite.holder.UserManageDataHolder;
import com.autosite.holder.DataHolderObject;


public class UserManageActionExtent extends AutositeActionExtent {

    //========================================================================================
    //========================================================================================


    //========================================================================================
    //========================================================================================
    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject) throws Exception{
        m_logger.debug("#UserManageAction#xtent.beforeAdd");        
        beforeAdd(request, response, holderObject.getDataObject());
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject)  throws Exception {
        m_logger.debug("#UserManageAction#xtent.afterAdd. id=" + holderObject.getId());     
        afterAdd(request, response, holderObject.getDataObject());
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject) throws Exception{
        m_logger.debug("#UserManageAction#xtent.beforeUpdate. id=" + holderObject.getId());     
        beforeUpdate(request, response, holderObject.getDataObject());
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject) throws Exception{
        m_logger.debug("#UserManageAction#xtent.afterUpdate. id=" + holderObject.getId());      
        afterUpdate(request, response, holderObject.getDataObject());
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject) throws Exception{
        m_logger.debug("#UserManageAction#xtent.beforeDelete. id=" + holderObject.getId());     
        beforeDelete(request, response, holderObject.getDataObject());
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject) throws Exception{
        m_logger.debug("#UserManageAction#xtent.afterDelete. id=" + holderObject.getId());      
        afterDelete(request, response, holderObject.getDataObject());
    }

    //========================================================================================
    // Propprietary Commands For This Action
    //========================================================================================

    public void processCommand(HttpServletRequest request, HttpServletResponse response, String cmd) throws Exception{
        if ( cmd == null || cmd.length() == 0) return;
        //-----------------------------------------------------------------------------------
        if (cmd.equalsIgnoreCase("add")) {
            processCommand_add(request, response, cmd);
            return;
        }
        //-----------------------------------------------------------------------------------
        if (cmd.equalsIgnoreCase("deactivate")) {
            processCommand_deactivate(request, response, cmd);
            return;
        }
        //-----------------------------------------------------------------------------------
        if (cmd.equalsIgnoreCase("del")) {
            processCommand_del(request, response, cmd);
            return;
        }
        //-----------------------------------------------------------------------------------
        if (cmd.equalsIgnoreCase("activate")) {
            processCommand_activate(request, response, cmd);
            return;
        }
    }



    private void processCommand_add(HttpServletRequest request, HttpServletResponse response, String cmd) throws Exception{
        HttpSession session = request.getSession();
        
    }

    private void processCommand_deactivate(HttpServletRequest request, HttpServletResponse response, String cmd) throws Exception{
        HttpSession session = request.getSession();
        
    }

    private void processCommand_del(HttpServletRequest request, HttpServletResponse response, String cmd) throws Exception{
        HttpSession session = request.getSession();
        
    }

    private void processCommand_activate(HttpServletRequest request, HttpServletResponse response, String cmd) throws Exception{
        HttpSession session = request.getSession();
        
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

//   public  String getErrorPage()          {return "user_manage_home";}
//   public  ected String getWarningPage()      {return "user_manage_home";}
//   public  String getAfterAddPage()       {return "user_manage_form";}
//   public  String getAfterEditPage()      {return "user_manage_form";}
//   public  String getAfterEditFieldPage() {return "user_manage_form";}
//   public  String getAfterDeletePage()    {return "user_manage_home";}
//   public  String getDefaultPage()        {return "user_manage_home";}


//   public  String getErrorPage()          {return DefaultPageForwardManager.getInstance().getPageForwardTo("UserManage", "action_error_forward_page", getDefaultPage());}
//   public  String getWarningPage()        {return DefaultPageForwardManager.getInstance().getPageForwardTo("UserManage", "action_warning_forward_page", getDefaultPage());}
//   public  String getAfterAddPage()       {return DefaultPageForwardManager.getInstance().getPageForwardTo("UserManage", "action_normal_add_forward_page", getDefaultPage());}
//   public  String getAfterEditPage()      {return DefaultPageForwardManager.getInstance().getPageForwardTo("UserManage", "action_normal_edit_forward_page", getDefaultPage());}
//   public  String getAfterEditFieldPage() {return DefaultPageForwardManager.getInstance().getPageForwardTo("UserManage", "action_normal_editfield_forward_page", getDefaultPage());}
//   public  String getAfterDeletePage()    {return DefaultPageForwardManager.getInstance().getPageForwardTo("UserManage", "action_normal_delete_forward_page", getDefaultPage());}

   public  String getDefaultPage()      {return DefaultPageForwardManager.getInstance().getPageForwardTo("UserManage", "action_default_forward_page", "user_manage_home");}
   public  String getConfirmPage()      {return DefaultPageForwardManager.getInstance().getPageForwardTo(((ChurMemberAction)m_action).getActionGroupName(), "confirm_page" , "confirm_required");}


   private UserManageDS m_ds = UserManageDS.getInstance();
   private static Logger m_logger = Logger.getLogger( UserManageActionExtent.class);
    
}
