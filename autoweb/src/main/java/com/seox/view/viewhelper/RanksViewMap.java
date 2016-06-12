/*
 * Created on Dec 9, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.seox.view.viewhelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

// This Map can be used to index RanksViewMap with keyword -> domain or domain->keyword

public class RanksViewMap {

    private String m_mainId;
    private Map m_subIdToRankView = new TreeMap();

    public RanksViewMap(String mainId) {
        m_mainId = mainId;
    }
    
    public String getMapMainId() {
        return m_mainId;
    }
    
    public void addRanksView(String subId, RanksView ranksView) {
        m_subIdToRankView.put(subId, ranksView);
    }
    
    public RanksView getRanksView(String subId) {
        return(RanksView) m_subIdToRankView.get(subId);
    }
    
    public List getAllRanksView() {
        return new ArrayList(m_subIdToRankView.values());
    }
}
