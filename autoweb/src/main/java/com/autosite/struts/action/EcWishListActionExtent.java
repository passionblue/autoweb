package com.autosite.struts.action;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.autosite.db.BaseAutositeDataObject;
import com.autosite.db.EcWishList;
import com.autosite.ds.EcWishListDS;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.jtrend.util.WebUtil;

public class EcWishListActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcWishListAction#xtent.beforeAdd");		
        EcWishList _EcWishList = (EcWishList)baseDbOject;

        if ( WebUtil.isNull(_EcWishList.getProductId())){
            m_logger.debug("productId is missing in this requres " + EcWishListDS.objectToString(_EcWishList));
            throw new Exception("productId is missing.");
        }
        
        List list = m_ds.getByUserId(_EcWishList.getId());
        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            EcWishList wl = (EcWishList) iterator.next();
            
            if (wl.getProductId() == _EcWishList.getProductId()){
                m_logger.debug("This product exists in the wish list. " + EcWishListDS.objectToString(_EcWishList));
                throw new Exception("This product exists in the wish list.");
            }
        }
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#EcWishListAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        EcWishList _EcWishList = (EcWishList)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcWishListAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        EcWishList _EcWishList = (EcWishList)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcWishListAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        EcWishList _EcWishList = (EcWishList)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcWishListAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        EcWishList _EcWishList = (EcWishList)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcWishListAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        EcWishList _EcWishList = (EcWishList)baseDbOject;
    }

	private EcWishListDS m_ds = EcWishListDS.getInstance();
    private static Logger m_logger = Logger.getLogger( EcWishListActionExtent.class);
    
}
