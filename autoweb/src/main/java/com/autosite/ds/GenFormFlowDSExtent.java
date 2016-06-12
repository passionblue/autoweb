/* 
Template last modification history:


Source Generated: Sun Mar 15 14:31:01 EDT 2015
*/

package com.autosite.ds;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Iterator;
import java.sql.Timestamp;
import java.util.concurrent.ConcurrentHashMap;
import com.autosite.db.AutositeDataObject;

import org.apache.log4j.Logger;

import com.autosite.AutositeGlobals;
import com.jtrend.service.DomainStore;


public class GenFormFlowDSExtent extends  GenFormFlowDS {

    public boolean getData(String arg, int arg2) {
		return true;
	}
    public void getTimestamp() {
	}

	// Note that m_cacheEnable flag may disable caching the object. 
    public void updateMaps(Object obj, boolean del) {
        super.updateMaps(obj, del);
        fireEvent((AutositeDataObject)obj, del);
    }
}