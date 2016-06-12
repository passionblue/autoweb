package com.autosite.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.AutositeAccount;
import com.autosite.db.SiteRegStart;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.AutositeAccountDS;
import com.autosite.ds.SiteRegStartDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.session.SiteRegStore;
import com.autosite.struts.form.SiteRegStartForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;

public class SiteRegStartActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception {
        m_logger.debug("#SiteRegStartAction#xtent.beforeAdd");
        SiteRegStart _SiteRegStart = (SiteRegStart) baseDbOject;
        HttpSession session = request.getSession();

        if (WebUtil.isNull(_SiteRegStart.getTargetDomain())) {
            m_logger.debug("targetDomain is missing in this requres " + SiteRegStartDS.objectToString(_SiteRegStart));
            throw new ActionExtentException("Target Domain is missing.", "site_reg_start");
        }

        SiteRegStore siteRegStore = (SiteRegStore) session.getAttribute(SiteRegStore.getSessionKey());
        if (siteRegStore == null) {
            m_logger.info("Site Reg Store is missing in session. Forwarding the site to start page");
            throw new ActionExtentException("Target Domain is missing.", "site_reg_start");
        }

        String commonUrl = SiteDS.getCommonUrl(siteRegStore.getDomain());
        
        Site site = SiteDS.getInstance().getSiteByUrl(commonUrl);

        if (site != null) {
            long accountId = site.getAccountId();
            if (AutositeAccountDS.getInstance().getById(accountId) != null) {
                m_logger.info("Site " + commonUrl + " already associated to account " + accountId);
                throw new ActionExtentException("This url already registered.", "site_reg_start");
            }

            AutositeAccount acc = AutositeAccountDS.getInstance().getObjectBySiteId(site.getId());
            if ( acc != null) {
                m_logger.info("Site " + commonUrl + " already associated to account " + accountId);
                throw new ActionExtentException("This url already registered.", "site_reg_start");
            }
        }
        
        siteRegStore.setStart(_SiteRegStart);

    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception {
        m_logger.debug("#SiteRegStartAction#xtent.afterAdd. id=" + baseDbOject.getId());
        SiteRegStart _SiteRegStart = (SiteRegStart) baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception {
        m_logger.debug("#SiteRegStartAction#xtent.beforeUpdate. id=" + baseDbOject.getId());
        SiteRegStart _SiteRegStart = (SiteRegStart) baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception {
        m_logger.debug("#SiteRegStartAction#xtent.afterUpdate. id=" + baseDbOject.getId());
        SiteRegStart _SiteRegStart = (SiteRegStart) baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception {
        m_logger.debug("#SiteRegStartAction#xtent.beforeDelete. id=" + baseDbOject.getId());
        SiteRegStart _SiteRegStart = (SiteRegStart) baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception {
        m_logger.debug("#SiteRegStartAction#xtent.afterDelete. id=" + baseDbOject.getId());
        SiteRegStart _SiteRegStart = (SiteRegStart) baseDbOject;
    }

    private SiteRegStartDS m_ds = SiteRegStartDS.getInstance();
    private static Logger m_logger = Logger.getLogger(SiteRegStartActionExtent.class);

}
