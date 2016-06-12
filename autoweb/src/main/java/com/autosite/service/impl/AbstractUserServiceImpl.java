package com.autosite.service.impl;

import java.util.Map;

import org.apache.commons.lang.NotImplementedException;

import com.autosite.db.AutositeUser;
import com.autosite.db.Site;
import com.autosite.service.AutositeUserService;

abstract class AbstractUserServiceImpl implements AutositeUserService {

    public AutositeUser createUser(Site site, Map parameters) throws Exception {
        throw new NotImplementedException("");
    }

    public AutositeUser createUser(Site site, Object parameterObj) throws Exception {
        throw new NotImplementedException("");
    }

    public AutositeUser updateUser(AutositeUser user) throws Exception {
        throw new NotImplementedException("");
    }

    public AutositeUser deleteUser(AutositeUser user) throws Exception {
        throw new NotImplementedException("");
    }

    @Override
    public AutositeUser createUser(Site site, String email, String password) throws Exception {
        throw new NotImplementedException("");
    }

    @Override
    public AutositeUser createUser(Site site, String username, String email, String password, int userType, boolean allowPagelessSession) throws Exception {
        throw new NotImplementedException("");
    }
}
