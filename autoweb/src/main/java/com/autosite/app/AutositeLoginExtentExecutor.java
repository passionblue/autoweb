package com.autosite.app;

import javax.servlet.http.HttpServletRequest;

import com.autosite.db.AutositeUser;

public interface AutositeLoginExtentExecutor {

    void execute(HttpServletRequest request, AutositeUser user) throws Exception;
}
