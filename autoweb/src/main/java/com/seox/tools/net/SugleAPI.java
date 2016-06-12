/*
 * Created on Jan 4, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.seox.tools.net;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

public class SugleAPI {
    
    private static Logger _logger = Logger.getLogger(SugleAPI.class);
    
    public static void main(String[] args) throws Exception {
        
//        String data = loadFromFile("c:\\search6.htm");
        
        String url = "http://localhost:8089/search?hl=en&q=newyork";
        String data = NetTool.retrieveData(url);
        
        SugleSearchResults results = parseContents(data);
        
        _logger.debug("Total=" + results.getNumReults());
        _logger.debug("This SiZe=" + results.getResults().size());
        _logger.debug(results.getResults());
        
    }
    
    public static String loadFromFile(String filename) throws Exception {

        BufferedReader in  = new BufferedReader(new FileReader(filename));
        StringBuffer buffer = new StringBuffer();
        while(true) {
            String line = in.readLine();
            if (line == null) break;
            buffer.append(line);
        }
        
        String s = buffer.toString();

        return s;
    }
    
    public static SugleSearchResults parseContents(String contents) throws Exception {
        
        int approxSearchNum = -1;
        
        String s = contents;
        System.out.println(contents);
        
        int pos = 0;
        
        String markOnNum = "</b> of about <b>";
        String marketOnNumEnd = "</b>";

        pos = s.indexOf(markOnNum);
        if ( pos >= 0) {
            
            int end = s.indexOf(marketOnNumEnd, pos + markOnNum.length());
            if ( end > pos && 100 > end-pos)  {
                String num = s.substring(pos + markOnNum.length(), end).trim();
                System.out.println(num);
                Number val =  NumberFormat.getInstance().parse(num);
                _logger.debug("Num search results=" + val.intValue());
                approxSearchNum = val.intValue();
                
            } else {
                _logger.error("Could not parse the number of searches");
            }
        }

        int  posThis = 0;
        int  posNext = 0;
        int  posLink = 0;
        int posLinkEnd = 0;

        String divSec = "<div class=g";
        String dvSecEnd = "</div>";
        
        String markLink = "<a class=l href=";
        String markLinkEnd = "\"";
        
        List links = new ArrayList();
        while(true) {

            posThis = s.indexOf(divSec, pos);
            posNext = s.indexOf(dvSecEnd, posThis + 20);

            if (posThis <0 || posNext<1) {
                break;
            }

            posLink = s.indexOf(markLink, posThis);
            posLinkEnd = s.indexOf(markLinkEnd, posLink + markLink.length()+1);
            
            if (posLink < posLinkEnd && posLinkEnd < posNext ) {

                String link = s.substring(posLink + markLink.length(), posLinkEnd);
                System.out.println("Link=" + link);
                links.add(link.trim());
            } else {
                throw new Exception("Unable parse the page to get links.");
            }

            pos = posNext;
        }

        SugleSearchResults ret = new SugleSearchResults();
        ret.setNumReults(approxSearchNum);
        ret.setResults(links);
        
        return ret;
    }
    
    public static boolean isDuplicate(List links1, List links2) {
        
        if (links1.size() != links2.size()) return false;
        
        Iterator iter1 = links1.iterator();
        Iterator iter2 = links2.iterator();
        
        while(true) {

            Object item1 = iter1.next();
            Object item2 = iter2.next();
            
            if (!item1.equals(item2))
                return false;
            
            if (!iter1.hasNext() || !iter2.hasNext()) {
                break;
            }
        }
        return true;
    }
}

class SugleSearchResults {
    
    private int m_numReults;
    private List m_results;
    public int getNumReults() {
        return m_numReults;
    }
    public void setNumReults(int numReults) {
        m_numReults = numReults;
    }
    public List getResults() {
        return m_results;
    }
    public void setResults(List results) {
        m_results = results;
    }
    
}
