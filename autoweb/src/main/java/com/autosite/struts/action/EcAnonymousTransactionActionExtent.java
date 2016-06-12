package com.autosite.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.EcAnonymousTransaction;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.EcAnonymousTransactionDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.EcAnonymousTransactionForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;

public class EcAnonymousTransactionActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcAnonymousTransactionAction#xtent.beforeAdd");		
        EcAnonymousTransaction _EcAnonymousTransaction = (EcAnonymousTransaction)baseDbOject;

        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#EcAnonymousTransactionAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        EcAnonymousTransaction _EcAnonymousTransaction = (EcAnonymousTransaction)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcAnonymousTransactionAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        EcAnonymousTransaction _EcAnonymousTransaction = (EcAnonymousTransaction)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcAnonymousTransactionAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        EcAnonymousTransaction _EcAnonymousTransaction = (EcAnonymousTransaction)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcAnonymousTransactionAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        EcAnonymousTransaction _EcAnonymousTransaction = (EcAnonymousTransaction)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcAnonymousTransactionAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        EcAnonymousTransaction _EcAnonymousTransaction = (EcAnonymousTransaction)baseDbOject;
    }

	private EcAnonymousTransactionDS m_ds = EcAnonymousTransactionDS.getInstance();
    private static Logger m_logger = Logger.getLogger( EcAnonymousTransactionActionExtent.class);
    
}
