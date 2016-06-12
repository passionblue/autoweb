/* 
Template last modification history:


Source Generated: Sat Feb 14 00:12:25 EST 2015
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

import com.autosite.db.SynkNodeTrackerTxDAO;
import com.autosite.db.SynkNodeTrackerTx;

public class SynkNodeTrackerTxDSExtent extends  SynkNodeTrackerTxDS {


	// Note that m_cacheEnable flag may disable caching the object. 
    public void updateMaps(Object obj, boolean del) {
        super.updateMaps(obj, del);
        fireEvent((AutositeDataObject)obj, del);
    }
}