package com.autosite.struts.action;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.SweepWorldcup;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.SweepWorldcupDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.SweepWorldcupForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.util.SweepWorldcupParamUtil;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;

public class SweepWorldcupActionExtent extends AutositeActionExtent {

    public void beforeMain(HttpServletRequest request, HttpServletResponse response) throws Exception{
        checkExpire();
    }
    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#SweepWorldcupAction#xtent.beforeAdd");		
        SweepWorldcup _SweepWorldcup = (SweepWorldcup)baseDbOject;
        checkExpire();

        setTeamFields(_SweepWorldcup, request);
        checkFinalScores(_SweepWorldcup);
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#SweepWorldcupAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        SweepWorldcup _SweepWorldcup = (SweepWorldcup)baseDbOject;
        request.getSession().setAttribute("k_top_text", "New Sweepstake was successfully entered.");
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#SweepWorldcupAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        SweepWorldcup _SweepWorldcup = (SweepWorldcup)baseDbOject;
        checkExpire();
        
        setTeamFields(_SweepWorldcup, request);
        checkFinalScores(_SweepWorldcup);
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#SweepWorldcupAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        SweepWorldcup _SweepWorldcup = (SweepWorldcup)baseDbOject;
        
        request.getSession().setAttribute("k_top_text", "Changes successfully saved");
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#SweepWorldcupAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
		SweepWorldcup _SweepWorldcup = (SweepWorldcup)baseDbOject;
		checkExpire();
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#SweepWorldcupAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        SweepWorldcup _SweepWorldcup = (SweepWorldcup)baseDbOject;
    }

    private void setTeamFields(SweepWorldcup sweepWorldcup, HttpServletRequest request) throws Exception{

        String quarterVal = SweepWorldcupParamUtil.convertToString(request, SweepWorldcupParamUtil.FIELD_QUARTER);
        String semiVal = SweepWorldcupParamUtil.convertToString(request, SweepWorldcupParamUtil.FIELD_SEMI);
        String finalVal = SweepWorldcupParamUtil.convertToString(request, SweepWorldcupParamUtil.FIELD_FINAL);
        String champVal = SweepWorldcupParamUtil.convertToString(request, SweepWorldcupParamUtil.FIELD_CHAMP);
        
        boolean incompleted = false;
        if (WebUtil.isNull(quarterVal)){
            incompleted = true;
        } else {
            sweepWorldcup.setQuarterFinalTeams(quarterVal);
        }
        
        if (WebUtil.isNull(semiVal)){
            incompleted = true;
        } else {
            sweepWorldcup.setSemiFinalTeams(semiVal);
        }

        if (WebUtil.isNull(finalVal)){
            incompleted = true;
        } else {
            sweepWorldcup.setFinalTeams(finalVal);
        }

        if (WebUtil.isNull(champVal)){
            incompleted = true;
        } else {
            sweepWorldcup.setChampion(champVal);
        }
        
    }

    private void checkExpire() throws Exception{

        Calendar cal = GregorianCalendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        
        if ( month >= Calendar.JUNE && day >= 12 )
            throw new ActionExtentException("Worldcup Sweepstakes has expired.");
        
    }

    private static void checkFinalScores(SweepWorldcup sweepWorldcup){
        if (sweepWorldcup.getFinalScoreLose() > sweepWorldcup.getFinalScoreWin()) {
            int temp = sweepWorldcup.getFinalScoreLose();
            sweepWorldcup.setFinalScoreLose(sweepWorldcup.getFinalScoreWin());
            sweepWorldcup.setFinalScoreWin(temp);
        }
        
    }
    
    
	private SweepWorldcupDS m_ds = SweepWorldcupDS.getInstance();
    private static Logger m_logger = Logger.getLogger( SweepWorldcupActionExtent.class);
    
}
