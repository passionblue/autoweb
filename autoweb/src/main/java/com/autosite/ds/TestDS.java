package com.autosite.ds;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import com.autosite.db.ContentCategory;
import com.autosite.db.ForumSubject;
import com.autosite.db.LinkStyleConfig;
import com.autosite.db.Panel;
import com.autosite.util.BlockListHandler;

public class TestDS {

    public static void main(String[] args) {
        //List products = EcProductDS.getInstance().getBySiteId(29);
        //EcCategory cat = EcCategoryDS.getInstance().getObjectByPageId(614);
        //System.out.println(cat.getCategoryName());

        System.out.println(BlockListHandler.getInstance().blocked("96.56.142.70"));
        
        /*

        List list = ContentCategoryDS.getInstance().getBySiteIdCategoryList(29, "xx");
        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            ContentCategory c = (ContentCategory) iterator.next();
            
            System.out.println(c.getId());
        }
        
        
        EcCheckoutAccountInfo obj = new EcCheckoutAccountInfo();
        obj.setSiteId(29);
        obj.setUserId(100);
        
        EcCheckoutAccountInfoDS.getInstance().put(obj);
        
        
        obj =EcCheckoutAccountInfoDS.getInstance().getBySiteIdUserId(29, 100);
        
        System.out.println(obj.getUserId());
        List all = PollDS.getInstance().getBySiteId(29);
        
        for (Iterator iterator = all.iterator(); iterator.hasNext();) {
            Poll poll = (Poll) iterator.next();
            
            System.out.println(PollDS.objectToString(poll));
        }
        

        List conts = ContentDS.getInstance().getByPageId(29);
        System.out.println(conts);
*/

        
    }

    static void testLinkStyleConfigDS(){
        
        LinkStyleConfig c = new LinkStyleConfig();
        
        c = LinkStyleConfigDS.createDefault();
        
        LinkStyleConfigDS.getInstance().put(c);
    }    
    static void testPanelDS(){
        
        Panel _Panel = new Panel(); 
        
        _Panel.setSiteId(1);
        _Panel.setColumnNum(12);
        _Panel.setTimeCreated(new Timestamp(System.currentTimeMillis()));
        
        List list = PanelDS.getInstance().getBySiteIdColumnNum(29, 3);
        
        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            Panel panel = (Panel) iterator.next();
            
            System.out.println(PanelDS.objectToString(panel));
        }
        
        
    }
    static void testFormSubjectDS(){
        ForumSubjectDS ds = new ForumSubjectDS();
        ForumSubject obj = ds.getById((long)1);
        System.out.println(obj);
    }
}
