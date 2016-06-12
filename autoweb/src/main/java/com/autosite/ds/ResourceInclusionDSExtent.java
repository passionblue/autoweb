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

import com.autosite.db.ResourceInclusionDAO;
import com.autosite.db.ResourceInclusion;

public class ResourceInclusionDSExtent extends  ResourceInclusionDS {

    public boolean getData(String arg, int arg2) {
		return true;
	}
    public void getHyunjoo() {
	}

    public void updateMaps(Object obj, boolean del) {
        super.updateMaps(obj, del);
        fireEvent((AutositeDataObject)obj, del);
    }
}