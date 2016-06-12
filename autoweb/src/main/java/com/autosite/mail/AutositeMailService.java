package com.autosite.mail;

public interface AutositeMailService {

    public void sendMail(String senderForReply, String dest, String subject, String content);
    public void sendHtml(String senderForReply, String dest, String subject, String content);

    public void sendMail(String senderForReply, String dest, String subject, String content, String attachementUrl);
    public void sendHtml(String senderForReply, String dest, String subject, String content, String attachementUrl);
    
    
}
