package com.seox.work; 

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.jtrend.service.DomainStore;
import com.seox.db.DevNote;
import com.seox.db.DevNoteDAO;
import com.seox.db.User;

public class DevNoteDS implements DomainStore { 

    protected DevNoteDAO m_devNoteDAO; 
    protected Map m_idToMap; 
    
    private static DevNoteDS m_devNoteDS= new DevNoteDS(); 
    private static Logger m_logger = Logger.getLogger(DevNoteDS.class);
    
    public static DevNoteDS getInstance() { 
        return m_devNoteDS; 
    } 
    
    protected DevNoteDS() { 
        m_devNoteDAO = new DevNoteDAO(); 
        m_idToMap = new HashMap(); 
    } 
    
    public void loadFromDB() throws Exception { 
        m_logger.info("Loading data " + this.getClass().getName());
       
        List list  = null;
        
        list = m_devNoteDAO.findAll(); 

        for (Iterator iter = list.iterator(); iter.hasNext();) { 
            DevNote devNote = (DevNote) iter.next(); 
//            m_logger.debug("Loaded DevNote " + devNote.getDevNoteId() + " user=" + devNote.getUserId());
            updateMaps(devNote, false);
        } 
    } 
    
    public DevNote getDevNote(Long devNoteId) { 
        return (DevNote) m_idToMap.get(devNoteId); 
    } 
    
    public boolean deleteDevNote(DevNote devNote) {
        
        try {
            m_devNoteDAO.delete(devNote);
        }
        catch (Exception e) {
            m_logger.error("Error while delete devNote from DB devNote " + devNote.getDevNoteId());
            return false;
        }
        updateMaps(devNote, true);
        m_logger.debug("DevNote deleted "+devNote.getDevNoteId());
        return true;
    }

    public boolean newDevNote(DevNote devNote) {
        
        try {
            m_devNoteDAO.save(devNote);
        }
        catch (Exception e) {
            m_logger.error("Error while add devNote from DB devNote " + devNote.getDevNoteId());
            return false;
        }
        
        updateMaps(devNote, false);
        m_logger.debug("New DevNote created "+devNote.getDevNoteId());
        return true;
    }

    public boolean updateDevNote(DevNote devNote) {
        
        try {
            m_devNoteDAO.attachDirty(devNote);
        }
        catch (Exception e) {
            m_logger.error("Error while update devNote from DB devNote " + devNote.getDevNoteId());
            return false;
        }
        m_logger.debug("DevNote updated "+devNote.getDevNoteId());
        return true;
    }    
    
    private synchronized void updateMaps(DevNote devNote, boolean del) {
        
        Long devNoteId = new Long(devNote.getDevNoteId());
        if (del)
            m_idToMap.remove(devNoteId);
        else
            m_idToMap.put(devNoteId,devNote);
    }

    public void reset() throws Exception {
    }

    public void print() {
        m_logger.debug(m_idToMap);
    }
    
    public static void main(String args[]) throws Exception {
        DevNoteDS nds = DevNoteDS.getInstance();
        nds.loadFromDB();
        
//        DevNote devNote = nds.getDevNote(new Long(3));
//        devNote.setDevNoteStr("TEST 3");
        
        DevNote devNote = new DevNote();
        devNote.setDevNoteEnteredT(new Date());
        devNote.setDevNoteStr("TESTTEST");
        
        nds.newDevNote(devNote);
        
        nds.print();
    }
} 
