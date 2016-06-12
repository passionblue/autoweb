package com.autosite.struts.action.core;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.autosite.db.AutositeDataObject;
import com.autosite.struts.action.AutositeActionService;
import com.jtrend.struts.core.DefaultPageForwardManager;

public class AutositeActionExtent {

    public AutositeActionService m_action = null;

    public void beforeMain(HttpServletRequest request, HttpServletResponse response) throws Exception {
    }

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, AutositeDataObject dataObject) throws Exception {
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, AutositeDataObject dataObject) throws Exception {
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, AutositeDataObject dataObject) throws Exception {
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, AutositeDataObject dataObject) throws Exception {
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, AutositeDataObject dataObject) throws Exception {
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, AutositeDataObject dataObject) throws Exception {
    }

/*    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, DataHolderObject dataObject) throws Exception {
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, DataHolderObject dataObject) throws Exception {
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, DataHolderObject dataObject) throws Exception {
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, DataHolderObject dataObject) throws Exception {
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, DataHolderObject dataObject) throws Exception {
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, DataHolderObject dataObject) throws Exception {
    }
*/
    public void processCommand(HttpServletRequest request, HttpServletResponse response, String cmd) throws Exception {
    }

    public void processCommand(HttpServletRequest request, HttpServletResponse response, String cmd, AutositeDataObject dataObject) throws Exception {
    }

    public String getErrorPage() {
        return DefaultPageForwardManager.getInstance().getPageForwardTo(m_action.getActionName(), "action_error_forward_page", getDefaultPage());
    }

    public String getWarningPage() {
        return DefaultPageForwardManager.getInstance().getPageForwardTo(m_action.getActionName(), "action_warning_forward_page", getDefaultPage());
    }

    public String getAfterAddPage() {
        return DefaultPageForwardManager.getInstance().getPageForwardTo(m_action.getActionName(), "action_normal_add_forward_page", getDefaultPage());
    }

    public String getAfterEditPage() {
        return DefaultPageForwardManager.getInstance().getPageForwardTo(m_action.getActionName(), "action_normal_edit_forward_page", getDefaultPage());
    }

    public String getAfterEditFieldPage() {
        return DefaultPageForwardManager.getInstance().getPageForwardTo(m_action.getActionName(), "action_normal_editfield_forward_page", getDefaultPage());
    }

    public String getAfterDeletePage() {
        return DefaultPageForwardManager.getInstance().getPageForwardTo(m_action.getActionName(), "action_normal_delete_forward_page", getDefaultPage());
    }

    public String getDefaultPage() {
        return DefaultPageForwardManager.getInstance().getPageForwardTo(m_action.getActionName(), "action_default_forward_page", null);
    }

    public String getActionPageForStatus(String status) {
        return DefaultPageForwardManager.getInstance().getPageForwardTo(m_action.getActionName(), status, null);
    }
    
    public String getPageByConfig(String config) {
        return DefaultPageForwardManager.getInstance().getPageForwardTo(m_action.getActionName(), config, getDefaultPage());
    }

    public String getConfirmPage() {
        return DefaultPageForwardManager.getInstance().getPageForwardTo(m_action.getActionGroupName(), "confirm_page", "confirm_required");
    }

    public Map processAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new HashMap();
    }

    public void setAction(AutositeActionService action) {
        m_action = action;
    }

}
