package com.autosite.struts.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.autosite.db.AutositeUser;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.db.SweepInvitation;
import com.autosite.db.SweepUserConfig;
import com.autosite.ds.SweepInvitationDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.util.SweepUtil;
import com.autosite.util.UserUtil;
import com.jtrend.util.WebUtil;

public class SweepInvitationActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#SweepInvitationAction#xtent.beforeAdd");		
        SweepInvitation _SweepInvitation = (SweepInvitation)baseDbOject;

        if ( WebUtil.isNull(_SweepInvitation.getEmail())){
            m_logger.debug("email is missing in this requres " + SweepInvitationDS.objectToString(_SweepInvitation));
            throw new ActionExtentException("Email is missing.", "");
        }
        
        AutositeUser user = UserUtil.getUserFromSession(request.getSession());
        
        
        if ( _SweepInvitation.getEmail().equals(user.getEmail())){
            throw new ActionExtentException("The email is being used for the registration of this account. Please check the email and try again", "");
        }

        if (user == null) {
            m_logger.error("Can't get user object");
            throw new ActionExtentException("Internal error occurred.", "");
        }
        
        List list = SweepInvitationDS.getInstance().getByUserId(user.getId());
        
        if (list.size() >= SweepUtil.SWEEP_WORLDCUP_MAX_INVITATION){
            m_logger.error("Can't get user object");
            throw new ActionExtentException("Already max number of invitation have been sent.", "");
        }
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#SweepInvitationAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        SweepInvitation _SweepInvitation = (SweepInvitation)baseDbOject;
        
        AutositeUser autoUser = UserUtil.getUserFromSession(request.getSession());
        
        List invs = SweepInvitationDS.getInstance().getByUserId(autoUser.getId());

        SweepUserConfig config = SweepUtil.getOrCreateSweepUserConfig(autoUser);
        
        int oldMax = config.getMaxSweepAllowed();
        config.setMaxSweepAllowed(invs.size() + 1);
        m_logger.info("Max set from " + oldMax + " to " + config.getMaxSweepAllowed());
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#SweepInvitationAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        SweepInvitation _SweepInvitation = (SweepInvitation)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#SweepInvitationAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        SweepInvitation _SweepInvitation = (SweepInvitation)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#SweepInvitationAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        SweepInvitation _SweepInvitation = (SweepInvitation)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#SweepInvitationAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        SweepInvitation _SweepInvitation = (SweepInvitation)baseDbOject;
    }

	private SweepInvitationDS m_ds = SweepInvitationDS.getInstance();
    private static Logger m_logger = Logger.getLogger( SweepInvitationActionExtent.class);
    
}
