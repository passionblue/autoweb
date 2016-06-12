package com.autosite.struts.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.autosite.db.BaseAutositeDataObject;
import com.autosite.db.EcCategory;
import com.autosite.db.EcPageProductRel;
import com.autosite.db.EcProduct;
import com.autosite.ds.EcCategoryDS;
import com.autosite.ds.EcPageProductRelDS;
import com.autosite.struts.action.core.AutositeActionExtent;

public class EcProductActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcProductAction#xtent.beforeAdd");		
        EcProduct _EcProduct = (EcProduct)baseDbOject;
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#EcProductAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        EcProduct _EcProduct = (EcProduct)baseDbOject;
        
        List cats = getAddToCategories(request);

        EcCategoryDS catDS = EcCategoryDS.getInstance();
        EcPageProductRelDS pageProdRelDS = EcPageProductRelDS.getInstance();

        for (Iterator iterator = cats.iterator(); iterator.hasNext();) {
            EcCategory ecCat = (EcCategory) iterator.next();
            
            EcPageProductRel  ccPageProductRel = new  EcPageProductRel();
            ccPageProductRel.setCategoryId(ecCat.getId());
            ccPageProductRel.setProductId(_EcProduct.getId());
            ccPageProductRel.setSiteId(_EcProduct.getSiteId());
            ccPageProductRel.setTimeCreated(new Timestamp(System.currentTimeMillis()));
            
            if (ecCat.getId() == _EcProduct.getCategoryId())
                ccPageProductRel.setMainCategory(1);
            else
                ccPageProductRel.setMainCategory(0);
            
            pageProdRelDS.put(ccPageProductRel);
            m_logger.info("------------------------------------------------------------- " + pageProdRelDS.objectToString(ccPageProductRel));
        }
        
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcProductAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        EcProduct _EcProduct = (EcProduct)baseDbOject;

        List cats = getAddToCategories(request);

        EcCategoryDS catDS = EcCategoryDS.getInstance();
        EcPageProductRelDS pageProdRelDS = EcPageProductRelDS.getInstance();

        List list = pageProdRelDS.getBySiteIdProductId(_EcProduct.getSiteId(), _EcProduct.getId());

        Set catIdInRequest = new HashSet();
        
        for (Iterator iterator = cats.iterator(); iterator.hasNext();) {
            EcCategory ecCat = (EcCategory) iterator.next();
            
            catIdInRequest.add(new Long(ecCat.getId()));
            
            boolean exist = false;
            for (Iterator iterator2 = list.iterator(); iterator2.hasNext();) {
                EcPageProductRel ppRel = (EcPageProductRel) iterator2.next();
                
                if (ecCat.getId() == ppRel.getCategoryId()) {
                    exist = true;
                    break;
                }
            }
            
            if (!exist) {
                EcPageProductRel  ccPageProductRel = new  EcPageProductRel();
                ccPageProductRel.setCategoryId(ecCat.getId());
                ccPageProductRel.setProductId(_EcProduct.getId());
                ccPageProductRel.setSiteId(_EcProduct.getSiteId());
                ccPageProductRel.setTimeCreated(new Timestamp(System.currentTimeMillis()));
                pageProdRelDS.put(ccPageProductRel);
            }
        }
        
        m_logger.debug("Cats in request=" + catIdInRequest);
        
        for (Iterator iterator2 = list.iterator(); iterator2.hasNext();) {
            EcPageProductRel ppRel = (EcPageProductRel) iterator2.next();
            
            if (!catIdInRequest.contains(new Long(ppRel.getCategoryId()))){
                if ( ppRel.getMainCategory() != 1)
                    pageProdRelDS.delete(ppRel);
            }
        }
       
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcProductAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        EcProduct _EcProduct = (EcProduct)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcProductAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        EcProduct _EcProduct = (EcProduct)baseDbOject;

        EcPageProductRelDS pageProdRelDS = EcPageProductRelDS.getInstance();
        List cats = pageProdRelDS.getBySiteIdProductId(_EcProduct.getSiteId(), _EcProduct.getId());
        for (Iterator iterator = cats.iterator(); iterator.hasNext();) {
            EcPageProductRel ccPageProductRel = (EcPageProductRel) iterator.next();
            pageProdRelDS.delete(ccPageProductRel);
        }
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcProductAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        EcProduct _EcProduct = (EcProduct)baseDbOject;
    }

    
    public List getAddToCategories(HttpServletRequest request){
        List ret = new ArrayList();
        
        String catKey = "category_";
        Enumeration enumer = request.getParameterNames();
        EcCategoryDS catDS = EcCategoryDS.getInstance();
        while(enumer.hasMoreElements()){
            String name = (String)enumer.nextElement();
            
            if (name.startsWith(catKey)){
                try {
                    long catId = Long.parseLong(name.substring(catKey.length()));
                    EcCategory ecCat = catDS.getById(catId);
                    m_logger.debug("category added. cat=" + catDS.objectToString(ecCat));
                    ret.add(ecCat);
                }
                catch (Exception e) {
                    m_logger.error(e.getMessage(),e);
                    continue;
                }
            }
            
        }
        return ret;
    }
    
    private static Logger m_logger = Logger.getLogger( EcProductActionExtent.class);
    
}
