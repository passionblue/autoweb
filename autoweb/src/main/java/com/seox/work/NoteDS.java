package com.seox.work; 

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.jtrend.service.DomainStore;
import com.seox.db.Note;
import com.seox.db.NoteDAO;
import com.seox.db.User;

public class NoteDS implements DomainStore { 

    protected NoteDAO m_noteDAO; 
    protected Map m_idToMap; 
    protected Map m_userIdToMap;
    protected User m_user;
    
    private static NoteDS m_noteDS= new NoteDS(); 
    private static Logger m_logger = Logger.getLogger(NoteDS.class);
    
    public static NoteDS getInstance() { 
        return m_noteDS; 
    } 

    protected NoteDS(User user) {
        this();
        m_user = user;
    }    
    
    protected NoteDS() { 
        m_noteDAO = new NoteDAO(); 
        m_idToMap = new HashMap(); 
        m_userIdToMap = new HashMap(); 
    } 
    
    public void loadFromDB() throws Exception { 
        m_logger.info("Loading data " + this.getClass().getName());
       
        List list  = null;
        
        if (m_user != null)
            list = m_noteDAO.findByUserId(new Long(m_user.getUserId()));
        else
            list = m_noteDAO.findAll(); 

        for (Iterator iter = list.iterator(); iter.hasNext();) { 
            Note note = (Note) iter.next(); 
            m_logger.debug("Loaded Note " + note.getNoteId() + " user=" + note.getUserId());
            updateMaps(note, false);
        } 
    } 
    
    public Note getNote(Long noteId) { 
        return (Note) m_idToMap.get(noteId); 
    } 
    
    public synchronized List getNotesByUser(Long userId)  {
        if (m_userIdToMap.containsKey(userId))
            return new ArrayList(((Map) m_userIdToMap.get(userId)).values());
        else
            return new ArrayList();
    } 
    
    public boolean deleteNote(User user, Note note) {
        
        if (user == null || note == null ) {
            m_logger.warn("Passed argument is null");
            return false;
        }
        
        Long userId = new Long (user.getUserId());
                
        synchronized (this) {
            if (m_userIdToMap.containsKey(new Long(user.getUserId()))) {
                Map userNotes = (Map)m_userIdToMap.get(userId);
                if ( !userNotes.containsKey(new Long(note.getNoteId()))) {
                    m_logger.warn("User " + userId + " does not own note " + note.getNoteId());
                    return false;
                }
            } else {
                m_logger.warn("User not found by ID" + user.getUserId() + " un=" + user.getUsername());
                return false;
            }
        }        
        
        try {
            m_noteDAO.delete(note);
        }
        catch (Exception e) {
            m_logger.error("Error while delete note from DB note " + note.getNoteId());
            return false;
        }
        updateMaps(note, true);
        m_logger.debug("Note deleted "+note.getNoteId());
        return true;
    }

    public boolean newNote(User user, Note note) {
        
        note.setUserId(user.getUserId());
        
        try {
            m_noteDAO.save(note);
        }
        catch (Exception e) {
            m_logger.error("Error while add note from DB note " + note.getNoteId());
            return false;
        }
        
        updateMaps(note, false);
        m_logger.debug("New Note created "+note.getNoteId());
        return true;
    }

    public boolean updateNote(Note note) {
        
        try {
            m_noteDAO.attachDirty(note);
        }
        catch (Exception e) {
            m_logger.error("Error while update note from DB note " + note.getNoteId());
            return false;
        }
        m_logger.debug("Note updated "+note.getNoteId());
        return true;
    }    
    
    private synchronized void updateMaps(Note note, boolean del) {
        
        Long noteId = new Long(note.getNoteId());
        if (del)
            m_idToMap.remove(noteId);
        else
            m_idToMap.put(noteId,note);
        
        
        // Update UserIdMap
        Map notes = (Map) m_userIdToMap.get(new Long(note.getUserId())); 
        if (notes == null) { 
            notes = new TreeMap();
            if (!del)
                m_userIdToMap.put(new Long(note.getUserId()), notes);
        } 
        if (del)
            notes.remove(noteId);
        else
            notes.put(noteId, note); 
    }

    public void reset() throws Exception {
    }

    public void print() {
        m_logger.debug(m_idToMap);
        m_logger.debug(m_userIdToMap);
    }
    
    public static void main(String args[]) throws Exception {
        NoteDS nds = NoteDS.getInstance();
        nds.loadFromDB();
        
        List notes = nds.getNotesByUser(new Long(20));
    }
    
    
    
} 
