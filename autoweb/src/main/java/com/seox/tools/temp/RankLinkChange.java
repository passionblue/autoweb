/*
 * Created on Jan 4, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.seox.tools.temp;

import java.util.Iterator;
import java.util.List;

import com.seox.db.Rank;
import com.seox.db.RankDAO;

public class RankLinkChange {

    public static void main(String[] args) {
        
        RankDAO dao = new RankDAO();
        List list = dao.findByProperty("rank", new Integer(0));
        
        System.out.println(list.size());
        
        for(Iterator iter=list.iterator();iter.hasNext();) {
            Rank rank = (Rank) iter.next();
            
            if (rank.getRank() == 0 && (rank.getLink() == null || rank.getLink().trim().equals(""))) {

                System.out.println(rank.getDomain());
                rank.setLink("rankNum=" + rank.getDomain() + ";");
                dao.attachDirty(rank);
            }
        }
    }
}
