package com.autosite.util.chur;

import java.util.Comparator;

import com.autosite.db.ChurMember;

public class ChurMemberNameComparator implements Comparator<ChurMember>{

    boolean m_reverse = false;
    public ChurMemberNameComparator(){
        this(false);
    }
    public ChurMemberNameComparator(boolean reverse){
        m_reverse = reverse;
    }
    
    public int compare(ChurMember o1, ChurMember o2) {
        if (m_reverse) 
            return o2.getFullName().compareToIgnoreCase(o1.getFullName());
        else
            return o1.getFullName().compareToIgnoreCase(o2.getFullName());
    };
}
