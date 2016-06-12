package com.autosite.struts.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.autosite.db.BaseAutositeDataObject;
import com.autosite.db.WhoisData;
import com.autosite.ds.WhoisDataDS;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.util.PollUtil;
import com.autosite.util.WhoisUtil;
import com.jtrend.util.WebParamUtil;
import com.jtrend.util.WebUtil;

public class WhoisDataActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#WhoisDataAction#xtent.beforeAdd");		
        WhoisData _WhoisData = (WhoisData)baseDbOject;

        if ( WebUtil.isNull(_WhoisData.getIp())){
            m_logger.debug("ip is missing in this requres " + WhoisDataDS.objectToString(_WhoisData));
            throw new ActionExtentException("Ip is missing.", "");
        }
        if ( WebUtil.isNull(_WhoisData.getServer())){
            m_logger.debug("server is missing in this requres " + WhoisDataDS.objectToString(_WhoisData));
            throw new ActionExtentException("Server is missing.", "");
        }

        
        List oldWhois = WhoisDataDS.getInstance().getBySiteIdIpList(_WhoisData.getSiteId(),_WhoisData.getIp());
        
        if (oldWhois.size() == 0 || WebUtil.isTrue(_WhoisData.getForceRequest())){
            String data = WhoisUtil.getRawWhois(_WhoisData.getIp(), _WhoisData.getServer(), 43,0);
            _WhoisData.setWhoisData(data);
            _WhoisData.setTimeExpired(PollUtil.getExpiringTime(_WhoisData.getTimeCreated(),365));
            
        } else {
            _WhoisData.setSkipPersist(true);
        }
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#WhoisDataAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        WhoisData _WhoisData = (WhoisData)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#WhoisDataAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        WhoisData _WhoisData = (WhoisData)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#WhoisDataAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        WhoisData _WhoisData = (WhoisData)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#WhoisDataAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        WhoisData _WhoisData = (WhoisData)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#WhoisDataAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        WhoisData _WhoisData = (WhoisData)baseDbOject;
    }

    public Map processAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map ret = new HashMap();
        return ret;
    }

	private WhoisDataDS m_ds = WhoisDataDS.getInstance();
    private static Logger m_logger = Logger.getLogger( WhoisDataActionExtent.class);
    
}
