package com.autosite.struts.action;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.autosite.db.AutositeDataObject;
import com.autosite.db.ChurIncome;
import com.autosite.db.ChurIncomeItem;
import com.autosite.db.ChurMember;
import com.autosite.ds.ChurIncomeAddDS;
import com.autosite.ds.ChurIncomeDS;
import com.autosite.ds.ChurIncomeItemDS;
import com.autosite.ds.ChurMemberDS;
import com.autosite.holder.ChurIncomeAddDataHolder;
import com.autosite.holder.ChurIncomeDataHolder;
import com.autosite.holder.DataHolderObject;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.util.chur.ChurUtil;
import com.jtrend.struts.core.DefaultPageForwardManager;
import com.jtrend.util.WebParamUtil;


public class ChurIncomeAddActionExtent extends AutositeActionExtent {

	//========================================================================================
	//========================================================================================


	//========================================================================================
	//========================================================================================
    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject) throws Exception{
		m_logger.debug("#ChurIncomeAddAction#xtent.beforeAdd");		

		ChurIncomeAddDataHolder data = (ChurIncomeAddDataHolder) holderObject;
        ChurMember member = ChurMemberDS.getInstance().getById(data.getChurMemberId());
        
        if (member == null) 
            throw new ActionExtentException("Member was not selected", getAfterAddPage());
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, AutositeDataObject holderObject)  throws Exception {
		m_logger.debug("#ChurIncomeAddAction#xtent.afterAdd. id=" + holderObject.getId());		
//		afterAdd(request, response, ((DataHolderObject)holderObject).getDataObject());

        ChurIncomeAddDataHolder data = (ChurIncomeAddDataHolder) holderObject;
        m_logger.info("Adding afterAdd for site" + data.getSiteId());
        
        ChurMember member = ChurMemberDS.getInstance().getById(data.getChurMemberId());
        
        if (member == null) 
            throw new Exception("Member was not selected");
        
        Double tithe = WebParamUtil.getDoubleValue(data.getTithe());
        Double weekly = WebParamUtil.getDoubleValue(data.getWeekly());
        Double thanks = WebParamUtil.getDoubleValue(data.getThanks());
        Double mission = WebParamUtil.getDoubleValue(data.getMission());
        Double contruction = WebParamUtil.getDoubleValue(data.getConstruction());
        Double other = WebParamUtil.getDoubleValue(data.getOther());
        int year = WebParamUtil.getIntValue(data.getYear());
        if ( year == 0 ) year = ChurUtil.getCurrentYear();
        
        String week = data.getWeek();
        if (week == null || week.trim().isEmpty()) 
            week = ChurUtil.getWeekString();
        
        if ( tithe > 0.0 ) {
            
            ChurIncomeItem incomeItem = ChurIncomeItemDS.getInstance().getBySiteIdToIncomeItem(data.getSiteId(), "tithe");
            if ( incomeItem != null) { 
                ChurIncome income = new ChurIncome();
                income.setSiteId(data.getSiteId());
                income.setChurMemberId(member.getId());
                income.setIncomeItemId(incomeItem.getId());
                income.setYear(year);
                income.setWeek(data.getWeek());
                income.setAmmount(tithe);
                income.setTimeCreated( new Timestamp(System.currentTimeMillis()));
                m_logger.info("Tithe " + incomeItem.getIncomeItem() + " " + tithe);
                ChurIncomeDS.getInstance().put(new ChurIncomeDataHolder(income));
            }   else {
                m_logger.error("Income item not found for tithe", new Exception());
            }
        }
        

        if ( weekly > 0.0 ) {
            
            ChurIncomeItem incomeItem = ChurIncomeItemDS.getInstance().getBySiteIdToIncomeItem(data.getSiteId(), "weekly");
            if ( incomeItem != null) { 
                ChurIncome income = new ChurIncome();
                income.setSiteId(data.getSiteId());
                income.setChurMemberId(member.getId());
                income.setIncomeItemId(incomeItem.getId());
                income.setYear(year);
                income.setWeek(data.getWeek());
                income.setAmmount(weekly);
                income.setTimeCreated( new Timestamp(System.currentTimeMillis()));
                m_logger.info("weekly " + incomeItem.getIncomeItem() + " " + weekly);
                ChurIncomeDS.getInstance().put(new ChurIncomeDataHolder(income));
                
            }   else {
                m_logger.error("Income item not found for tithe", new Exception());
            }
        }

        if ( thanks > 0.0 ) {
            ChurIncomeItem incomeItem = ChurIncomeItemDS.getInstance().getBySiteIdToIncomeItem(data.getSiteId(), "thanks");
            if ( incomeItem != null) { 
                ChurIncome income = new ChurIncome();
                income.setSiteId(data.getSiteId());
                income.setChurMemberId(member.getId());
                income.setIncomeItemId(incomeItem.getId());
                income.setWeek(data.getWeek());
                income.setYear(year);
                income.setAmmount(thanks);
                income.setTimeCreated( new Timestamp(System.currentTimeMillis()));
                m_logger.info("thanks " + incomeItem.getIncomeItem() + " " + thanks);
                ChurIncomeDS.getInstance().put(new ChurIncomeDataHolder(income));
                
            }   else {
                m_logger.error("Income item not found for tithe", new Exception());
            }
        }
        if ( mission > 0.0 ) {
            
            ChurIncomeItem incomeItem = ChurIncomeItemDS.getInstance().getBySiteIdToIncomeItem(data.getSiteId(), "mission");
            if ( incomeItem != null) { 
                ChurIncome income = new ChurIncome();
                income.setSiteId(data.getSiteId());
                income.setChurMemberId(member.getId());
                income.setIncomeItemId(incomeItem.getId());
                income.setYear(year);
                income.setWeek(data.getWeek());
                income.setAmmount(mission);
                income.setTimeCreated( new Timestamp(System.currentTimeMillis()));
                m_logger.info("mission " + incomeItem.getIncomeItem() + " " + mission);
                ChurIncomeDS.getInstance().put(new ChurIncomeDataHolder(income));
            }   else {
                m_logger.error("Income item not found for tithe", new Exception());
            }
        }

        if ( contruction > 0.0 ) {
            
            ChurIncomeItem incomeItem = ChurIncomeItemDS.getInstance().getBySiteIdToIncomeItem(data.getSiteId(), "construction");
            if ( incomeItem != null) { 
                ChurIncome income = new ChurIncome();
                income.setSiteId(data.getSiteId());
                income.setChurMemberId(member.getId());
                income.setIncomeItemId(incomeItem.getId());
                income.setYear(year);
                income.setWeek(data.getWeek());
                income.setAmmount(contruction);
                income.setTimeCreated( new Timestamp(System.currentTimeMillis()));
                m_logger.info("contruction " + incomeItem.getIncomeItem() + " " + contruction);
                ChurIncomeDS.getInstance().put(new ChurIncomeDataHolder(income));
                
            }   else {
                m_logger.error("Income item not found for tithe", new Exception());
            }
        }

        if ( other > 0.0 ) {
            
            ChurIncomeItem incomeItem = ChurIncomeItemDS.getInstance().getBySiteIdToIncomeItem(data.getSiteId(), "other");
            if ( incomeItem != null) { 
                ChurIncome income = new ChurIncome();
                income.setSiteId(data.getSiteId());
                income.setChurMemberId(member.getId());
                income.setIncomeItemId(incomeItem.getId());
                income.setYear(year);
                income.setWeek(data.getWeek());
                income.setAmmount(other);
                income.setTimeCreated( new Timestamp(System.currentTimeMillis()));
                m_logger.info("Tithe " + incomeItem.getIncomeItem() + " " + other);
                ChurIncomeDS.getInstance().put(new ChurIncomeDataHolder(income));
            }   else {
                m_logger.error("Income item not found for tithe", new Exception());
            }
        }
    }

    public  void beforeUpdate(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject) throws Exception{
		m_logger.debug("#ChurIncomeAddAction#xtent.beforeUpdate. id=" + holderObject.getId());		
		beforeUpdate(request, response, holderObject.getDataObject());
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject) throws Exception{
		m_logger.debug("#ChurIncomeAddAction#xtent.afterUpdate. id=" + holderObject.getId());		
		afterUpdate(request, response, holderObject.getDataObject());
    }

    public  void beforeDelete(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject) throws Exception{
		m_logger.debug("#ChurIncomeAddAction#xtent.beforeDelete. id=" + holderObject.getId());		
		beforeDelete(request, response, holderObject.getDataObject());
    }

    public  void afterDelete(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject) throws Exception{
		m_logger.debug("#ChurIncomeAddAction#xtent.afterDelete. id=" + holderObject.getId());		
		afterDelete(request, response, holderObject.getDataObject());
    }

	//========================================================================================
	// Propprietary Commands For This Action
	//========================================================================================

    public void processCommand(HttpServletRequest request, HttpServletResponse response, String cmd) throws Exception{
		if ( cmd == null || cmd.length() == 0) return;
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

    public String getErrorPage()          {return DefaultPageForwardManager.getInstance().getPageForwardTo("ChurIncomeAdd", "action_error_forward_page", "chur_income_add_home");}
    public String getWarningPage()        {return DefaultPageForwardManager.getInstance().getPageForwardTo("ChurIncomeAdd", "action_warning_forward_page", "chur_income_add_home");}
    public String getAfterAddPage()       {return DefaultPageForwardManager.getInstance().getPageForwardTo("ChurIncomeAdd", "action_normal_add_forward_page", "chur_income_add_form");}
    public String getAfterEditPage()      {return DefaultPageForwardManager.getInstance().getPageForwardTo("ChurIncomeAdd", "action_normal_edit_forward_page", "chur_income_add_form");}
    public String getAfterEditFieldPage() {return DefaultPageForwardManager.getInstance().getPageForwardTo("ChurIncomeAdd", "action_normal_editfield_forward_page", "chur_income_add_form");}
    public String getAfterDeletePage()    {return DefaultPageForwardManager.getInstance().getPageForwardTo("ChurIncomeAdd", "action_normal_delete_forward_page", "chur_income_add_home");}
    public String getDefaultPage()        {return DefaultPageForwardManager.getInstance().getPageForwardTo("ChurIncomeAdd", "action_default_forward_page", "chur_income_add_home");}
    public String getConfirmPage()        {return DefaultPageForwardManager.getInstance().getPageForwardTo(((ChurMemberAction)m_action).getActionGroupName(), "confirm_page" , "confirm_required");}

   private ChurIncomeAddDS m_ds = ChurIncomeAddDS.getInstance();
   private static Logger m_logger = Logger.getLogger( ChurIncomeAddActionExtent.class);
    
}
