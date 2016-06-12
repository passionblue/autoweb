package com.autosite.notification;

import com.autosite.util.cleaner.CleanerDef;

public class NotificationUtil {

    
    
    public static NotificationRecipient createSmsRecipient(String phoneNumber) {
        NotificationRecipient ret = new NotificationRecipient();
        ret.setMethodType(CleanerDef.CustomerNotificationMethodTypeSMS);
        ret.setRecipientAddress(phoneNumber);
        return ret;
    }
    
    public static NotificationRecipient createEmailRecipient(String emailAddress) {
        NotificationRecipient ret = new NotificationRecipient();
        ret.setMethodType(CleanerDef.CustomerNotificationMethodTypeEmail);
        ret.setRecipientAddress(emailAddress);
        return ret;
        
    }
}
