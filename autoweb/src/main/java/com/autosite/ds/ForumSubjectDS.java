package com.autosite.ds;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.autosite.db.ForumSubject;
import com.autosite.db.ForumSubjectDAO;
import com.jtrend.service.DomainStore;

public class ForumSubjectDS extends AbstractDS implements DomainStore {


    protected Map m_SubjectToMap;



    private static Logger m_logger = Logger.getLogger(ForumSubjectDS.class);
    private static ForumSubjectDS m_ForumSubjectDS = null;

    public static synchronized ForumSubjectDS getInstance() {
        if (m_ForumSubjectDS == null) {
            m_ForumSubjectDS = new ForumSubjectDS();
        }
        return m_ForumSubjectDS;
    }

    public static synchronized ForumSubjectDS getInstance(long id) {
        ForumSubjectDS ret = (ForumSubjectDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new ForumSubjectDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_ForumSubjectDS;
    }



    private static Map m_dsMap = new HashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {
            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((ForumSubjectDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
            
        } else {
            super.loadFromDB();
        }
    }


    protected ForumSubjectDS() {
        m_dao = new ForumSubjectDAO();
        m_idToMap = new HashMap();

        m_SubjectToMap = new HashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected ForumSubjectDS(long id) {
		m_loadById = id;
        m_dao = new ForumSubjectDAO();
        m_idToMap = new HashMap();

        m_SubjectToMap = new HashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }



    public ForumSubject getById(Long id) {
        return (ForumSubject) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        ForumSubject o = (ForumSubject)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            m_logger.debug("ForumSubject removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            m_logger.debug("ForumSubject added to DS " + o.getId());
        }

		updateSubjectMap(obj, del);
    }



    

    public ForumSubject getBySubject(String keySubject) {
		return (ForumSubject)m_SubjectToMap.get(keySubject);
    }

	private void updateSubjectMap(Object obj, boolean del) {
        ForumSubject o = (ForumSubject)obj;
		String _Subject = 	o.getSubject();

        if (del) {
            // delete from SubjectToMap

			if (m_SubjectToMap.containsKey(_Subject)){
                m_SubjectToMap.remove(_Subject);
            	m_logger.debug("ForumSubject removed from SubjectToMap" + o.getId() + " for [" + _Subject+ "]");
			} else {
            	m_logger.debug("ForumSubject removed from SubjectToMap" + o.getId() + " for [" + _Subject+ "]");
			} 
        }
        else {
            
			if (m_SubjectToMap.containsKey(_Subject)){
            	m_logger.debug("ForumSubject repalced SubjectToMap" + o.getId() + " for [" + _Subject+ "]");
			} else {
            	m_logger.debug("ForumSubject added to SubjectToMap" + o.getId() + " for [" + _Subject+ "]");
			} 
            m_SubjectToMap.put(_Subject, o);
        }
	}

    public static void main(String[] args) throws Exception {

        ForumSubjectDS ds = new ForumSubjectDS();
        ForumSubject obj = ds.getById((long)1);
        System.out.println(obj);

    }
}
