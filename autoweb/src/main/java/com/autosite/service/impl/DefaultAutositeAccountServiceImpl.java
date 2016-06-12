package com.autosite.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autosite.db.AutositeAccount;
import com.autosite.db.AutositeUser;
import com.autosite.db.Site;
import com.autosite.ds.AutositeAccountDS;
import com.autosite.ds.SiteDS;
import com.autosite.service.AutositeAccountService;
import com.autosite.util.SiteAccountManager;
import com.jtrend.util.TimeNow;

public class DefaultAutositeAccountServiceImpl implements AutositeAccountService {

    private static Logger m_logger = LoggerFactory.getLogger(DefaultAutositeAccountServiceImpl.class);
    
    @Override
    public AutositeAccount createAccountFor(Site newSite, String companyTitie, AutositeUser user) throws Exception {

        AutositeAccount account = AutositeAccountDS.getInstance().getById(newSite.getAccountId());
        
        if ( account != null) {
            m_logger.error("Account exists for the site " + newSite.getSiteUrl() + " id=" + newSite.getId() + " account " + account.getAccountNum());
            throw new Exception("Account exists for the site " + newSite.getSiteUrl() + " id=" + newSite.getId());
        }

        AutositeAccount newAccount = new AutositeAccount();

        newAccount.setAccountNum(SiteAccountManager.createAccountNum());
        newAccount.setEnabled(1);
        newAccount.setSiteId(newSite.getId());
        newAccount.setTimeCreated(new TimeNow());
        newAccount.setCompany(companyTitie == null? newSite.getSiteUrl() : companyTitie);
        newAccount.setEmail(user.getEmail());
        newAccount.setAccountOwnerId(user.getId());

        //TODO
        newAccount.setFirstName("TBD");
        newAccount.setLastName("TBD");
        newAccount.setPhone("TBD");

        AutositeAccountDS.getInstance().put(newAccount);
        
        // Update site with AccountId
        newSite.setAccountId(newAccount.getId());
        SiteDS.getInstance().update(newSite);
        
        return account;
    }

}
