package com.autosite.holder;

import com.autosite.db.AutositeDataObject;
import com.autosite.db.BaseAutositeDataObject;

public interface DataHolderObject extends AutositeDataObject {

    BaseAutositeDataObject getDataObject();
    public void setId(long id);
    public long getSiteId();
    public void setSiteId(long sid);
    
    public Object getValue(String name);
    
}
