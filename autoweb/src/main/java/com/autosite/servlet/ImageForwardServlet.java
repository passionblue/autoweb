package com.autosite.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.autosite.AutositeGlobals;
import com.autosite.app.AutoSiteInitiator;
import com.autosite.db.AutositeUser;
import com.autosite.db.Page;
import com.autosite.db.PageConfig;
import com.autosite.db.Site;
import com.autosite.db.SiteConfig;
import com.autosite.db.StyleConfig;
import com.autosite.ds.LinkStyleConfigDS;
import com.autosite.ds.PageConfigDS;
import com.autosite.ds.PageDS;
import com.autosite.ds.PanelDS;
import com.autosite.ds.SiteConfigDS;
import com.autosite.ds.SiteDS;
import com.autosite.ds.StyleConfigDS;
import com.autosite.session.AutositeSessionContext;
import com.jtrend.session.SessionContext;
import com.jtrend.util.WebUtil;

public class ImageForwardServlet  extends AbstractAutositeServlet {
    
    protected boolean m_debug = AutositeGlobals.m_debug;

    public ImageForwardServlet() 
    {
        new AutoSiteInitiator().init();
        
        siteDS = SiteDS.getInstance();
        siteConfigDS = SiteConfigDS.getInstance();
        panelDS = PanelDS.getInstance();
        linkStyleConfigDS = LinkStyleConfigDS.getInstance();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {

        HttpSession session = request.getSession();
        
        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        String url = request.getRequestURI();
        m_logger.debug(url);
        String parts[] = url.split("/");

        m_logger.debug("num parts=" + parts.length);
        
        if ( parts.length < 1 ) {
            m_logger.info("length of parts is too short. returning as error");
        }
        
        if ( parts[1].equals("mimgs") || parts[1].equals("simgs") || parts[1].equals("pimgs")  ){

            String fullPath ="/image/";
            
            if (parts[1].equals("simgs")){
                fullPath="/s" + site.getId() + "/img";
            } else if (parts[1].equals("pimgs")){
                fullPath="/pub_img";
            } else {
                AutositeSessionContext ctx = (AutositeSessionContext) session.getAttribute(SessionContext.getSessionKey());
                
                if (ctx != null) {
                    AutositeUser user = ctx.getUserObject();
                    fullPath="/s" + site.getId() + "/u" + user.getId() + "/img";
                } else {
                    fullPath="/s" + site.getId() + "/img";
                }
            }
            
            fullPath = fullPath +"/" + parts[2];
            m_logger.info("Full path = " + fullPath);

            request.getRequestDispatcher("org.apache.catalina.servlets.DefaultServlet").forward(request, response);
            
        }
    
    }
    private static Logger m_logger = Logger.getLogger(ImageForwardServlet.class);
    
    
    protected SiteDS siteDS;
    protected SiteConfigDS siteConfigDS;
    protected PanelDS panelDS;
    protected LinkStyleConfigDS linkStyleConfigDS;
}
