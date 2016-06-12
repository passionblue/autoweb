package com.autosite.ds;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.autosite.db.ForumPost;
import com.autosite.db.ForumPostDAO;
import com.jtrend.service.DomainStore;

public class ForumPostDS extends AbstractDS implements DomainStore {

    protected Map m_SubjectIdToMap;
    protected Map m_UserIdToMap;




    private static Logger m_logger = Logger.getLogger(ForumPostDS.class);
    private static ForumPostDS m_ForumPostDS = null;

    public static synchronized ForumPostDS getInstance() {
        if (m_ForumPostDS == null) {
            m_ForumPostDS = new ForumPostDS();
        }
        return m_ForumPostDS;
    }

    public static synchronized ForumPostDS getInstance(long id) {
        ForumPostDS ret = (ForumPostDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new ForumPostDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_ForumPostDS;
    }



    private static Map m_dsMap = new HashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {
            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((ForumPostDAO)m_dao).findByUserId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
            
        } else {
            super.loadFromDB();
        }
    }


    protected ForumPostDS() {
        m_dao = new ForumPostDAO();
        m_idToMap = new HashMap();

        m_SubjectIdToMap = new HashMap();
        m_UserIdToMap = new HashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected ForumPostDS(long id) {
		m_loadById = id;
        m_dao = new ForumPostDAO();
        m_idToMap = new HashMap();

        m_SubjectIdToMap = new HashMap();
        m_UserIdToMap = new HashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }



    public ForumPost getById(Long id) {
        return (ForumPost) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        ForumPost o = (ForumPost)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            m_logger.debug("ForumPost removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            m_logger.debug("ForumPost added to DS " + o.getId());
        }

		updateSubjectIdMap(obj, del);
		updateUserIdMap(obj, del);
    }


    public List getBySubjectId(long SubjectId) {
        
        Long _SubjectId  = new Long(SubjectId);
        if (m_SubjectIdToMap.containsKey(_SubjectId)) {
            return new ArrayList( ((Map)m_SubjectIdToMap.get(_SubjectId)).values() );
        } else {
            return new ArrayList();
        }
    }

	private void updateSubjectIdMap(Object obj, boolean del) {
        ForumPost o = (ForumPost)obj;

        if (del) {

            // delete from SubjectIdToMap
            Map map  = (Map) m_SubjectIdToMap.get(new Long(o.getSubjectId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            m_logger.debug("ForumPost removed from SubjectIdToMap" + o.getId() + " from " + o.getSubjectId());
        }
        else {
            
            // add to SubjectIdToMap
            Map map  = (Map) m_SubjectIdToMap.get(new Long(o.getSubjectId()));
            if ( map == null ) {
                map = new TreeMap();
                m_SubjectIdToMap.put(new Long(o.getSubjectId()), map);
            	m_logger.debug("created new   SubjectIdToMap for " + o.getSubjectId());

            }
            map.put(new Long(o.getId()), o);            
            m_logger.debug("ForumPost added to SubjectIdToMap " + o.getId() + " to " + o.getSubjectId());
        }
	}

    public List getByUserId(long UserId) {
        
        Long _UserId  = new Long(UserId);
        if (m_UserIdToMap.containsKey(_UserId)) {
            return new ArrayList( ((Map)m_UserIdToMap.get(_UserId)).values() );
        } else {
            return new ArrayList();
        }
    }

	private void updateUserIdMap(Object obj, boolean del) {
        ForumPost o = (ForumPost)obj;

        if (del) {

            // delete from UserIdToMap
            Map map  = (Map) m_UserIdToMap.get(new Long(o.getUserId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            m_logger.debug("ForumPost removed from UserIdToMap" + o.getId() + " from " + o.getUserId());
        }
        else {
            
            // add to UserIdToMap
            Map map  = (Map) m_UserIdToMap.get(new Long(o.getUserId()));
            if ( map == null ) {
                map = new TreeMap();
                m_UserIdToMap.put(new Long(o.getUserId()), map);
            	m_logger.debug("created new   UserIdToMap for " + o.getUserId());

            }
            map.put(new Long(o.getId()), o);            
            m_logger.debug("ForumPost added to UserIdToMap " + o.getId() + " to " + o.getUserId());
        }
	}


    

    public static void main(String[] args) throws Exception {

        ForumPostDS ds = new ForumPostDS();
        ForumPost obj = ds.getById((long)1);
        System.out.println(obj);

    }
}
