package com.autosite.service;

import java.util.Map;

import com.autosite.db.AutositeUser;
import com.autosite.db.Site;
import com.jtrend.service.UserService;

public interface AutositeUserService extends UserService {
    
    AutositeUser createUser(Site site, String email,String password) throws Exception;
    AutositeUser createUser(Site site, String username, String email, String password, int userType, boolean allowPagelessSession) throws Exception;
    AutositeUser createUser(Site site, Map parameters) throws Exception;
    AutositeUser createUser(Site site, Object parameterObj) throws Exception;

    AutositeUser updateUser(AutositeUser user) throws Exception;
    AutositeUser deleteUser(AutositeUser user) throws Exception;
    
}
