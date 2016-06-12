package com.autosite.holder;

import java.sql.Timestamp;
import java.sql.Date;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.util.html.HtmlGenRow;
import java.util.ArrayList;
import java.util.List;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import com.autosite.db.ResourceInclusion;


public class ResourceInclusionDataHolder  extends AbstractDataHolder  implements java.io.Serializable, DataHolderObject, HtmlGenRow {

 // Fields

	//Files to idx
    private static Map<String, Integer> fieldsMap = new ConcurrentSkipListMap<String, Integer>();

    static {
        fieldsMap.put("name", new Integer(0));
        fieldsMap.put("include", new Integer(1));
        fieldsMap.put("resourceType", new Integer(2));
    }


	ResourceInclusion m_resourceInclusion;

	public ResourceInclusionDataHolder(ResourceInclusion _ResourceInclusion){
		m_resourceInclusion =  _ResourceInclusion; 	
	}

	public ResourceInclusionDataHolder(){
		m_resourceInclusion =  new ResourceInclusion(); 	
	}

	public  BaseAutositeDataObject getDataObject(){
		return 	m_resourceInclusion;
	}


	public long getId(){
		return 	m_resourceInclusion.getId();
	}
	public void setId(long id){
		// INVALID
	}
	public long getSiteId(){
		return 	m_resourceInclusion.getSiteId();
	}
	public void setSiteId(long sid){
		m_resourceInclusion.setSiteId(sid);
	}


	public String getName(){
		return m_resourceInclusion.getName();
	}
	public int getInclude(){
		return m_resourceInclusion.getInclude();
	}
	public int getResourceType(){
		return m_resourceInclusion.getResourceType();
	}

	public void setName(String _name ){
		m_resourceInclusion.setName(_name);
	}
	public void setInclude(int _include ){
		m_resourceInclusion.setInclude(_include);
	}
	public void setResourceType(int _resourceType ){
		m_resourceInclusion.setResourceType(_resourceType);
	}

	// For HtmlGenRow
    public int getColumnCount() {
		return fieldsMap.size();
    }
    public static List getFieldsName(){
        return new ArrayList(fieldsMap.keySet());
    }
    public String getColumnData(String col){
        if(fieldsMap.containsKey(col)){
            return getColumnData(fieldsMap.get(col).intValue());
        } else {
            return null;
        }
    }

    public String getColumnData(int colNum) {

        if ( colNum == 0) return getName() == null?"":getName().toString();
        if ( colNum == 1) return String.valueOf(getInclude());
        if ( colNum == 2) return String.valueOf(getResourceType());
		return "";
    }
}