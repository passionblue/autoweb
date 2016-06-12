package com.autosite.util;

import com.autosite.db.EmailSubs;
import com.autosite.ds.EmailSubsDS;
import com.jtrend.util.TimeNow;

public class EmailSubscriptionUtil {

    public static void registerEmailForSubscription(long siteId, String firstName, String lastName, String email, String subject){
        EmailSubs subs = new EmailSubs();
        subs.setSiteId(siteId);
        subs.setConfirmed(1);
        subs.setFirstName(firstName);
        subs.setLastName(lastName);
        subs.setTimeCreated(new TimeNow());
        subs.setEmail(email);
        subs.setSubject(subject);
        
        EmailSubsDS.getInstance().put(subs);
    }
    
}
