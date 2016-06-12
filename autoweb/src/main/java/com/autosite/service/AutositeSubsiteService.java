package com.autosite.service;

import com.autosite.db.Site;

public interface AutositeSubsiteService {

   public Site createSubSite(Site baseSite, String subname) throws Exception;
   
   public boolean isAvailable(String subname);
   
}
