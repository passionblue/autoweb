package com.autosite.theme;

import com.autosite.db.Site;

public interface AutositeTheme {

    void installTheme(Site site);
    
    void uninstallTheme(Site site);
    
}



