/*
 * Created on Dec 23, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.seox.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

public class DomainNameUtil {

    private static Set m_singleDomains = new HashSet();
    private static Set m_countryDomains = new HashSet();
    
    static
    {
        m_singleDomains.add("com");
        m_singleDomains.add("org");
        m_singleDomains.add("info");
        m_singleDomains.add("net");
        m_singleDomains.add("biz");
        m_singleDomains.add("tv");
        m_singleDomains.add("ws");
        m_singleDomains.add("name");
        m_singleDomains.add("mobi");
        m_singleDomains.add("gov");
        m_singleDomains.add("us");
    }
    public static String getRootDomain(String string) {

        URL url = null;
        String host = null;
        try {
            url = new URL(string);
            host = url.getHost();
        }
        catch (MalformedURLException e) {
            m_logger.error(e);
            
            if (string.endsWith("/")) {
                host = string.substring(0, string.length()-1);
            } else {
                host = string;
            }
        }

        int pos = host.lastIndexOf(".");
        
        String dom = host.substring(pos+1);

        if ( m_singleDomains.contains(dom) ) {
            pos = host.lastIndexOf(".", pos-1);
            return host.substring(pos +1).toLowerCase();
        } else {
            pos = host.lastIndexOf(".", pos-1);
            pos = host.lastIndexOf(".", pos-1);
            return host.substring(pos +1).toLowerCase();
        }
    }
    private static Logger m_logger = Logger.getLogger(DomainNameUtil.class);
}
