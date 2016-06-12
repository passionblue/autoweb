package com.autosite.mail.impl;

import java.io.File;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.autosite.mail.AutositeMailService;
import com.jtrend.util.WebUtil;

public class MailServiceImpl implements AutositeMailService {

    private static Logger m_logger = LoggerFactory.getLogger(MailServiceImpl.class);
    
    private MailSender          mailSender;
    private SimpleMailMessage   simpleMailMessage;
    private String              mailFrom;
    
    public MailSender getMailSender() {
        return mailSender;
    }

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public SimpleMailMessage getSimpleMailMessage() {
        return simpleMailMessage;
    }

    public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
        this.simpleMailMessage = simpleMailMessage;
    }

    public String getMailFrom() {
        return mailFrom;
    }

    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom;
    }

    @Override
    public void sendMail(String senderForReply, String dest, String subject, String content) {

        SimpleMailMessage message = new SimpleMailMessage(simpleMailMessage);
        
        if ( senderForReply == null) 
            message.setFrom(mailFrom);
        else 
            message.setFrom(senderForReply);
            
        message.setTo(dest);
        message.setSubject(subject);
        message.setText(content);
        
        mailSender.send(message);
    }

    @Override
    public void sendHtml(String senderForReply, String dest, String subject, String content) {
        
        final MimeMessage       mimeMessage = ((JavaMailSenderImpl)mailSender).createMimeMessage(); 
        final MimeMessageHelper messageHelper     = new MimeMessageHelper(mimeMessage);
        
        try {
            if ( senderForReply == null) 
                messageHelper.setFrom(mailFrom);
            else 
                messageHelper.setFrom(senderForReply);
            messageHelper.setTo(dest);
            messageHelper.setSubject(subject);
            messageHelper.setText(content, true);
            
            ((JavaMailSenderImpl)mailSender).send(mimeMessage);
        }
        catch (Exception e) {
            m_logger.error(e.getMessage(),e);
        }
    }

    @Override
    public void sendMail(String senderForReply, String dest, String subject, String content, String attachementUrl) {
        
        try {
            final MimeMessage       mimeMessage = ((JavaMailSenderImpl)mailSender).createMimeMessage(); 
            final MimeMessageHelper messageHelper     = new MimeMessageHelper(mimeMessage, WebUtil.isNotNull(attachementUrl));

            if ( senderForReply == null) 
                messageHelper.setFrom(mailFrom);
            else 
                messageHelper.setFrom(senderForReply);
            messageHelper.setTo(dest);
            messageHelper.setSubject(subject);
            messageHelper.setText(content, false);
            
            setAttachementInMessageHelper(messageHelper, attachementUrl);
            ((JavaMailSenderImpl)mailSender).send(mimeMessage);
        }
        catch (Exception e) {
            m_logger.error(e.getMessage(),e);
        }        
    }

    @Override
    public void sendHtml(String senderForReply, String dest, String subject, String content, String attachementUrl) {
        
        try {

            final MimeMessage       mimeMessage = ((JavaMailSenderImpl)mailSender).createMimeMessage(); 
            final MimeMessageHelper messageHelper     = new MimeMessageHelper(mimeMessage, WebUtil.isNotNull(attachementUrl));
            
            if ( senderForReply == null) 
                messageHelper.setFrom(mailFrom);
            else 
                messageHelper.setFrom(senderForReply);
            messageHelper.setTo(dest);
            messageHelper.setSubject(subject);
            messageHelper.setText(content, true);
            
            setAttachementInMessageHelper(messageHelper, attachementUrl);
            ((JavaMailSenderImpl)mailSender).send(mimeMessage);

        }
        catch (Exception e) {
            m_logger.error(e.getMessage(),e);
        }        

    }
    
    
    private void setAttachementInMessageHelper( MimeMessageHelper messageHelper, String attachementUrl) throws Exception {
        // let's attach the infamous windows Sample file (this time copied to c:/)
        
        if (  WebUtil.isNotNull(attachementUrl) ) {
        
            File file = new File(attachementUrl);
            
            if ( file.exists() ) {
                FileSystemResource fileResource = new FileSystemResource(file);
                
                messageHelper.addAttachment(file.getName(), fileResource);
            } else {
                 
            }
        }            
    }
}
