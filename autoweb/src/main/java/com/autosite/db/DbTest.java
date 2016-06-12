package com.autosite.db;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class DbTest {

    
    public static void main(String[] args) {
        contentTest();
    }
    public static void contentTest() {
        ContentDAO dao = new ContentDAO();

        
        List list = dao.findBySiteId(new Long(29));
        System.out.println(list.size());
    }

    public static void pageTest() {
        PageDAO dao = new PageDAO();
        Page page = new Page();
        page.setCreatedTime(new Timestamp(System.currentTimeMillis()));
        page.setPageName("test");
        page.setPageMenuTitle("Test");
        
        dao.save(page);
        List all = dao.findAll();
        System.out.println(all);
    }

    public static void siteTest() {
        
        Site site = new Site();
        site.setSiteUrl("www.uxsx5.com");
        site.setCreatedTime(new Timestamp(System.currentTimeMillis()));
        SiteDAO dao = new SiteDAO();
        
        dao.save(site);
        
        List all = dao.findAll();
        System.out.println(all);
    }
}
