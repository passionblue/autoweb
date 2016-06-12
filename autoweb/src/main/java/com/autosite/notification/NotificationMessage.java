package com.autosite.notification;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class NotificationMessage {

    private List m_recipients = new CopyOnWriteArrayList();
    private String m_message; 

    private String m_senderAddress; // Mostly for email
    private String m_senderAddressForReply; // For the reply purpose
    private String m_senderTitle;
    
    public NotificationMessage(String message){
        m_message = message;
    }


    public List getRecipients() {
        return m_recipients;
    }


    public void addRecipients(NotificationRecipient recipient) {
        if ( recipient != null) 
            m_recipients.add(recipient);
    }


    public String getMessage() {
        return m_message;
    }


    public void setMessage(String message) {
        m_message = message;
    }


    public String getSenderAddress() {
        return m_senderAddress;
    }


    public void setSenderAddress(String senderAddress) {
        m_senderAddress = senderAddress;
    }


    public String getSenderTitle() {
        return m_senderTitle;
    }


    public void setSenderTitle(String senderTitle) {
        m_senderTitle = senderTitle;
    }


    public String getSenderAddressForReply() {
        return m_senderAddressForReply;
    }


    public void setSenderAddressForReply(String senderAddressForReply) {
        m_senderAddressForReply = senderAddressForReply;
    }


    

    
    
    
}
