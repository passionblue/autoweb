package com.autosite.content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

public class ContentSilo {

    public final static String SEPARATOR = "^%$";
    private static ContentSilo m_instance = null;
    
    protected Map m_idToContent = new HashMap();
    protected Map m_siteToContents = new HashMap();
    protected Map m_commonContents = new HashMap();
    protected FileRepository m_fileRepo = null;
    
    public static synchronized ContentSilo getInstance() {
        if (m_instance == null)
            m_instance = new ContentSilo();
        
        return m_instance;
    }
    
    
    private ContentSilo() {
        
        try {
            m_fileRepo = new FileRepository("sitedata.dat");
            
            List loadedData = m_fileRepo.getAllData();
            System.out.println("Loaded=" + loadedData.size());
            //extract site information from data
            for (Iterator iterator = loadedData.iterator(); iterator.hasNext();) {
                String data = (String) iterator.next();
                System.out.println("DATA@@=" + data);
                Content cont = decodeContent(data);
                
                if (cont == null) {
                    System.out.println("################### ");
                    continue;
                }
                
                if (cont.isValid()) {
                    addToCache(cont);
                } else  {
                    removeFromCache(cont.getId());  
                }
            }
        }
        catch (Exception e) {
            //m_logger.error("FileRepo not init. data will be lost", e);
            e.printStackTrace();
        }
    }

    private Content decodeContent(String data) {
        
        int idx = data.indexOf(SEPARATOR);
        
        if (idx <0) {
            m_logger.debug("Data corrupted. header not found " + data);
        }
        
        String header = data.substring(0, idx);
        String content = data.substring(idx + SEPARATOR.length());
        
        StringTokenizer  tok = new StringTokenizer(header, "|");

        Content cont = new Content();
        cont.setContent(content);

        while(tok.hasMoreTokens()) {
            String nextTok = tok.nextToken();
            
            int pos = nextTok.indexOf("=");
            if (pos <0) continue;
            String key = nextTok.substring(0, pos);
            String val = nextTok.substring(pos + 1);
            
            if (key.equals("site")) {
                cont.setSite(val);
            } else if (key.equals("id") ){
                cont.setId(val);
            } else if (key.equals("removed")) {
                if ( val.equals("true"))
                    cont.setValid(false);
            }
        }

        if (cont.getId() == null ) return null;
        return cont;
    }

    private String encodeContent(Content cont) {
        
        StringBuffer buf = new StringBuffer();
        
        buf.append("id=").append(cont.getId()).append("|");
        if (cont.getSite() != null) 
            buf.append("site=").append(cont.getSite()).append("|");
        if (!cont.isValid()) {
            m_logger.debug("Content " + cont.getId() + " was removed. ");
            buf.append("removed=").append("true");
        }
        
        buf.append(SEPARATOR).append(cont.getContent());
        
        return buf.toString();
    }
    
    
    public synchronized boolean delete(String id) {
        if (!m_idToContent.containsKey(id)) {
            m_logger.debug("Content not found for " + id);
            return false;
        }
        Content cont = (Content) m_idToContent.get(id);

        cont.setValid(false);
        String persist = encodeContent(cont);
        m_fileRepo.append(persist);
        
        
        removeFromCache(cont.getId());
        return true;
    }
    
    private synchronized void removeFromCache(String id) {
        Content cont = (Content) m_idToContent.get(id);

        if ( cont.getSite() == null) {
            m_commonContents.remove(cont.getId());
        }  else {
            
            Map map = (Map) m_siteToContents.get(cont.getSite());
            if (map != null)
                map.remove(cont.getId());
        }
        
        m_idToContent.remove(id);
    }
    
    public synchronized Content store(String site, String content) {
        return store(site, content, true);
    }
    
    public synchronized Content store(String site, String content, boolean persistData) {
        
        if (content == null || content.trim().equals("")) {
            m_logger.warn("content for " + site + " is empty.");
            return null;
        }

        Content newCont = new Content();
        newCont.setContent(content);
        newCont.setSite(site);
        newCont.setId(String.valueOf(System.currentTimeMillis()));
        
        m_logger.debug("Adding content site=" + site + ",content=" + content);
        
        if (persistData) {
            String persist = encodeContent(newCont);
            m_logger.debug("Encoded for persist=" + persist);
            m_fileRepo.append(persist);
        }
        
        addToCache(newCont);
        return newCont;
    }

    private void addToCache(Content content) {

        
        if ( content.getSite() == null) {
            for (Iterator iterator = m_siteToContents.values().iterator(); iterator.hasNext();) {
                Map siteContent = (Map) iterator.next();
                siteContent.put(content.getId(), content);
            }
            m_commonContents.put(content.getId(), content);
        } else {
            Map contents = (Map) m_siteToContents.get(content.getSite());
            
            if ( contents == null) {
                contents = new HashMap();
                m_siteToContents.put(content.getSite(), contents);
                // TODO need to copy all common for newly added
                // contents.addAll(m_commonContents);
            }
            contents.put(content.getId(), content);
        }
        
        m_idToContent.put(content.getId(), content);
        m_logger.debug("Content added to cache=" + content.getId() + " ## " + content);
    }
    
    public synchronized Content getContent(String id) {
        return (Content) m_idToContent.get(id);
    }
    
    public synchronized List getSites() {
        return new ArrayList(m_siteToContents.keySet());
    }
    
    // Returns list of string of contents. for the passed site. 
    public synchronized List getContents(String site) {
        Map contents = (Map) m_siteToContents.get(site);

        List ret = new ArrayList();
        
        if (contents == null) return ret;
        
        for (Iterator iterator = contents.values().iterator(); iterator.hasNext();) {
            Content cont = (Content) iterator.next();
            ret.add(cont.getContent());
        }
        
        return ret;
    }
    
    public synchronized List getContent2(String site) {
        Map contents = (Map) m_siteToContents.get(site);

        if (contents == null) return new ArrayList();
        
        return new ArrayList(contents.values());
    }
    
    public static void main(String[] args) {
        ContentSilo silo = ContentSilo.getInstance();

        
    }
    private static Logger m_logger = Logger.getLogger(ContentSilo.class);
}
