package com.autosite.session;

import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autosite.db.AutositeRemoteDevice;
import com.autosite.db.AutositeSessionContextEntity;
import com.autosite.db.AutositeUser;
import com.autosite.db.Site;
import com.autosite.ds.AutositeRemoteDeviceDS;
import com.autosite.ds.AutositeSessionContextEntityDS;
import com.autosite.ds.AutositeUserDS;
import com.autosite.ds.SiteDS;
import com.jtrend.session.SessionContext;
import com.jtrend.util.TimeNow;
import com.jtrend.util.WebUtil;

public class AutositeSessionContextUtil {
    
    private static Logger m_logger = LoggerFactory.getLogger(AutositeSessionContextUtil.class);
    
    public static SessionContext getSessionContextFromHttpSession(HttpSession session)
    {
        SessionContext ret = (SessionContext) session.getAttribute(SessionContext.getSessionKey());
        if ( ret == null) {
            
            Site site = (Site) session.getAttribute("k_site");
            
            ret = AutositeSessionContext.create(site == null?"XXXXX":site.getId()+"-" + site.getSiteUrl(), session);
            
            session.setAttribute(SessionContext.getSessionKey(), ret);
            SessionContext.registerSessionContext(session, ret);
            
            m_logger.debug(">>>>> AutositeSessionContext NEWLY created at " + ret.getCreatedTime() + ", serial=" + ret.getSerial());
            
            ret.setServer(site.getSiteUrl());
            
        } else {
            m_logger.debug("SessionContext found. Created at " + ret.getCreatedTime() + " Last=" + new Date(ret.getLastAccess())  + ",User=" + ret.getUsername() + "," + ret);
        }

        return ret;
    } 
    
    
    public static void persistSessionContext(Site site, AutositeSessionContext sessionContext){

        AutositeRemoteDevice registeredDevice = AutositeRemoteDeviceDS.getInstance().getBySiteIdToDeviceId(site.getId(), sessionContext.getSourceDeviceId());
        
        AutositeSessionContextEntity contextEntity = AutositeSessionContextEntityDS.getInstance().getObjectBySerial(sessionContext.getSerial());
        
        boolean createdNow = false;
        if( contextEntity == null) {

            contextEntity = new AutositeSessionContextEntity();
            
            contextEntity.setSerial(sessionContext.getSerial());
            contextEntity.setTimeCreated(new TimeNow());
            contextEntity.setRemoteIp(sessionContext.getRemoteIp());
            contextEntity.setSiteId(sessionContext.getUserObject().getSiteId());
            contextEntity.setRemoteIp(sessionContext.getRemoteIp());
            contextEntity.setRemoteDeviceId(registeredDevice == null?0:registeredDevice.getId());
            createdNow = true;
        }
        
        contextEntity.setSessionType(sessionContext.getSessionType());

        if ( sessionContext.isLogin()) {
            contextEntity.setIsLogin(WebUtil.AUTOSITE_TRUE);

            if( sessionContext.getUserObject() != null) {
                contextEntity.setLoginUserId(sessionContext.getUserObject().getId());
            }
            
            contextEntity.setTimeLogin(new Timestamp(sessionContext.getLoginTime().getTime()));
            contextEntity.setTimeUpdated(new TimeNow());
        }            

        if (createdNow){
            AutositeSessionContextEntityDS.getInstance().put(contextEntity);
        } else {
            AutositeSessionContextEntityDS.getInstance().update(contextEntity);
        }
    }
    
    
    static public SessionContext findBySerial(String serial){
        
        SessionContext context =  SessionContext.findBySerial(serial);
        
        if( context != null) {
            return context;
        }

        m_logger.debug("Context not found in memory. load from database " + serial);
        
        AutositeSessionContextEntity entity = AutositeSessionContextEntityDS.getInstance().getObjectBySerial(serial);
        AutositeSessionContext sessionContext = AutositeSessionContext.create(true, serial);

        // Somehow this session is not registered in the server. So return return.
        if ( entity == null) { 
            m_logger.error("Session Entity not found either from DB. " + serial);
            return null;
        }
        
        sessionContext.setRemoteIp(entity.getRemoteIp());

        if ( WebUtil.isTrue(entity.getIsLogin())){

            m_logger.debug("This context has logged in. loading login information " + serial);
            AutositeUser user = AutositeUserDS.getInstance().getById(entity.getLoginUserId());
            Site site = SiteDS.getInstance().getById(user.getSiteId());

            sessionContext.setLogin(WebUtil.isTrue(entity.getIsLogin()));
            sessionContext.setUsername(user.getUsername());
            sessionContext.setUserObject(user);
            sessionContext.setServer(site.getSiteUrl());
            
            sessionContext.setDefaultSessionType(entity.getSessionType());
            sessionContext.setSelectedSessionType(entity.getSessionType());
            
            AutositeRemoteDevice device = AutositeRemoteDeviceDS.getInstance().getById(entity.getRemoteDeviceId());
            
            if ( device != null) {
                sessionContext.setSourceDeviceId(device.getDeviceId());
                sessionContext.setRemoteDevice(device);
            }
            
            m_logger.debug("This context has logged in. loading login information " + user.getUsername() + " created at " + entity.getTimeCreated() + " logged in at " + entity.getTimeLogin() );
        }
        return sessionContext;
    }
    
    
    public static AutositeSessionContext createInvalidSessionContext(int status) {
        
        AutositeSessionContext context = new AutositeSessionContext("*INVALID*");
        context.setSessionStatus(status);
        
        return context;
    }
    
}
