package com.autosite.service;

import com.autosite.db.AutositeAccount;
import com.autosite.db.AutositeUser;
import com.autosite.db.Site;

public interface AutositeAccountService {

    AutositeAccount createAccountFor(Site site, String companyTitie, AutositeUser user) throws Exception;
}
