package com.autosite.ds;

import java.util.List;

import com.autosite.db.AutositeDataObject;

public class StyleConfigDSExtent extends  StyleConfigDS {


    public void updateMaps(Object obj, boolean del) {
        super.updateMaps(obj, del);
        fireEvent((AutositeDataObject)obj, del);
    }
    
    
    
    
    
    
    public static void main(String[] args) throws Exception {
        List l = StyleConfigDS.getInstance().getBySiteIdToStyleUseList(29, 0);
        System.out.println(l.size());
        
        StyleConfigDS.getInstance().loadFromDB();
    }
    
}