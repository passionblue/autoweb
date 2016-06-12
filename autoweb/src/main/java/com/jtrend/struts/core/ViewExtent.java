package com.jtrend.struts.core;

public interface ViewExtent {

    String getSiteName();
    String getAlternateFor(String alias, String server); // Being use for home at the moment. specified in the filw using "."
    String getAlternateUrl(String alias, String server);
}
