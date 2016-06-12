package com.autosite.ds;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.autosite.db.ForumComment;
import com.autosite.db.ForumCommentDAO;
import com.jtrend.service.DomainStore;

public class ForumCommentDS extends AbstractDS implements DomainStore {





    private static Logger m_logger = Logger.getLogger(ForumCommentDS.class);
    private static ForumCommentDS m_ForumCommentDS = null;

    public static synchronized ForumCommentDS getInstance() {
        if (m_ForumCommentDS == null) {
            m_ForumCommentDS = new ForumCommentDS();
        }
        return m_ForumCommentDS;
    }

    protected ForumCommentDS() {
        m_dao = new ForumCommentDAO();
        m_idToMap = new HashMap();

        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e);
        }
    }


    public ForumComment getById(Long id) {
        return (ForumComment) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        ForumComment o = (ForumComment)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            m_logger.debug("ForumComment removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            m_logger.debug("ForumComment added to DS " + o.getId());
        }

    }



    

    public static void main(String[] args) throws Exception {

        ForumCommentDS ds = new ForumCommentDS();
        ForumComment obj = ds.getById((long)1);
        System.out.println(obj);

    }
}
