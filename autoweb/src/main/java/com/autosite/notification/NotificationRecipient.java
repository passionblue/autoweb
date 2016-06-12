package com.autosite.notification;

public class NotificationRecipient {
    
    private int     m_methodType; //SMS or Phone
    private String  m_recipientAddress; // Phone number or email.
    
    
    public NotificationRecipient(){
        
    }


    public int getMethodType() {
        return m_methodType;
    }


    public void setMethodType(int methodType) {
        m_methodType = methodType;
    }


    public String getRecipientAddress() {
        return m_recipientAddress;
    }


    public void setRecipientAddress(String recipientAddress) {
        m_recipientAddress = recipientAddress;
    }
    
    
    
    
    
    
}
