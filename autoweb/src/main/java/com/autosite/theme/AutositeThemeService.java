package com.autosite.theme;

import com.autosite.db.Site;
import com.autosite.db.ThemeAggregator;

public interface AutositeThemeService {
    
    void installTheme(Site site, ThemeAggregator themeAggregator);
    void installTheme(Site site, ThemeAggregator themeAggregator, boolean initialSetup);
    void uninstallTheme(Site site, ThemeAggregator themeAggregator);

}
