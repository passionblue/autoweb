package com.autosite.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.autosite.db.BlogCategory;
import com.autosite.db.BlogPostCategoryRel;
import com.autosite.ds.BlogCategoryDS;
import com.autosite.ds.BlogPostCategoryRelDS;

public class BlogUtil {

    public static List getCategories(long siteId, long postId){
     
        List ret = new ArrayList();
        
        List postCatRel = BlogPostCategoryRelDS.getInstance().getByBlogPostId(postId);
        
        BlogCategoryDS catDS = BlogCategoryDS.getInstance();
        for (Iterator iterator = postCatRel.iterator(); iterator.hasNext();) {
            BlogPostCategoryRel rel = (BlogPostCategoryRel) iterator.next();
            
            BlogCategory cat = catDS.getById(rel.getBlogCategoryId());

            if (cat.getSiteId() == siteId) {
                ret.add(cat);
            }
        }
        return ret;
        
    }
}
